package com.cspydo.dronemanager.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.Assert.assertEquals;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DispatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
	public void createDispatch() throws Exception {
        String payload = "{\r\n    \"drone_id\":1,\r\n    \"medications\":[1,2,3]\r\n}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/dispatch/create")
				.accept(MediaType.APPLICATION_JSON).content(payload)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
}
