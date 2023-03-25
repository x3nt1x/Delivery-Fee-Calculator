package com.x3nt1x.calculator.controller;

import com.x3nt1x.calculator.service.DeliveryFeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryFeeController.class)
public class DeliveryFeeControllerTest
{
    @Autowired
    private MockMvc mvc;

    @MockBean
    private DeliveryFeeService service;

    @BeforeEach
    public void setup()
    {
        doReturn("3.5").when(service).calculateDeliveryFee("Tartu", "Car", Optional.empty());
        doReturn("3").when(service).calculateDeliveryFee("Tallinn", "Bike", Optional.of("2023-03-25-12"));
        doReturn("Invalid vehicle!").when(service).calculateDeliveryFee("Tallinn", "Boat", Optional.empty());
        doReturn("Invalid city!").when(service).calculateDeliveryFee("Haapsalu", "Scooter", Optional.empty());
        doReturn("Usage of selected vehicle type is forbidden!").when(service).calculateDeliveryFee("Pärnu", "Bike", Optional.empty());
        doReturn("No weather data available or invalid time format!").when(service).calculateDeliveryFee(eq("Tartu"), eq("Bike"), any());
    }

    @Test
    public void calculateDeliveryFeeTest() throws Exception
    {
        mvc.perform(get("/api/fee/")
                    .param("city", "Tartu")
                    .param("vehicle", "Car")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("3.5"));
    }

    @Test
    public void calculateDeliveryFeeWithProvidedTimeTest() throws Exception
    {
        mvc.perform(get("/api/fee/")
                    .param("city", "Tallinn")
                    .param("vehicle", "Bike")
                    .param("time", "2023-03-25-12")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("3"));
        }

    @Test
    public void calculateDeliveryFeeInvalidVehicleTest() throws Exception
    {
        mvc.perform(get("/api/fee/")
                    .param("city", "Tallinn")
                    .param("vehicle", "Boat")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Invalid vehicle!"));
    }

    @Test
    public void calculateDeliveryFeeInvalidCityTest() throws Exception
    {
        mvc.perform(get("/api/fee/")
                    .param("city", "Haapsalu")
                    .param("vehicle", "Scooter")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Invalid city!"));
    }

    @Test
    public void calculateDeliveryFeeForbiddenVehicleTest() throws Exception
    {
        mvc.perform(get("/api/fee/")
                    .param("city", "Pärnu")
                    .param("vehicle", "Bike")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("Usage of selected vehicle type is forbidden!"));
    }

    @Test
    public void calculateDeliveryFeeMissingDataTest() throws Exception
    {
        mvc.perform(get("/api/fee/")
                    .param("city", "Tartu")
                    .param("vehicle", "Bike")
                    .param("time", "2025-03-25-14")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("No weather data available or invalid time format!"));
    }

    @Test
    public void calculateDeliveryFeeInvalidFormatTest() throws Exception
    {
        mvc.perform(get("/api/fee/")
                    .param("city", "Tartu")
                    .param("vehicle", "Bike")
                    .param("time", "2023-14-25")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().string("No weather data available or invalid time format!"));
    }
}