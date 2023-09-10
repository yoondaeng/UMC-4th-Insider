package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class PostExchangesRes {
    private Long id;
    private String title;
    private String imageUrl;
    private String name;
    private Integer count;
    private String wantItem;
    private String weight;
    private String shelfLife;
    private Timestamp createdAt;
    private Long categoryId;
    private Users user;
    private Integer zipCode;
    private String detail;

}
