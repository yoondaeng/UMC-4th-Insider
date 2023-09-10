package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostMessagesReq {
    private Long chatRoomId;
    private Long senderId;
    private String content;
}
