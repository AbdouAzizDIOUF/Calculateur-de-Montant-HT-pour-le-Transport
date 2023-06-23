package com.example.transportv2.service;

import com.example.transportv2.entity.Client;
import com.example.transportv2.entity.ConditionTaxation;
import com.example.transportv2.entity.Localite;
import com.example.transportv2.entity.Tarif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataLoaderService {

    private final String CLIENTS_FILE_PATH = "src/main/resources/static/client.xml";
    private final String LOCALITES_FILE_PATH = "src/main/resources/static/localite.xml";
    private final String CONDITIONS_TAXATIONS_FILE_PATH = "src/main/resources/static/conditionTaxation.xml";
    private final String TARIFS_FILE_PATH = "src/main/resources/static/tarif.xml";



    public List<Client> loadClients() {
        List<Client> clients = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(CLIENTS_FILE_PATH));
            document.getDocumentElement().normalize();

            NodeList clientList = document.getElementsByTagName("ObjectClient");

            for (int i = 0; i < clientList.getLength(); i++) {
                Element clientElement = (Element) clientList.item(i);

                Integer idClient = Integer.parseInt(clientElement.getElementsByTagName("idClient").item(0).getTextContent());
                String raisonSociale = clientElement.getElementsByTagName("raisonSociale").item(0).getTextContent();
                String codePostal = clientElement.getElementsByTagName("codePostal").item(0).getTextContent();
                String ville = clientElement.getElementsByTagName("ville").item(0).getTextContent();

                Client client = new Client(idClient, raisonSociale, codePostal, ville);
                clients.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return clients;
    }


    public List<Localite> loadLocalites() {
        List<Localite> localites = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            //String LOCALITES_FILE_PATH = "path/to/localite.xml";
            Document document = builder.parse(new File(LOCALITES_FILE_PATH));
            document.getDocumentElement().normalize();

            NodeList localiteList = document.getElementsByTagName("ObjectLocalite");

            for (int i = 0; i < localiteList.getLength(); i++) {
                Element localiteElement = (Element) localiteList.item(i);

                String codePostal = localiteElement.getElementsByTagName("codePostal").item(0).getTextContent();
                String ville = localiteElement.getElementsByTagName("ville").item(0).getTextContent();
                Integer zone = Integer.parseInt(localiteElement.getElementsByTagName("zone").item(0).getTextContent());

                Localite localite = new Localite(codePostal, ville, zone);
                localites.add(localite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return localites;
    }


    public List<ConditionTaxation> loadConditionsTaxations() {
        List<ConditionTaxation> conditionsTaxations = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(CONDITIONS_TAXATIONS_FILE_PATH));
            document.getDocumentElement().normalize();

            NodeList conditionTaxationList = document.getElementsByTagName("ObjectConditionTaxation");

            for (int i = 0; i < conditionTaxationList.getLength(); i++) {
                ConditionTaxation conditionTaxation = new ConditionTaxation();
                Element conditionTaxationElement = (Element) conditionTaxationList.item(i);

                String idClient = conditionTaxationElement.getElementsByTagName("idClient").item(0).getTextContent();//Integer.parseInt();
                BigDecimal taxePortDu = new BigDecimal(conditionTaxationElement.getElementsByTagName("taxePortDu").item(0).getTextContent());
                BigDecimal taxePortPaye = new BigDecimal(conditionTaxationElement.getElementsByTagName("taxePortPaye").item(0).getTextContent());
                boolean useTaxePortDuGenerale = Boolean.parseBoolean(conditionTaxationElement.getElementsByTagName("useTaxePortDuGenerale").item(0).getTextContent());
                boolean useTaxePortPayeGenerale = Boolean.parseBoolean(conditionTaxationElement.getElementsByTagName("useTaxePortPayeGenerale").item(0).getTextContent());

                if (!idClient.isEmpty()) {
                    conditionTaxation.setIdClient(Integer.parseInt(idClient));
                }else {
                    conditionTaxation.setIdClient(0);
                }

                conditionTaxation.setTaxePortPaye(taxePortPaye);
                conditionTaxation.setTaxePortDu(taxePortDu);
                conditionTaxation.setUseTaxePortDuGenerale(useTaxePortDuGenerale);
                conditionTaxation.setUseTaxePortPayeGenerale(useTaxePortPayeGenerale);

                //ConditionTaxation conditionTaxation = new ConditionTaxation(idClient, taxePortDu, taxePortPaye, useTaxePortDuGenerale, useTaxePortPayeGenerale);
                conditionsTaxations.add(conditionTaxation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conditionsTaxations;
    }

    public List<Tarif> loadTarifs() {
        List<Tarif> tarifs = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(TARIFS_FILE_PATH));
            document.getDocumentElement().normalize();

            NodeList tarifList = document.getElementsByTagName("ObjectTarif");

            for (int i = 0; i < tarifList.getLength(); i++) {

                Tarif tarif = new Tarif();
                Element tarifElement = (Element) tarifList.item(i);

                String codeDepartement = tarifElement.getElementsByTagName("codeDepartement").item(0).getTextContent();
                String idClient = tarifElement.getElementsByTagName("idClient").item(0).getTextContent();
                String idClientHeritage = tarifElement.getElementsByTagName("idClientHeritage").item(0).getTextContent();
                BigDecimal montant = new BigDecimal(tarifElement.getElementsByTagName("montant").item(0).getTextContent());
                Integer zone = Integer.parseInt(tarifElement.getElementsByTagName("zone").item(0).getTextContent());

                if (!idClient.isEmpty()) {
                    tarif.setIdClient(Integer.parseInt(idClient));
                }else {
                    tarif.setIdClient(0);
                }

                if (!idClientHeritage.isEmpty()) {
                    tarif.setIdClientHeritage(Integer.parseInt(idClientHeritage));
                }else {
                    tarif.setIdClientHeritage(0);
                }


                tarif.setZone(zone);
                tarif.setMontant(montant);
                tarif.setCodeDepartement(codeDepartement);


                //Tarif tarif = new Tarif(idClient,idClientHeritage,codeDepartement, montant, zone);
                tarifs.add(tarif);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tarifs;
    }

}
