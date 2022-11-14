package com.example.monetaryamount;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.io.IOException;

import static com.example.monetaryamount.MoneyUtils.monnaie;

/**
 * Représentation sous forme d'un objet {@link MonetaryAmountDto}
 */
public class DefaultMonetaryAmountSerializer extends JsonSerializer<MonetaryAmount> {

    @Override
    public void serialize(MonetaryAmount monetaryAmount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        
        // le montant est arrondi à ce qui est normalement attendu par la monnaie du montant.
        // On applique bien l'arrondi au nombre de décimale porté par le MonetaryAmount.
        // Il est donc tout à fait possible d'avoir un EUR avec 3 décimales si un Currency EUR a été défini avec 3 décimales.
        // A GARDER à l'esprit : la fonction toString s'appuie sur les locales et va formatter forcément en 2 décimales
        var roundedAmountToExpectedCurrency = Monetary.getDefaultRounding().apply(monetaryAmount); 
        
        // le montant est indiqué
        jsonGenerator.writeNumberField("montant", roundedAmountToExpectedCurrency.getNumber().doubleValue());
        jsonGenerator.writeNumberField("decimale", monetaryAmount.getCurrency().getDefaultFractionDigits());
        jsonGenerator.writeStringField("monnaie", monnaie(monetaryAmount));
        jsonGenerator.writeEndObject();

    }
}