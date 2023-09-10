package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginRes {
    private Long id;
    private String jwt;
    private Integer sellerOrBuyer;

}
