package com.mastercard.developers.carboncalculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mastercard.developers.carboncalculator.configuration.ApiConfiguration;
import com.mastercard.developers.carboncalculator.service.EnvironmentalImpactService;
import com.mastercard.developers.carboncalculator.service.ServiceProviderService;
import com.mastercard.developers.carboncalculator.service.SupportedParametersService;
import org.junit.jupiter.api.Test;
import org.openapitools.client.ApiException;
import org.openapitools.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.mastercard.developers.carboncalculator.service.MockData.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class CarbonCalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private Gson gson ;

    @MockBean
    private EnvironmentalImpactService environmentalImpactService;

    @MockBean
    private ApiConfiguration apiConfiguration;

    @MockBean
    private SupportedParametersService supportedParametersService;

    @MockBean
    private ServiceProviderService serviceProviderApi;

    @MockBean
    private ServiceProviderConfig serviceProviderConfig;

    ApiException apiException = new ApiException(400, "Bad Request");

    @Test
    void calculateFootprintsWithMccRequest() throws Exception {

        when(environmentalImpactService.calculateFootprints(any())).thenReturn(
                transactionFootprints());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(transactions());

        MvcResult mvcResult =this.mockMvc.perform(post("/demo/transaction-footprints").contentType(
                MediaType.APPLICATION_JSON).content(
              jsonContent)).andExpect(
                status().isOk()).andReturn();


        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);

    }

    @Test
    void calculateFootprintsWithException() throws Exception {

        when(environmentalImpactService.calculateFootprints(any())).thenThrow(
                apiException);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(transactions());

        MvcResult mvcResult =this.mockMvc.perform(post("/demo/transaction-footprints").contentType(
                MediaType.APPLICATION_JSON).content(
                jsonContent)).andExpect(
                status().isBadRequest()).andReturn();


        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);

    }

    @Test
    void getSupportedCurrencies() throws Exception {

        when(supportedParametersService.getSupportedCurrencies()).thenReturn(
                currencies());

        MvcResult mvcResult = this.mockMvc
                .perform(get("/demo/supported-currencies"))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);

    }

    @Test
    void getSupportedCurrenciesException() throws Exception {

        when(supportedParametersService.getSupportedCurrencies()).thenThrow(apiException);

        MvcResult mvcResult = this.mockMvc
                .perform(get("/demo/supported-currencies"))
                .andExpect(status().isBadRequest()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);

    }

    @Test
    void getSupportedMerchantCategories() throws Exception {

        when(supportedParametersService.getSupportedMerchantCategories()).thenReturn(
                merchantCategories());

        MvcResult mvcResult = this.mockMvc
                .perform(get("/demo/supported-mccs"))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);

    }

    @Test
    void getSupportedMerchantCategoriesException() throws Exception {

        when(supportedParametersService.getSupportedMerchantCategories()).thenThrow(
                apiException);

        MvcResult mvcResult = this.mockMvc
                .perform(get("/demo/supported-mccs"))
                .andExpect(status().isBadRequest()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);

    }

    @Test
    void getServiceProvider() throws Exception {
        when(serviceProviderApi.getServiceProvider()).thenReturn(serviceProvider());

        MvcResult mvcResult = this.mockMvc
                .perform(get("/demo/service-providers"))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
    }

    @Test
    void getServiceProviderException() throws Exception {
        when(serviceProviderApi.getServiceProvider()).thenThrow(apiException);

        MvcResult mvcResult = this.mockMvc
                .perform(get("/demo/service-providers"))
                .andExpect(status().isBadRequest()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
    }

    @Test
    void updateServiceProvider() throws Exception {
        ServiceProviderConfig providerConfig = new ServiceProviderConfig();
        providerConfig.setCustomerName("customerName");
        providerConfig.setSupportedAccountRange("5425");
        when(serviceProviderApi.updateServiceProvider(providerConfig)).thenReturn(serviceProvider());

        MvcResult mvcResult = this.mockMvc
                .perform(put("/demo/service-providers").contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(providerConfig)))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
    }

    @Test
    void updateServiceProviderException() throws Exception {
        ServiceProviderConfig providerConfig = new ServiceProviderConfig();
        providerConfig.setCustomerName("customerName");
        providerConfig.setSupportedAccountRange("5425");
        when(serviceProviderApi.updateServiceProvider(providerConfig)).thenThrow(apiException);

        MvcResult mvcResult = this.mockMvc
                .perform(put("/demo/service-providers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(providerConfig)))
                .andExpect(status().isBadRequest()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertNotNull(response);
    }

}
