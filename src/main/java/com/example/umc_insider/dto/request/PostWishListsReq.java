package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostWishListsReq {
    public Long userId;
    public Long goodsOrExchangesId;
    public Integer status; // 0: goods, 1: exchanges
}
