package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Markets;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostGoodsRes {
    private Long id;
    private String title;
}
