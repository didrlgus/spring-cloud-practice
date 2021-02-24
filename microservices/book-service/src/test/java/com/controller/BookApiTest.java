package com.controller;

import com.config.MockMvcTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.apache.http.HttpStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@MockMvcTest
public class BookApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

//    @DisplayName("책 추가 테스트")
//    @Test
//    public void addBookTest() {
//
//        mockMvc.perform(post("/books")
//                .contentType(APPLICATION_JSON).content(objectMapper.writeValueAsString(bookRequestDto)))
//                .andReturn().getResponse();
//    }



    private int callAPIAndGetStatusCode(String url, String jwt) throws Exception {
        return mockMvc.perform(get(url)
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn().getResponse().getStatus();
    }
}
