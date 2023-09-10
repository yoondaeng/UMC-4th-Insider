package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserByIdRes {
    private String nickname;
    private String userId;
    private String pw;
    private String email;
    private Integer zipCode;
    private String detailAddress;
    private String img;
    private Integer sellerOrBuyer;
    private Long registerNum;

}
