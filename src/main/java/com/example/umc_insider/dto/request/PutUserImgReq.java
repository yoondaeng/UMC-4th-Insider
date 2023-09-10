package com.example.umc_insider.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutUserImgReq {
    private long userId;
    private String img_url;
}
