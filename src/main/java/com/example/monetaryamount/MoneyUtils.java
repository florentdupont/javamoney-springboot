package com.example.monetaryamount;

import lombok.experimental.UtilityClass;
import org.javamoney.moneta.CurrencyUnitBuilder;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

@UtilityClass
public class MoneyUtils {

    public static CurrencyUnit euro() {
        return Monetary.getCurrency("EUR");
    }

    public static MonetaryAmount amount(Number amount, String codeMonnaie) {
        return Monetary.getDefaultAmountFactory().setCurrency(Monetary.getCurrency(codeMonnaie)).setNumber(nullToZero(amount)).create();
    }

    /**
     * Créé un montant d'un monnaie, mais en précisant une partie décimale
     * @param amount
     * @param codeMonnaie
     * @param fractionDigits
     * @return
     */
    public static MonetaryAmount amount(Number amount, String codeMonnaie, int fractionDigits) {
        var defaultCurrency = Monetary.getCurrency(codeMonnaie);
        var morePreciseCurrency = CurrencyUnitBuilder.of(defaultCurrency.getCurrencyCode(), defaultCurrency.getContext()).setNumericCode(defaultCurrency.getNumericCode()).setDefaultFractionDigits(fractionDigits).build();                
        return Monetary.getDefaultAmountFactory().setCurrency(morePreciseCurrency).setNumber(nullToZero(amount)).create();
    }

    /**
     * Copie un montant dans un autre, avec un nombre de décimale différents
     * @param fractionDigits
     * @return
     */
    public static MonetaryAmount amount(MonetaryAmount amount, int fractionDigits) {
        var defaultCurrency = amount.getCurrency();
        var morePreciseCurrency = CurrencyUnitBuilder.of(defaultCurrency.getCurrencyCode(), defaultCurrency.getContext()).setNumericCode(defaultCurrency.getNumericCode()).setDefaultFractionDigits(fractionDigits).build();
        return Monetary.getDefaultAmountFactory().setCurrency(morePreciseCurrency).setNumber(nullToZero(amount.getNumber())).create();
    }
    
    public static MonetaryAmount amount(Number amount, CurrencyUnit unit) {
        return Monetary.getDefaultAmountFactory().setCurrency(unit).setNumber(nullToZero(amount)).create();
    }

    public static BigDecimal nullToZero(BigDecimal amount) {
        return amount != null ? amount : BigDecimal.ZERO;
    }

    public static String monnaie(MonetaryAmount monetaryAmount) {
        return monetaryAmount != null ? monetaryAmount.getCurrency().getCurrencyCode() : null;
    }

    public static Number nullToZero(Number amount) {
        return amount != null ? amount : 0;
    }
    
}
