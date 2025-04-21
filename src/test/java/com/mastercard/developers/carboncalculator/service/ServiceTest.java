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

    @BeforeEach
    void setUp() throws Exception {
        when(apiClient.buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(), anyMap(), anyMap(),
                any(), any())).thenReturn(mock(Call.class));
    }

    @Test
    void calculateFootprints() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(
                new ApiResponse<>(200, new HashMap<>(), transactionFootprints()));

        List<TransactionFootprintData> transactionFootprints = environmentalImpactService.calculateFootprints(
                transactions());

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(transactionFootprints);
    }

    @Test
    void getSupportedCurrencies() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(
                new ApiResponse<>(200, new HashMap<>(), currencies()));
        List<Currency> currencies = supportedParametersService.getSupportedCurrencies();

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(currencies);
    }

    @Test
    void getSupportedMerchantCategories() throws Exception {

        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(
                new ApiResponse<>(200, new HashMap<>(), merchantCategories()));

        List<MerchantCategory> merchantCategories = supportedParametersService.getSupportedMerchantCategories();

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(merchantCategories);
    }

    @Test
    void serviceProvider() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(
                new ApiResponse<>(201, new HashMap<>(), MockData.serviceProvider()));

        ServiceProvider serviceProvider = serviceProviderService.getServiceProvider();

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(serviceProvider);
    }

    @Test
    void serviceProviderErrorScenario() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenThrow(new ApiException(404, new HashMap<>(),
                getErrorResponseBody(
                        "ACCOUNT_NOT_FOUND",
                        "We cannot find the account which you are using to access this service. Kindly register your account or contact your Mastercard associate if you have already registered with us earlier.",
                        false,
                        "")));


        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> serviceProviderService.getServiceProvider());

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void updateServiceProviderErrorScenario() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenThrow(new ApiException(404, new HashMap<>(),
                getErrorResponseBody(
                        "INVALID_REQUEST_PARAMETER",
                        "One of the request parameters is either invalid or is missing, try again with the correct request",
                        false,
                        "supportedAccountRange must match \\\"^[\\\\d\\\\,]{1,}\\\"")));


        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> serviceProviderService.updateServiceProvider(serviceProviderConfig));

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void updateProvider() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenReturn(
                new ApiResponse<>(200, new HashMap<>(), MockData.serviceProvider()));

        ServiceProviderConfig serviceProviderConfig = new ServiceProviderConfig();
        serviceProviderConfig.setCustomerName("Customer1");

        ServiceProvider serviceProvider = serviceProviderService.updateServiceProvider(serviceProviderConfig);

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        assertNotNull(serviceProvider);
    }

    @Test
    void calculateFootprintsErrorScenario() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenThrow(new ApiException(400, new HashMap<>(),
                getErrorResponseBody(
                        "INVALID_REQUEST_PARAMETER",
                        "One of the request parameters is invalid, try again with correct request.",
                        false,
                        "carbonIndexCalculation.transactions[0].mcc: size must be between 1 and 4")));

        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> environmentalImpactService.calculateFootprints(
                        invalidTransactionRequest()));

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void supportedCurrenciesErrorScenario() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenThrow(new ApiException(400, new HashMap<>(),
                getErrorResponseBody(
                        "'UNSUPPORTED_CURRENCY'",
                        "The currency in the request is not supported, try again with a different one.",
                        false,
                        "")));

        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> supportedParametersService.getSupportedCurrencies());

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

    @Test
    void supportedMCCErrorScenario() throws Exception {
        when(apiClient.execute(any(Call.class), any(Type.class))).thenThrow(new ApiException(400, new HashMap<>(),
                getErrorResponseBody(
                        "'UNSUPPORTED_MCC'",
                        "The mcc in the request is not supported, try again with a different one.",
                        false,
                        "")));

        ApiException apiException = Assertions.assertThrows(ApiException.class,
                () -> supportedParametersService.getSupportedMerchantCategories());

        verify(apiClient, atMostOnce()).buildCall(anyString(), anyString(), anyList(), anyList(), any(), anyMap(),
                anyMap(), anyMap(), any(), any());
        verify(apiClient, atMostOnce()).execute(any(Call.class), any(Type.class));

        Assertions.assertNotNull(apiException.getResponseBody());
    }

}