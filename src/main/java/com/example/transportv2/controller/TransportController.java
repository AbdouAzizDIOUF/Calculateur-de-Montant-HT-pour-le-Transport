package com.example.transportv2.controller;


import com.example.transportv2.entity.ResultCalcul;
import com.example.transportv2.service.TransportCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author diouf
 */
@RestController
public class TransportController {
    private TransportCalculator transportCalculator;

    @Autowired
    public TransportController(TransportCalculator transportCalculator) {
        this.transportCalculator = transportCalculator;
    }

    @GetMapping("/calcul-montant")
    public ResultCalcul calculerMontantTransport(@RequestParam int idExp,
                                                 @RequestParam int idDest,
                                                 @RequestParam int nombreColis,
                                                 @RequestParam BigDecimal poids,
                                                 @RequestParam boolean portPaye
                                                 ) {


        return this.transportCalculator.calculateTransportCost(idExp, idDest, nombreColis, poids, portPaye);
    }
}
