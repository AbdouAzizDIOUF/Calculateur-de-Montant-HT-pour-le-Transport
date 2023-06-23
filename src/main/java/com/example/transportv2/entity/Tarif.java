package com.example.transportv2.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @author diouf
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Tarif {

    private Integer idClient;
    private Integer idClientHeritage;
    private String codeDepartement;
    private BigDecimal montant;
    private Integer zone;
}