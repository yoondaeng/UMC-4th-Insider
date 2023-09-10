//package com.example.umc_insider.service;
//
//import com.fasterxml.jackson.databind.JsonNode;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.server.reactive.HttpHandler;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//
//@Service
//public class GoogleLoginService {
//    private final Environment env;
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    public GoogleLoginService(Environment env) {
//        this.env = env;
//    }
//    public void socialLogin(String code, String registrationId) {
//        String accessToken = getAccessToken(code, registrationId);
//        System.out.println("accessToken = " + accessToken);
//    }
//
//    private String getAccessToken(String authorizationCode, String registrationId) {
//        String clientId = env.getProperty("oauth2." + registrationId + ".client-id");
//        String clientSecret = env.getProperty("oauth2." + registrationId + ".client-secret");
//        String redirectUri = env.getProperty("oauth2." + registrationId + ".redirect-uri");
//        String tokenUri = env.getProperty("oauth2." + registrationId + ".token-uri");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", authorizationCode);
//        params.add("client_id", clientId);
//        params.add("client_secret", clientSecret);
//        params.add("redirect_uri", redirectUri);
//        params.add("grant_type", "authorization_code");
//
//        org.springframework.http.HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//
//        HttpEntity entity = new HttpEntity(params, headers);
//
//        ResponseEntity<JsonNode> responseNode = restTemplate.exchange(tokenUri, HttpMethod.POST, entity, JsonNode.class);
//        JsonNode accessTokenNode = responseNode.getBody();
//        return accessTokenNode.get("access_token").asText();
//    }
//}
