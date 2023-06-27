package com.example.transportv2.controller;


import com.example.transportv2.entity.*;
import com.example.transportv2.service.DataLoaderService;
import com.example.transportv2.service.TransportCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author diouf
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TransportController {
    private TransportCalculator transportCalculator;

    @Autowired
    private DataLoaderService dataLoaderService;

    @Autowired
    public TransportController(TransportCalculator transportCalculator) {
        this.transportCalculator = transportCalculator;
    }

    @GetMapping("/calcul-montant")
    public ResultCalcul calculerMontantTransport(@RequestParam int idExp, @RequestParam int idDest,
                                                 @RequestParam int nombreColis, @RequestParam BigDecimal poids,
                                                 @RequestParam boolean portPaye
                                                 ) {


        return this.transportCalculator.calculateTransportCost(idExp, idDest, nombreColis, poids, portPaye);
    }


    @GetMapping("/localites")
    public List<Localite> getLocalites() {
        return this.dataLoaderService.loadLocalites();
    }


    @GetMapping("/clients")
    public List<Client> getClients() {
        return this.dataLoaderService.loadClients();
    }

    @GetMapping("/tarifs")
    public List<Tarif> getTarifs() {
        return this.dataLoaderService.loadTarifs();
    }

    @GetMapping("/ctaxtations")
    public List<ConditionTaxation> getTaxtations() {
        return this.dataLoaderService.loadConditionsTaxations();
    }



}
