package com.example.umc_insider.dto.request;

import lombok.Getter;

@Getter
public class PutUserProfileReq {
    private Long id;


    public PutUserProfileReq() {
        // 디폴트 생성자
    }

    public PutUserProfileReq(Long id) {
        this.id = id;
    }
}
