package com.example.monetaryamount;

import com.fasterxml.jackson.databind.module.SimpleModule;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.javamoney.moneta.function.MonetaryOperators;
import org.javamoney.moneta.spi.MonetaryConfig;
import org.javamoney.moneta.spi.RoundedMoneyAmountFactory;
import org.javamoney.moneta.spi.RoundedMoneyAmountFactoryProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import static com.example.monetaryamount.MoneyUtils.euro;

@SpringBootApplication
@Configuration
public class MonetaryamountApplication {

	@Bean
	public SimpleModule monetaryAmountModule() {
		// sérialisation/désérialisation des Monetary Amount
		SimpleModule module = new SimpleModule();
		module.addSerializer(MonetaryAmount.class, new DefaultMonetaryAmountSerializer());
		module.addDeserializer(MonetaryAmount.class, new DefaultMonetaryAmountDeserializer());
		return module;
	}

	public static void main(String[] args) {

		var defaultEuro = euro();
		
		var montantTTC = MoneyUtils.amount(5.00d, "EUR");
		
		var euro3Digits = CurrencyUnitBuilder.of(defaultEuro.getCurrencyCode(), defaultEuro.getContext()).setNumericCode(defaultEuro.getNumericCode()).setDefaultFractionDigits(3).build();
		
		var montantHT = MoneyUtils.amount(montantTTC, 3).divide(1.10);

		// MonetaryConfig.setValue("org.javamoney.moneta.useJDKdefaultFormat", "true");
		
		// arrondi le nombre a ce qui est attendu par la devise, par défaut, le nombre n'est pas arrondi !
		montantHT = Monetary.getDefaultRounding().apply(montantHT);

		System.out.println(montantHT);
		// var result = montantHT.multiply(1.10);

		// je le traduis en euros (avec seulement 2 décimales).
		// var resultInEuros = Monetary.getDefaultAmountFactory().setCurrency(euro()).setNumber(result.getNumber()).create();

		// MonetaryAmount amount = Monetary.getDefaultRounding().apply(result);

		// MonetaryAmount amount = Monetary.getRounding(result.getCurrency()).apply(result);
		
		
		SpringApplication.run(MonetaryamountApplication.class, args);
	}
}
