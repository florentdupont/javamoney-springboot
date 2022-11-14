package com.example.monetaryamount;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import javax.money.MonetaryAmount;
import java.io.IOException;

public class DefaultMonetaryAmountDeserializer extends JsonDeserializer<MonetaryAmount> {

    @Override
    public MonetaryAmount deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        Double montant = node.get("montant") != null ? node.get("montant").asDouble() : null;
        String monnaie = node.get("monnaie").asText();

        // On reconstruit un montant avec ce qui est normalement attendu. Attention, pour des raisons de sécurité,
        // il est préférable de ne pas reconstruire avec le nombre de décimales passées en paramètres
        // ou alors le limiter
        return MoneyUtils.amount(montant, monnaie);
    }
}