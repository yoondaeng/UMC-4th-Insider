package com.example.umc_insider.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class KakaoProfile {

    public Long id;
    @JsonProperty("connected_at")
    public String connectedAt;
    public Properties properties;
    @JsonProperty("kakao_account")
    public KakaoAccount kakaoAccount;


    public static class Properties {
        @JsonProperty("nickname")
        public String nickname;

    }

    public static class KakaoAccount {
        @JsonProperty("profile_nickname_needs_agreement")
        public Boolean profileNicknameNeedsAgreement;
        public Profile profile;
        @JsonProperty("has_email")
        public Boolean hasEmail;
        @JsonProperty("email_needs_agreement")
        public Boolean emailNeedsAgreement;
        @JsonProperty("is_email_valid")
        public Boolean isEmailValid;
        @JsonProperty("is_email_verified")
        public Boolean isEmailVerified;

        @JsonProperty("email")
        public String email;

    }

    public static class Profile {

        public String nickname;

    }

}