package com.example.foodfindsbackend.analysis;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CodeAnalysisService {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_API_KEY = "OPEN_AI_KEY"; // add key here

    private final RestTemplate restTemplate;

    public CodeAnalysisService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String analyzeCode(String codeSnippet) {
        String model = "gpt-3.5-turbo"; // Using GPT-3.5

        // Create the request body with code snippet
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", new Object[] {
                Map.of("role", "system", "content", "You are an expert code reviewer and help detect code smells in Java code."),
                Map.of("role", "user", "content", codeSnippet)
        });

        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 150);

        // Send POST request
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                return responseBody;
            } else {
                return "Error: " + response.getStatusCode();
            }
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
