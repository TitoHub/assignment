/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.backend.controller;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author pearl
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JukeControllerIntegrationTest {
    
    private static TestRestTemplate restTemplate = new TestRestTemplate();
    
    @LocalServerPort
    private int localServerPort;
    
    
    @Test
    public void when_test_return_paginated_jokeboxes_with_required_parameters(){
        String settingId = "b43f247a-8478-4f24-8e28-792fcfe539f5";
        
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + localServerPort + "/jukeboxes"+"?settingId="+settingId, String.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
    
    @Test
    public void when_test_return_paginated_jokeboxes_without_required_parameters(){
        
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:" + localServerPort + "/jukeboxes", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
