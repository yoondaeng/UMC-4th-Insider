package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Messages;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetMessagesRes {
    private Long id;
    private String content;
    private boolean readOrNot;
    private Timestamp createdAt;

    private Users senderId;
    private Long messgesId;

    public GetMessagesRes(Long id, String content, Users user, Long messgesId, Timestamp createdAt){
        this.id = id;
        this.content = content;
        this.senderId = user;
        this.messgesId = messgesId;
        this.createdAt = createdAt;
    }
}
