package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostLoginReq {
    private String userId;
    private String pw;
}