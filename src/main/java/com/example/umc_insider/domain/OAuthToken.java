package com.example.umc_insider.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuthToken {
//    private String id_token;
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Long expires_in;
    private Long refresh_token_expires_in;

    @JsonProperty("scope")
    private String scope;
}
