package com.cspydo.dronemanager.controller;

import com.cspydo.dronemanager.repository.DroneRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cspydo.dronemanager.model.Drone;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DroneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DroneRepository droneRepository;

    @Test
    public void registerDrone() throws Exception{
        Drone drone = new Drone();
        drone.setSerialNumber(Mockito.anyString());
        drone.setModel(Mockito.anyString());
        drone.setWeightLimit(Mockito.anyDouble());
        drone.setBatteryCapacity(Mockito.anyInt());
        droneRepository.save(drone);
        Assertions.assertThat(drone.getId()).isGreaterThan(0);
    }

    @Test
    public void getDroneBatteryLevel() throws Exception{
        long id=1;
        Drone drone = droneRepository.findById(id).get();
        Assertions.assertThat(drone.getBatteryCapacity()).isBetween(0, 100);
    }

    @Test
    public void getCurrentLoad() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/drone/currentload/1")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result;
        try {
            result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.OK.value(), response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkAvailableDrones() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
        .get("/drone/available")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result;
        try {
            result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response = result.getResponse();
            assertEquals(HttpStatus.OK.value(), response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
