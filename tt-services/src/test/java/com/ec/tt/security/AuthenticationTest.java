package com.ec.tt.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Lazy
@Service
public class AuthenticationTest {
    @Value("${keycloak.realm}")
    String REALM;
    @Value("${keycloak.resource}")
    String CLIENT_ID;

    @Value("${keycloak.credentials.secret}")
    String CLIENT_SECRET;

    public String obtainAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", CLIENT_ID);
        map.add("client_secret", CLIENT_SECRET);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "http://localhost:8080/realms/" + REALM + "/protocol/openid-connect/token",
                HttpMethod.POST,
                request,
                Map.class);
        return (String) response.getBody().get("access_token");
    }
}
