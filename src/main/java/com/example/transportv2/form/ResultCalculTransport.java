package com.example.transportv2.form;

import com.example.transportv2.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor @AllArgsConstructor @Data
public class ResultCalculTransport {

    private double montantHTTarif;
    private double taxeAAppliquer;
    private double montantHTTotal;

    private Client expediteur;
    private Client destinataire;

}
