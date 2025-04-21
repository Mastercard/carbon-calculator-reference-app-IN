/*
 *  Copyright (c) 2021-2025 Mastercard
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mastercard.developers.carboncalculator.controller;

import com.mastercard.developers.carboncalculator.service.EnvironmentalImpactService;
import com.mastercard.developers.carboncalculator.service.SupportedParametersService;
import com.mastercard.developers.carboncalculator.service.ServiceProviderService;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.mastercard.developers.carboncalculator.exception.CustomExceptionHandler.getErrorObjectResponseEntity;


/**
 * This controller class exposes the following endpoints
 * 1. /transaction-footprints
 * 2. /supported-currencies
 * 3. /supported-mccs
 * 4. /service-provider
 * <p>
 * Issuer can consume these endpoints directly through their web or mobile application or add their implementation on top of this.
 */
@RestController
@RequestMapping("/demo")
public class CarbonCalculatorController {

    private final EnvironmentalImpactService environmentalImpactService;
    private final SupportedParametersService supportedParametersService;
    private final ServiceProviderService serviceProviderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(CarbonCalculatorController.class);

    public CarbonCalculatorController(EnvironmentalImpactService environmentalImpactService, SupportedParametersService supportedParametersService, ServiceProviderService serviceProviderService) {
        this.environmentalImpactService = environmentalImpactService;
        this.supportedParametersService = supportedParametersService;
        this.serviceProviderService = serviceProviderService;
    }

    @PostMapping("/transaction-footprints")
    public ResponseEntity<Object> calculateFootprints(@RequestBody List<TransactionData> mcTransactions) {

        List<TransactionFootprintData> footprintData = null;
        try {
            footprintData = environmentalImpactService.calculateFootprints(mcTransactions);
        } catch (ApiException exception) {
            LOGGER.error("transaction-footprints apiException : {}", exception.getResponseBody());
            return getErrorObjectResponseEntity(exception);
        }
        return ResponseEntity.ok(footprintData);

    }

    @GetMapping("/supported-currencies")
    public ResponseEntity<Object> getSupportedCurrencies() {
        List<Currency> currencyList = null;
        try {
            currencyList = supportedParametersService.getSupportedCurrencies();
        } catch (ApiException exception) {
            LOGGER.error("supported-currencies apiException : {}", exception.getResponseBody());
            return getErrorObjectResponseEntity(exception);
        }
        return ResponseEntity.ok(currencyList);
    }

    @GetMapping("/supported-mccs")
    public ResponseEntity<Object> getSupportedMerchantCategories() {
        List<MerchantCategory> merchantCategories = null;
        try {
            merchantCategories = supportedParametersService.getSupportedMerchantCategories();
        } catch (ApiException exception) {
            LOGGER.error("supported-mccs apiException : {}", exception.getResponseBody());
            return getErrorObjectResponseEntity(exception);
        }
        return ResponseEntity.ok(merchantCategories);
    }

    @GetMapping("/service-providers")
    public ResponseEntity<Object> getServiceProvider() {
        ServiceProvider serviceProvider = null;
        try {
            serviceProvider = serviceProviderService.getServiceProvider();
        } catch (ApiException exception) {
            LOGGER.error("Get service-providers apiException : {}", exception.getResponseBody());
            return getErrorObjectResponseEntity(exception);
        }
        return ResponseEntity.ok(serviceProvider);
    }

    @PutMapping("/service-providers")
    public ResponseEntity<Object> updateServiceProvider(@RequestBody ServiceProviderConfig serviceProviderConfig) {

        ServiceProvider serviceProvider = null;

        try {
            serviceProvider = serviceProviderService.updateServiceProvider(serviceProviderConfig);
        } catch (ApiException exception) {
            LOGGER.error("service-providers apiException : {}", exception.getResponseBody());
            return getErrorObjectResponseEntity(exception);
        }
        return ResponseEntity.ok(serviceProvider);
    }
}
