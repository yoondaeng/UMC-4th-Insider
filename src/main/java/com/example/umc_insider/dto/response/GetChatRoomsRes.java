package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatRoomsRes {
    private Long sellerId;
    private Long buyerId;
}
