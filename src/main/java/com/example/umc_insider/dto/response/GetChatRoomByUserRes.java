package com.example.umc_insider.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@AllArgsConstructor
public class GetChatRoomByUserRes {
    private Long chatRoomId;
    private String otherNickName;
    private String lastMessage;
    private Timestamp createdAt;
    private Long goodsId;
    private String otherImgUrl;
}
