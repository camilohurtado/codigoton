package com.codigoton.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Camilo Hurtado
 */
@Service
@RequiredArgsConstructor
public class CodeDecryptConsumer {

    @Value("${decrypt.service.url}")
    private String DECRYPT_SERVICE_URL;
    private final RestTemplate restTemplate;

    public String getDecryptedCode(String code) throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity(DECRYPT_SERVICE_URL + code, String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody();
        }else {
            throw new Exception("Failed to connect to Decrypt service");
        }
    }
}
