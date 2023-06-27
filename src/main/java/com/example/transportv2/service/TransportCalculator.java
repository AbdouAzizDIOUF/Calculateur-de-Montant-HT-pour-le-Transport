package com.example.transportv2.service;

import com.example.transportv2.entity.*;
import com.example.transportv2.repository.ResultCalculRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author diouf
 */
@Service
public class TransportCalculator {
    private final DataLoaderService dataLoaderService;

    @Autowired
    private ResultCalculRepository resultCalculRepository;

    public TransportCalculator(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    /**
     *
     * @param expId
     * @param destId
     * @param nombreColis
     * @param poids
     */
    public ResultCalcul calculateTransportCost(int expId, int destId, int nombreColis, BigDecimal poids, boolean portPaye) {

        // Chargement des clients, localités, conditions de taxation et tarifs
        List<Client> clients = dataLoaderService.loadClients();
        List<Localite> localites = dataLoaderService.loadLocalites();
        List<ConditionTaxation> conditionsTaxations = dataLoaderService.loadConditionsTaxations();
        List<Tarif> tarifs = dataLoaderService.loadTarifs();

        // Affichage des clients disponibles
        System.out.println("Liste des clients :");
        for (Client client : clients) {
            System.out.println("id : "+client.getIdClient() + " - " + client.getRaisonSociale()+"  -  ville :"+client.getVille()+"  -  code Postal :"+client.getCodePostal());
        }



        Client expediteur = findClientById(clients, expId);
        Client destinataire = findClientById(clients, destId);

        portPaye = portPaye; // true si port payé, false si port du (à modifier selon le choix)

        // Récupération de la zone du destinataire
        int zone = findZoneByCodePostal(localites, destinataire.getCodePostal());

        // Recherche du tarif correspondant
        Tarif tarif = findTarif(tarifs, expediteur.getIdClient(), destinataire.getIdClient(), destinataire.getCodePostal(), zone);

        // Si le tarif n'existe pas pour la zone déterminée, on utilise le tarif de la zone précédente
        if (tarif == null) {
            int zonePrecedente = zone - 1;
            tarif = findTarif(tarifs, expediteur.getIdClient(), destinataire.getIdClient(), destinataire.getCodePostal(), zonePrecedente);
        }

        // Si le tarif n'existe pas pour le client et le département, on utilise le tarif général ou un tarif hérité
        if (tarif == null) {
            tarif = findTarif(tarifs, 0, expediteur.getIdClient(), destinataire.getCodePostal(), zone);
        }

        // Récupération de la condition de taxation correspondante
        ConditionTaxation conditionTaxation = findConditionTaxation(conditionsTaxations, expediteur.getIdClient());

        // Si le client n'a pas de condition de taxation, on utilise les conditions de taxation générales
        if (conditionTaxation == null) {
            conditionTaxation = findConditionTaxation(conditionsTaxations, 0);
        }

        // Calcul du montant HT
        BigDecimal montantHT = calculateMontantHT(tarif, conditionTaxation, portPaye, nombreColis, poids);

        // Affichage du détail du calcul
        System.out.println("Détail du calcul :");
        System.out.println("Code Postal :"+tarif.getCodeDepartement());
        System.out.println("Montant HT tarif : " + tarif.getMontant());
        System.out.println("Taxe à appliquer : " + getTaxeToApply(conditionTaxation, portPaye, nombreColis, poids));
        System.out.println("Montant HT total : " + montantHT);

        ResultCalcul resultCalcul = new ResultCalcul();
        resultCalcul.setMontantHTTarif(tarif.getMontant());
        resultCalcul.setTaxeAAppliquer(getTaxeToApply(conditionTaxation, portPaye, nombreColis, poids));
        resultCalcul.setMontantHTTotal(montantHT);

        resultCalcul.setIdExpediteur(expId);
        resultCalcul.setIdDestinataire(destId);
        resultCalcul.setDateCreation(new Date());

        this.resultCalculRepository.save(resultCalcul);
        return resultCalcul;
    }


    /**
     * Return le client selon son l'id
     *
     * @param clients
     * @param idClient
     * @return
     */
    private Client findClientById(List<Client> clients, int idClient) {
        for (Client client : clients) {
            if (client.getIdClient() == idClient) {
                return client;
            }
        }
        return null;
    }

    /**
     * Recupere la zone d'un localité
     *
     * @param localites
     * @param codePostal
     * @return
     */
    private int findZoneByCodePostal(List<Localite> localites, String codePostal) {
        for (Localite localite : localites) {
            System.out.println("--------------------------------");
            System.out.println("code post: "+codePostal);
            System.out.println("localite : "+localite.toString());
            System.out.println("--------------------------------");

            if (localite.getCodePostal().equals(codePostal)) {
                return localite.getZone();
            }
        }
        return 0; // Valeur par défaut si la zone n'est pas trouvée
    }


    /**
     * Reucupere le tarif du client
     *
     * @param tarifs
     * @param idClient
     * @param idClientHeritage
     * @param codeDepartement
     * @param zone
     * @return
     */
    private Tarif findTarif(List<Tarif> tarifs, int idClient, Integer idClientHeritage, String codeDepartement, int zone) {
        for (Tarif tarif : tarifs) {
            if (tarif.getIdClient() == idClient && tarif.getCodeDepartement().equals(codeDepartement) && tarif.getZone() == zone) {
                return tarif;
            } else if (tarif.getIdClient().equals(idClientHeritage) && tarif.getCodeDepartement().equals(codeDepartement) && tarif.getZone() == zone) {
                return tarif;
            }
        }
        return null;
    }


    /**
     *
     * @param conditionsTaxations
     * @param idClient
     * @return
     */
    private ConditionTaxation findConditionTaxation(List<ConditionTaxation> conditionsTaxations, int idClient) {
        for (ConditionTaxation conditionTaxation : conditionsTaxations) {
            if (conditionTaxation.getIdClient() == idClient) {
                return conditionTaxation;
            }
        }
        return null;
    }


    /**
     *
     * @param tarif
     * @param conditionTaxation
     * @param portPaye
     * @param nombreColis
     * @param poids
     * @return
     */
    private BigDecimal calculateMontantHT(Tarif tarif, ConditionTaxation conditionTaxation, boolean portPaye, int nombreColis, BigDecimal poids) {
        BigDecimal montantHT = tarif.getMontant();

        if (portPaye) {
            if (conditionTaxation.isUseTaxePortPayeGenerale()) {
                montantHT = montantHT.add(conditionTaxation.getTaxePortPaye());
            } else {
                montantHT = montantHT.add(conditionTaxation.getTaxePortPaye().multiply(new BigDecimal(nombreColis)).multiply(poids));
            }
        } else {
            if (conditionTaxation.isUseTaxePortDuGenerale()) {
                montantHT = montantHT.add(conditionTaxation.getTaxePortDu());
            } else {
                montantHT = montantHT.add(conditionTaxation.getTaxePortDu().multiply(new BigDecimal(nombreColis)).multiply(poids));
            }
        }

        return montantHT;
    }


    /**
     *
     * @param conditionTaxation
     * @param portPaye
     * @param nombreColis
     * @param poids
     * @return
     */
    private BigDecimal getTaxeToApply(ConditionTaxation conditionTaxation, boolean portPaye, int nombreColis, BigDecimal poids) {
        if (portPaye) {
            if (conditionTaxation.isUseTaxePortPayeGenerale()) {
                return conditionTaxation.getTaxePortPaye();
            } else {
                return conditionTaxation.getTaxePortPaye().multiply(new BigDecimal(nombreColis)).multiply(poids);
            }
        } else {
            if (conditionTaxation.isUseTaxePortDuGenerale()) {
                return conditionTaxation.getTaxePortDu();
            } else {
                return conditionTaxation.getTaxePortDu().multiply(new BigDecimal(nombreColis)).multiply(poids);
            }
        }
    }

}
