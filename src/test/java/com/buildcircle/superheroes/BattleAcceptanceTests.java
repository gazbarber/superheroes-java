package com.buildcircle.superheroes;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperheroesApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BattleAcceptanceTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    private String createURLWithPort(String uri) {
        return "http://localhost:" + this.port + uri;
    }

    @Test
    void whenBatmanBattlesJoker() {
        //Given
        HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/battle?hero=Batman&villain=Joker"),
                HttpMethod.GET, entity, String.class);

        //Then
        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
        Assert.assertEquals("{\"name\":\"Joker\",\"score\":8.2,\"type\":\"villain\",\"weakness\":null}", response.getBody());
    }

    @Test
    void whenSupermanBattlesLexLuthor() {
        //Given
        HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());
        //When
        ResponseEntity<String> response = restTemplate.getForEntity(
                createURLWithPort("/battle?hero=Superman&villain=Lex Luthor"),
                String.class);

        //Then
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals("{\"name\":\"Superman\",\"score\":9.6,\"type\":\"hero\",\"weakness\":\"Lex Luthor\"}", response.getBody());
    }

    @Test
    void whenBatmanBattlesSuperman() {
        //Given
        HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/battle?hero=Batman&villain=Superman"),
                HttpMethod.GET, entity, String.class);

        //Then
        Assert.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenBatmanBattlesNoOne() {
        //Given
        HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());

        //When
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/battle?hero=Batman&villain="),
                HttpMethod.GET, entity, String.class);

        //Then
        Assert.assertTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST);
    }
}
