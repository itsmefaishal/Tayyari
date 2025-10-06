package com.AuthServices.Service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;

@Service
public class EmailServiceBrevo {
    private static final String API_URL = "https://api.brevo.com/v3/smtp/email";
	
    @Value("${brevo.api.key}")
    private  String API_KEY;

    public void sendEmail(String to, String subject, String text) {
		 System.out.println("API key" + API_KEY);
		
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> body = Map.of(
                "sender", Map.of("email", "pata51522@gmail.com"),
                "to", new Object[]{Map.of("email", to)},
                "subject", subject,
                "textContent", text
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", API_KEY);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(API_URL, request, String.class);
        System.out.println("Response: " + response.getBody());
    }
}

