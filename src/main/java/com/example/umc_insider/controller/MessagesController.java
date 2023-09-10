package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.domain.Messages;
import com.example.umc_insider.dto.request.PostMessagesReq;
import com.example.umc_insider.dto.request.PostUserReq;
import com.example.umc_insider.dto.response.GetMessagesRes;
import com.example.umc_insider.dto.response.PostMessagesRes;
import com.example.umc_insider.service.ChatRoomsService;
import com.example.umc_insider.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    private final MessagesService messagesService;
    private final ChatRoomsService chatRoomsService;

    @Autowired
    public MessagesController(MessagesService messagesService, ChatRoomsService chatRoomsService) {
        this.messagesService = messagesService;
        this.chatRoomsService = chatRoomsService;
    }

    @PostMapping("/send")
    public BaseResponse<PostMessagesRes> createMessages(@RequestBody PostMessagesReq postMessagesReq) {
        PostMessagesRes response = messagesService.createMessages(postMessagesReq);
        return new BaseResponse<>(response);
    }

    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<GetMessagesRes>> getMessagesInChatRoom(@PathVariable Long chatRoomId) {
        List<GetMessagesRes> messages = messagesService.findMessagesInChatRoom(chatRoomId);
        return ResponseEntity.ok(messages);
    }


}
