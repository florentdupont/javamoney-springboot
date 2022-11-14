package com.example.monetaryamount;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.money.CurrencyContextBuilder;
import java.util.List;

import static com.example.monetaryamount.MoneyUtils.amount;
import static com.example.monetaryamount.MoneyUtils.euro;

@RequiredArgsConstructor
@Service
public class ProductService {
    
    private final ProductRepository repository; 
    
    @Transactional 
    public Product createProduct() {
        
        var product = new Product();
        product.setCode("PROD1");
        product.setLibelle("Produit 1");
        product.setMontantTTC(amount(100, euro()));
       
        /*
             80   X
            100   18
        */             
        
        // on veut pouvoir créer un montant avec 3 décimales alors que la monnaie n'en propose normalement
        // que 2.
        
        var defaultEuro = euro(); 
        var euro3Digits = CurrencyUnitBuilder.of(defaultEuro.getCurrencyCode(), defaultEuro.getContext()).setNumericCode(defaultEuro.getNumericCode()).setDefaultFractionDigits(3).build();
       
        
        // https://www.lucca.fr/magazine/finances/notes-de-frais/bizarrerie-fiscale-notes-de-frais
        // https://www.evoliz.com/blog/421-20200916-les-mysteres-de-l-arrondi-tva-sur-facture.html
        // la compta des écarts de réglements ; 
        // d'un point de vue comptable, un montant HT devrait malgré tout être sur 2 décimales.
//        product.setMontantHT(montantHT);

        
        var montantHT = MoneyUtils.amount(4.545, euro3Digits);
        montantHT.multiply(1.10);
        
        return repository.save(product);
        
    }

    public List<Product> getProducts() {
        return repository.findFromMonetaryAmount();
    }
}
