package com.mastercard.developers.carboncalculator.service;

import okhttp3.Call;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.api.EnvironmentalImpactApi;
import org.openapitools.client.api.ServiceProviderApi;
import org.openapitools.client.api.SupportedParametersApi;
import org.openapitools.client.model.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import static com.mastercard.developers.carboncalculator.service.MockData.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {

    @InjectMocks
    private EnvironmentalImpactService environmentalImpactService;

    @InjectMocks
    private SupportedParametersService supportedParametersService;

    @InjectMocks
    private ServiceProviderService serviceProviderService;

    @InjectMocks
    private ServiceProviderConfig serviceProviderConfig;

    @Mock
    private ApiClient apiClient;

    @Mock
    private EnvironmentalImpactApi environmentalImpactApi;

    @Mock
    private SupportedParametersApi supportedParametersApi;

    @Mock
    private ServiceProviderApi serviceProviderApi;

    @Test
    void calculateFootprints() throws Exception {
        when(environmentalImpactApi.footprintsByTransactionData( any())).thenReturn( transactionFootprints());

        List<TransactionFootprintData> transactionFootprints = environmentalImpactService.calculateFootprints(
                transactions());

        verify(environmentalImpactApi, atMostOnce()).footprintsByTransactionData( any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(transactionFootprints);
    }

    @Test
    void getSupportedCurrencies() throws Exception {
        when(supportedParametersApi.getSupportedCurrencies()).thenReturn( currencies());

        List<Currency> currencies = supportedParametersService.getSupportedCurrencies();

        verify(supportedParametersApi, atMostOnce()).getSupportedCurrencies();
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(currencies);
    }

    @Test
    void getSupportedMerchantCategories() throws Exception {

        when(supportedParametersApi.getSupportedMerchantCategories()).thenReturn(merchantCategories());

        List<MerchantCategory> merchantCategories = supportedParametersService.getSupportedMerchantCategories();

        verify(supportedParametersApi, atMostOnce()).getSupportedMerchantCategories();
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(merchantCategories);
    }

    @Test
    void serviceProvider() throws Exception {
        when(serviceProviderApi.getServiceProviderById()).thenReturn( MockData.serviceProvider());

        ServiceProvider serviceProvider = serviceProviderService.getServiceProvider();

        verify(serviceProviderApi, atMostOnce()).getServiceProviderById();
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(serviceProvider);
    }

    @Test
    void serviceProviderErrorScenario() throws Exception {
        when(serviceProviderApi.getServiceProviderById()).thenThrow(new ApiException(404, new HashMap<>(),
                getErrorResponseBody(
                        "ACCOUNT_NOT_FOUND",
                        "We cannot find the account which you are using to access this service. Kindly register your account or contact your Mastercard associate if you have already registered with us earlier.",
                        false,
                        "")));


        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> serviceProviderService.getServiceProvider());

        verify(serviceProviderApi, atMostOnce()).getServiceProviderById();
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void updateServiceProviderErrorScenario() throws Exception {
        when( serviceProviderApi.updateServiceProvider(any())).thenThrow(new ApiException(404, new HashMap<>(),
                getErrorResponseBody(
                        "INVALID_REQUEST_PARAMETER",
                        "One of the request parameters is either invalid or is missing, try again with the correct request",
                        false,
                        "supportedAccountRange must match \\\"^[\\\\d\\\\,]{1,}\\\"")));


        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> serviceProviderService.updateServiceProvider(serviceProviderConfig));

        verify(serviceProviderApi, atMostOnce()).updateServiceProvider(any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void updateProvider() throws Exception {
        when(serviceProviderApi.updateServiceProvider( any())).thenReturn(MockData.serviceProvider());

        ServiceProviderConfig serviceProviderConfig = new ServiceProviderConfig();
        serviceProviderConfig.setCustomerName("Customer1");

        ServiceProvider serviceProvider = serviceProviderService.updateServiceProvider(serviceProviderConfig);

        verify(serviceProviderApi, atMostOnce()).updateServiceProvider(any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(serviceProvider);
    }

    @Test
    void calculateFootprintsErrorScenario() throws Exception {
        when(environmentalImpactApi.footprintsByTransactionData(any())).thenThrow(new ApiException(400, new HashMap<>(),
                getErrorResponseBody(
                        "INVALID_REQUEST_PARAMETER",
                        "One of the request parameters is invalid, try again with correct request.",
                        false,
                        "carbonIndexCalculation.transactions[0].mcc: size must be between 1 and 4")));

        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> environmentalImpactService.calculateFootprints(
                        invalidTransactionRequest()));

        verify(environmentalImpactApi, atMostOnce()).footprintsByTransactionData(any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void supportedCurrenciesErrorScenario() throws Exception {
        when(supportedParametersApi.getSupportedCurrencies()).thenThrow(new ApiException(400, new HashMap<>(),
                getErrorResponseBody(
                        "'UNSUPPORTED_CURRENCY'",
                        "The currency in the request is not supported, try again with a different one.",
                        false,
                        "")));

        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> supportedParametersService.getSupportedCurrencies());

        verify(supportedParametersApi, atMostOnce()).getSupportedCurrencies();
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void supportedMCCErrorScenario() throws Exception {
        when(supportedParametersApi.getSupportedMerchantCategories()).thenThrow(new ApiException(400, new HashMap<>(),
                getErrorResponseBody(
                        "'UNSUPPORTED_MCC'",
                        "The mcc in the request is not supported, try again with a different one.",
                        false,
                        "")));

        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> supportedParametersService.getSupportedMerchantCategories());

        verify(supportedParametersApi, atMostOnce()).getSupportedMerchantCategories();
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

}