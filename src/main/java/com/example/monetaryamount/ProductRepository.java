package com.example.monetaryamount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /*
     Exemple de requête JPQL utilisant les sous-types mappés.
     Les noms des colonnes sont ceux retournés par le PersistentMoneyAmountAndCurrency.getPropertyNames()
     Attention, sous IntelliJ IDEA, l'inspection "Query Language Check" remonte une erreur car il n'est
     pas capable de voir ces attributs étant donné qu'ils sont mappés dynamiquement.
     Dans ce cas, on suppress le warning via le @SuppressWarnings("JpaQlInspection")
     */
    @SuppressWarnings("JpaQlInspection")
    @Query("select p from Product p where p.montantTTC.currency = 'EUR' and p.montantTTC.amount >= 100")
    List<Product> findFromMonetaryAmount();
}
