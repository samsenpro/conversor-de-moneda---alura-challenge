package com.example.alurachallenge.conversor;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class MonedaConversor {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD"; // Ejemplo de API
    private static final String API_KEY = "dbdbd1836bce6f8baa777088"; // Tu API Key

    public static Map<String, Double> getExchangeRates() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Authorization", "Bearer " + API_KEY)
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        Map<String, Double> exchangeRates = new HashMap<>();

        try {
            JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
            JsonObject ratesObject = jsonResponse.getAsJsonObject("rates");

            for (Map.Entry<String, com.google.gson.JsonElement> entry : ratesObject.entrySet()) {
                exchangeRates.put(entry.getKey(), entry.getValue().getAsDouble());
            }



        } catch (JsonSyntaxException e) {
            System.out.println("Error al analizar el JSON: " + e.getMessage());
        }

        return exchangeRates;
    }
}
