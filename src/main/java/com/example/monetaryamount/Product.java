package com.example.monetaryamount;


import lombok.Data;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.TypeDef;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@TypeDef(name = "persistentMoneyAmountAndCurrency", defaultForType = MonetaryAmount.class, typeClass = PersistentMonetaryAmount.class)
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column
    private String code;
    
    @Column
    private String libelle;

    // il est possible de forcer la taille d'un champ en indiquant explicitement son format
    @Columns(columns = {@Column(name = "montant_ttc_devise", length = 3),
                        @Column(name = "montant_ttc_valeur", columnDefinition = "numeric(11,2)")})
    private MonetaryAmount montantTTC;

    // il est possible de forcer la taille d'un champ en indiquant explicitement son format
    @Columns(columns = {@Column(name = "montant_ht_devise", length = 3),
            @Column(name = "montant_ht_valeur", columnDefinition = "numeric(12,3)")})
    private MonetaryAmount montantHT;
    
}
