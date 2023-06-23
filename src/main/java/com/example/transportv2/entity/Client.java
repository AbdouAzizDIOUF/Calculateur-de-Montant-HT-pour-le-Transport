package com.example.transportv2.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Client {

    private Integer idClient;
    private String raisonSociale;
    private String codePostal;
    private String ville;
}