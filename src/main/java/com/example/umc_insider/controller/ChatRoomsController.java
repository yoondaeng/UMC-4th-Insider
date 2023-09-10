package com.example.umc_insider.controller;

import com.amazonaws.Response;
import com.amazonaws.services.kms.model.NotFoundException;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.domain.Messages;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.dto.request.PostChatRoomsReq;
import com.example.umc_insider.dto.response.*;
import com.example.umc_insider.repository.ChatRoomsRepository;
import com.example.umc_insider.repository.MessagesRepository;
import com.example.umc_insider.service.ChatRoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chatRooms")
public class ChatRoomsController {
    private final ChatRoomsService chatRoomsService;
    private final ChatRoomsRepository chatRoomsRepository;
    private final MessagesRepository messagesRepository;

    @Autowired
    public ChatRoomsController(ChatRoomsService chatRoomsService, ChatRoomsRepository chatRoomsRepository, MessagesRepository messagesRepository){
        this.chatRoomsRepository = chatRoomsRepository;
        this.messagesRepository = messagesRepository;
        this.chatRoomsService = chatRoomsService;
    }

    // 새로운 채팅방 생성하기
    @PostMapping("/create")
    public BaseResponse<PostChatRoomsRes> createChatRoom(@RequestBody PostChatRoomsReq postChatRoomsReq) throws BaseException {
        PostChatRoomsRes response = chatRoomsService.createChatRooms(postChatRoomsReq);
        return new BaseResponse<>(response);
    }

    // 채팅방에 참가한 유저 목록 조회하기
    @GetMapping("/{chatRoomId}/users")
    public ResponseEntity<List<Users>> getUsersInChatRoom(@PathVariable Long chatRoomId) {
        List<Users> users = chatRoomsService.getUsersInChatRoom(chatRoomId);
        return ResponseEntity.ok(users);
    }

    // 유저의 채팅방 목록 조회
    @GetMapping("/{id}")
    public ResponseEntity<List<GetChatRoomByUserRes>>  getChatRoomByUser(@PathVariable("id") Long userId) {
        List<GetChatRoomByUserRes> chatRoomByUserResList = chatRoomsService.findChatRoomByUserId(userId);
        return ResponseEntity.ok(chatRoomByUserResList);
    }

    // 각 유저가 채팅방에서 구매완료
    @PutMapping("/purchase/{chatRoomId}/{id}")
    public ResponseEntity<ChatRooms> purchase(@PathVariable Long chatRoomId, @PathVariable Long id) {
        ChatRooms chatRooms = chatRoomsRepository.findChatRoomsById(chatRoomId);

        if(chatRooms.getSeller().getId().equals(id) && chatRooms.getSeller_or_not().booleanValue() != true){
            chatRooms.setSeller_or_not(true);
        }
        else if(chatRooms.getBuyer().getId().equals(id) && chatRooms.getBuyer_or_not() != true){
            chatRooms.setBuyer_or_not(true);
        }
        ChatRooms updatedChatRoom = chatRoomsRepository.save(chatRooms);
        return new ResponseEntity<>(updatedChatRoom, HttpStatus.OK);
    }

    // 유저의 구매, 판매, 교환 목록 조회
    @GetMapping("/goods/{id}")
    public ResponseEntity<Map<String, List<GetGoodsRes>>> getGoodsByUser(@PathVariable("id") Long userId) {
        Map<String, List<GetGoodsRes>> result = new HashMap<>();

        List<GetGoodsRes> saleList = chatRoomsService.getSaleByUser(userId);
        List<GetGoodsRes> purchaseList = chatRoomsService.getPurchaseByUser(userId);

        result.put("sale", saleList);
        result.put("purchase", purchaseList);

        return ResponseEntity.ok(result);
    }

    //userId로 exchanges 정보 조회
    @GetMapping("/exchanges/{id}")
    public ResponseEntity<Map<String, List<PostExchangesRes>>> getExchangesByUser(@PathVariable("id") Long userId) {
        Map<String, List<PostExchangesRes>> result = new HashMap<>();

        List<PostExchangesRes> saleList = chatRoomsService.getExchangesMeByUser(userId);
        List<PostExchangesRes> purchaseList = chatRoomsService.getExchangesOtherByUser(userId);

        result.put("Exchange my item", saleList);
        result.put("Exchange your item", purchaseList);

        return ResponseEntity.ok(result);
    }

    // 채팅방 id로 채팅방 조회
    @GetMapping("/info/{chatId}")
    public BaseResponse<GetChatRoomByChatRoomIdRes> getChatRoomByChatRoomId(@PathVariable("chatId") Long chatId){
        GetChatRoomByChatRoomIdRes response = chatRoomsService.getChatRoomByChatRoomId(chatId);
        return new BaseResponse<>(response);
    }



}

