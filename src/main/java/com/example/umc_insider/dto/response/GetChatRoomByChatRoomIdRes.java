package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetChatRoomByChatRoomIdRes {
    private Long sellerId;
    private Long buyerId;
    private Integer status;
    private Long goodsOrExchangesId;
    private Boolean sellerOrNot;
    private Boolean buyerOrNot;
    private Timestamp createdAt;
}
