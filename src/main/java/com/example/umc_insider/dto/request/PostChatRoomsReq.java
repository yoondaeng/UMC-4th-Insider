package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostChatRoomsReq {
    private Long sellerId;
    private Long buyerId;
    private Integer status; // 0이면 goods, 1이면 exchanges로
    private Long goodsOrExchangesId;
}
