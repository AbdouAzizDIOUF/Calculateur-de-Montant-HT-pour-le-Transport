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
public class ConditionTaxation {

    private Integer idClient;
    private BigDecimal taxePortDu;
    private BigDecimal taxePortPaye;
    private boolean useTaxePortDuGenerale;
    private boolean useTaxePortPayeGenerale;
}