package com.example.monetaryamount;

import lombok.RequiredArgsConstructor;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.util.List;

import static com.example.monetaryamount.MoneyUtils.euro;

@RequiredArgsConstructor
@RestController
public class ProductController {
    
    private final ProductService service;
    
    @PostMapping("/products")
    public String createProduct() {
        service.createProduct();
        return "OK";
        
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return service.getProducts();
        
    }
    
    @GetMapping("/calc") 
    public MonetaryAmount calc() {
        var defaultEuro = euro();
        var euro3Digits = CurrencyUnitBuilder.of(defaultEuro.getCurrencyCode(), defaultEuro.getContext()).setNumericCode(defaultEuro.getNumericCode()).setDefaultFractionDigits(3).build();
        var montantHT = MoneyUtils.amount(4.545, euro3Digits);
        var result = montantHT.multiply(1.10);
        
        // je le traduis en euros (avec seulement 2 décimales).
        // var resultInEuros = Monetary.getDefaultAmountFactory().setCurrency(euro()).setNumber(result.getNumber()).create();
        
        // MonetaryAmount amount = Monetary.getDefaultRounding().apply(result);

        MonetaryAmount amount = Monetary.getRounding(result.getCurrency()).apply(result);
       
        
        
        
        return amount;
        
    }
}
