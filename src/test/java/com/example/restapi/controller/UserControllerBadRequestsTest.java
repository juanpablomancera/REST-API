package com.example.restapi.controller;

import com.example.restapi.entities.User;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerBadRequestsTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(){
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void notFinById() {
        ResponseEntity<User> response = testRestTemplate.getForEntity("/api/users/800", User.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void notCreateUser(){
        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("id",80);
        userDetailsRequestJson.put("name","Nicolas");
        userDetailsRequestJson.put("surname","Darvas");
        userDetailsRequestJson.put("registrationDate","1960-06-13");
        userDetailsRequestJson.put("premium",false);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request = new HttpEntity(userDetailsRequestJson.toString(), headers);

        ResponseEntity<User> response = testRestTemplate.exchange("/api/users", HttpMethod.POST, request , User.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void updateUserNullId(){

        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("id",null);
        userDetailsRequestJson.put("name","Nicolas");
        userDetailsRequestJson.put("surname","Testing update user");
        userDetailsRequestJson.put("registrationDate","1960-06-13");
        userDetailsRequestJson.put("premium",false);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request = new HttpEntity(userDetailsRequestJson.toString(), headers);

        ResponseEntity<User> response = testRestTemplate.exchange("/api/users", HttpMethod.PUT, request , User.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    void updateUserNotExistsId(){

        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("id",900);
        userDetailsRequestJson.put("name","Nicolas");
        userDetailsRequestJson.put("surname","Testing update user");
        userDetailsRequestJson.put("registrationDate","1960-06-13");
        userDetailsRequestJson.put("premium",false);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request = new HttpEntity(userDetailsRequestJson.toString(), headers);

        ResponseEntity<User> response = testRestTemplate.exchange("/api/users", HttpMethod.PUT, request , User.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteNotExistsById(){
        ResponseEntity<String> response = testRestTemplate.exchange("/api/users/900", HttpMethod.DELETE, null , String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
