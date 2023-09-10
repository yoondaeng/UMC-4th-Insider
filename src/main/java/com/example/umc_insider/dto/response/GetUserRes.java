package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private Long id;
    private String userId;
    private String nickname;
    private String pw;
    private String email;
    private Address address; // fk
    private Integer sellerOrBuyer;

}
