package com.example.umc_insider.dto.request;

import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.Markets;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostGoodsReq {
    private String title;
    private String price;
    private Integer rest;
    private String shelf_life;
    private Long userIdx;
    private String name;
    private Long categoryId;
    private String weight;

}
