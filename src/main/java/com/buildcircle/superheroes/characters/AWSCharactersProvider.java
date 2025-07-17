package com.buildcircle.superheroes.characters;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class AWSCharactersProvider implements CharactersProvider {
    private static final String CharactersUri = "https://s3.eu-west-2.amazonaws.com/build-circle/characters.json";

    private ObjectMapper objectMapper;

    public AWSCharactersProvider(@Autowired ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public CharactersResponse getCharacters() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(CharactersUri))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        CharactersResponse charactersResponse = objectMapper.readValue(response.body(),CharactersResponse.class);

        return charactersResponse;
    }
}
