package com.example.restapi.controller;

import com.example.restapi.entities.User;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

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
    void findUsers() {
        ResponseEntity<User[]> response = testRestTemplate.getForEntity("/api/users", User[].class);

        List<User> users = Arrays.asList(response.getBody());
        System.out.println(users);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void finById() {
        
        ResponseEntity<User> response = testRestTemplate.getForEntity("/api/users/1", User.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createUser() {

        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("name","Nicolas");
        userDetailsRequestJson.put("surname","Darvas");
        userDetailsRequestJson.put("registrationDate","1960-06-13");
        userDetailsRequestJson.put("premium",false);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request = new HttpEntity(userDetailsRequestJson.toString(), headers);

        ResponseEntity<User> response = testRestTemplate.exchange("/api/users", HttpMethod.POST, request , User.class);
        User result = response.getBody();

        assertEquals("Nicolas", result.getName());


    }

    @Test
    void updateUser() {

        createUser();

        JSONObject userDetailsRequestJson = new JSONObject();
        userDetailsRequestJson.put("id",1L);
        userDetailsRequestJson.put("name","Nicolas");
        userDetailsRequestJson.put("surname","Testing update user");
        userDetailsRequestJson.put("registrationDate","1960-06-13");
        userDetailsRequestJson.put("premium",false);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        HttpEntity<String> request = new HttpEntity(userDetailsRequestJson.toString(), headers);

        ResponseEntity<User> response = testRestTemplate.exchange("/api/users", HttpMethod.PUT, request , User.class);
        User result = response.getBody();

        assertEquals("Testing update user", result.getSurname());
    }

    @Test
    void deleteById() {

        createUser();

        ResponseEntity<String> response = testRestTemplate.exchange("/api/users/1", HttpMethod.DELETE, null , String.class);

        assertEquals(204, response.getStatusCodeValue());
    }


    @Test
    void deleteAll() {

        ResponseEntity<String> response = testRestTemplate.exchange("/api/users", HttpMethod.DELETE, null , String.class);

        assertEquals(204, response.getStatusCodeValue());
    }
}