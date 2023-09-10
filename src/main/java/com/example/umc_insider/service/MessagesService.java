package com.example.umc_insider.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.internal.eventstreaming.Message;
import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Messages;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostMessagesReq;
import com.example.umc_insider.dto.response.GetMessagesRes;
import com.example.umc_insider.dto.response.PostMessagesRes;
import com.example.umc_insider.repository.ChatRoomsRepository;
import com.example.umc_insider.repository.MessagesRepository;
import com.example.umc_insider.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessagesService {
    private MessagesRepository messagesRepository;
    private ChatRoomsRepository chatRoomsRepository;
    private UserRepository userRepository;

    @Autowired
    public MessagesService(MessagesRepository messagesRepository, ChatRoomsRepository chatRoomsRepository, UserRepository userRepository){
        this.messagesRepository = messagesRepository;
        this.chatRoomsRepository = chatRoomsRepository;
        this.userRepository = userRepository;
    }

    // 채팅 보내기
    public PostMessagesRes createMessages(PostMessagesReq postMessagesReq) {
        Messages messages = new Messages();
        Users users = userRepository.findUsersById(postMessagesReq.getSenderId());
        ChatRooms chatRooms = chatRoomsRepository.findChatRoomsById(postMessagesReq.getChatRoomId());
        messages.setSender(users);
        messages.setChatRoom(chatRooms);
        messages.createMessages(postMessagesReq.getContent());
        messagesRepository.save(messages);
        return new PostMessagesRes(messages.getId());
    }

    public List<GetMessagesRes> findMessagesInChatRoom(Long chatRoomId) {
        ChatRooms chatRooms = chatRoomsRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));
        List<Messages> messagesList = messagesRepository.findByChatRoomIdOrderByCreated_atAsc(chatRoomId);
        List<GetMessagesRes> getMessagesRes = messagesList.stream()
                .map(messages -> new GetMessagesRes(messages.getId(), messages.getContent(), messages.getSender(), messages.getId(), messages.getCreated_at()))
                .collect(Collectors.toList());
        return getMessagesRes;
    }

    // 마지막 메세지
    public Messages getLastMessage(Long chatRoomId) {
        List<Messages> messages = messagesRepository.findByChatRoomIdOrderByCreated_atDesc(chatRoomId);
        return messages.isEmpty() ? null : messages.get(0);
    }


}
