package com.example.transportv2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author diouf
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class ResultCalcul implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal montantHTTarif;
    private BigDecimal taxeAAppliquer;
    private BigDecimal montantHTTotal;

    private int idExpediteur;

    private int idDestinataire;

    private Date dateCreation;
}
