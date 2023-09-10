package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class PostWishListsRes {
    private Long wishListId;
    private Long userId;
    private Long goodsOrExchangesId;
    private Timestamp createdAt;
    private Integer status; // 0: goods, 1: exchanges
}
