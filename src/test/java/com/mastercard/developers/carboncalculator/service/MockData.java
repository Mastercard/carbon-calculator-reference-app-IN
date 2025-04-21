package com.mastercard.developers.carboncalculator.service;

import org.openapitools.client.model.Currency;
import org.openapitools.client.model.Error;
import org.openapitools.client.model.*;

import java.math.BigDecimal;
import java.util.*;

import static com.mastercard.developers.carboncalculator.util.JSON.serializeErrors;

public class MockData {

    private static final String SOURCE = "Carbon-Calculator";

    public static List<TransactionData> transactions() {
//        List<TransactionData> mcTransactions = new ArrayList<>();
        TransactionData transactionFootprint = (TransactionData) new TransactionData().transactionId("TX-1")
                .mcc("3000").amount(
                        new Amount().currencyCode("EUR").value(new BigDecimal(150)));
        return Collections.singletonList(transactionFootprint);
    }

    public static List<TransactionFootprintData> transactionFootprints() {

        TransactionFootprintData transactionFootprint = (TransactionFootprintData) new TransactionFootprintData().transactionId(
                "TX-1").category(new Category().mainCategory("Transportation").subCategory(
                "Flights").sector(
                "Airlines").sectorCode(
                "505")).mcc("3000").scoreReference("MCC").carbonEmissionInGrams(BigDecimal.valueOf(205688.73)).carbonEmissionInOunces(
                BigDecimal.valueOf(7255.46));

        return Collections.singletonList(transactionFootprint);

    }

    public static List<MerchantCategory> merchantCategories() {

        MerchantCategory category = new MerchantCategory();
        category.mcc("3000").category(
                new Category().mainCategory("Transportation").subCategory(
                        "Flights").sector(
                        "Airlines").sectorCode("505"));
        return Collections.singletonList(category);

    }


    public static List<Currency> currencies() {
        Currency mcCurrency = new Currency().currencyCode("USD");
        return Collections.singletonList(mcCurrency);
    }

    public static ServiceProvider serviceProvider() {
        return new ServiceProvider().clientId("clientId").customerId(
                "customerId").customerName("customerName").status("ACTIVE").supportedAccountRange("5425");
    }

    public static List<TransactionData> invalidTransactionRequest() {
        List<TransactionData> mcTransactions = new ArrayList<>();
        mcTransactions.add((TransactionData) new TransactionData().transactionId("TX-1")
                .mcc("12345").amount(
                        new Amount().currencyCode("EUR").value(new BigDecimal(150))));
        return mcTransactions;
    }

    public static String getErrorResponseBody(String reasonCode, String description, boolean recoverable, String details) {
        Error error = new Error().source(SOURCE).reasonCode(reasonCode).description(description).recoverable(
                recoverable).details(details);
        ErrorWrapper response = new ErrorWrapper().errors(new Errors().addErrorItem(error));
        return serializeErrors(response);
    }
}
