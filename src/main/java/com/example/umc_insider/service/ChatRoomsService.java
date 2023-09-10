package com.example.umc_insider.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.domain.*;
import com.example.umc_insider.dto.request.PostChatRoomsReq;
import com.example.umc_insider.dto.response.*;
import com.example.umc_insider.repository.*;
import com.example.umc_insider.utils.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomsService {
    private ChatRoomsRepository chatRoomsRepository;
    private MessagesRepository messagesRepository;
    private UserRepository userRepository;
    private MessagesService messagesService;
    private GoodsRepository goodsRepository;
    private ExchangesRepository exchangesRepository;

    @Autowired
    public ChatRoomsService(UserRepository userRepository, ChatRoomsRepository chatRoomsRepository, MessagesRepository messagesRepository, MessagesService messagesService, GoodsRepository goodsRepository, ExchangesRepository exchangesRepository) {
        this.userRepository = userRepository;
        this.chatRoomsRepository = chatRoomsRepository;
        this.messagesRepository = messagesRepository;
        this.messagesService = messagesService;
        this.goodsRepository = goodsRepository;
        this.exchangesRepository = exchangesRepository;
    }


    // 새로운 채팅방 생성하기
    public PostChatRoomsRes createChatRooms(PostChatRoomsReq postChatRoomsReq) throws BaseException {
        if(postChatRoomsReq.getStatus() == 0){ // goods
            ChatRooms chatRooms = chatRoomsRepository.findBySellerIdAndBuyerIdAndGoodsId(postChatRoomsReq.getSellerId(), postChatRoomsReq.getBuyerId(), postChatRoomsReq.getGoodsOrExchangesId());
            if(chatRooms != null){
                return new PostChatRoomsRes(chatRooms.getId());
            }
            chatRooms = new ChatRooms();
            chatRooms.createChatRooms(postChatRoomsReq.getSellerId(), postChatRoomsReq.getBuyerId(), postChatRoomsReq.getStatus(), postChatRoomsReq.getGoodsOrExchangesId());

            Users seller = userRepository.findById(postChatRoomsReq.getSellerId())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 판매자입니다."));
            Users buyer = userRepository.findById(postChatRoomsReq.getBuyerId())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 구매자입니다."));
            Goods goods = goodsRepository.findById(postChatRoomsReq.getGoodsOrExchangesId())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다."));

            chatRooms.setSeller(seller);
            chatRooms.setBuyer(buyer);
            chatRooms.setGoods(goods);
            chatRooms.setExchanges(null);

            chatRoomsRepository.save(chatRooms);
            return new PostChatRoomsRes(chatRooms.getId());
        } else{ // exchanges
            ChatRooms chatRooms = chatRoomsRepository.findBySellerIdAndBuyerIdAndExchangesId(postChatRoomsReq.getSellerId(), postChatRoomsReq.getBuyerId(), postChatRoomsReq.getGoodsOrExchangesId());
            if(chatRooms != null){
                return new PostChatRoomsRes(chatRooms.getId());
            }
            chatRooms = new ChatRooms();
            chatRooms.createChatRooms(postChatRoomsReq.getSellerId(), postChatRoomsReq.getBuyerId(), postChatRoomsReq.getStatus(), postChatRoomsReq.getGoodsOrExchangesId());

            Users seller = userRepository.findById(postChatRoomsReq.getSellerId())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 판매자입니다."));
            Users buyer = userRepository.findById(postChatRoomsReq.getBuyerId())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 구매자입니다."));
            Exchanges exchanges = exchangesRepository.findById(postChatRoomsReq.getGoodsOrExchangesId())
                    .orElseThrow(() -> new NotFoundException("존재하지 않는 상품입니다."));

            chatRooms.setSeller(seller);
            chatRooms.setBuyer(buyer);
            chatRooms.setGoods(null);
            chatRooms.setExchanges(exchanges);

            chatRoomsRepository.save(chatRooms);
            return new PostChatRoomsRes(chatRooms.getId());
        }
    }

    // 채팅방에 참가한 유저 목록 조회하기
    public List<Users> getUsersInChatRoom(Long chatRoomId) {
        ChatRooms chatRooms = chatRoomsRepository.findById(chatRoomId)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 채팅방입니다."));
        List<Users> users = new ArrayList<>();
        users.add(chatRooms.getSeller());
        users.add(chatRooms.getBuyer());
        return users;
    }

    // 유저의 채팅방 목록 조회
    public List<GetChatRoomByUserRes> findChatRoomByUserId(Long id) {
        List<ChatRooms> chatRoomsList = chatRoomsRepository.findBySellerIdOrBuyerId(id);
        Users user = userRepository.findUsersById(id);

        return chatRoomsList.stream().map(chatRoom -> {
            Long chatRoomId = chatRoom.getId();
            Long otherUserId = (chatRoom.getSeller().getId().equals(id)) ? chatRoom.getBuyer().getId() : chatRoom.getSeller().getId();

            Users otherUser = userRepository.findUsersById(otherUserId);
            String otherNickName = otherUser.getNickname(); // 상대방 닉네임

            Messages lastMessageObj = messagesService.getLastMessage(chatRoomId);

            String lastMessage = lastMessageObj != null ? lastMessageObj.getContent() : "";
            Timestamp createdAt = (lastMessageObj != null) ? lastMessageObj.getCreated_at() : null;

            Goods goods = chatRoom.getGoods();
            Long goodsId = (goods != null) ? goods.getId() : null;
            String otherImg = userRepository.findById(otherUserId).get().getImage_url();

            return new GetChatRoomByUserRes(chatRoomId, otherNickName, lastMessage, createdAt, goodsId, otherImg);
        }).collect(Collectors.toList());

    }

    @Transactional
    public void updateGoodsIdToNullForChatRooms(Long goodsId) {
        List<ChatRooms> chatRooms = chatRoomsRepository.findByGoodsId(goodsId);
        for (ChatRooms chatRoom : chatRooms) {
            chatRoom.setGoods(null);
        }
    }

    @Transactional
    public void updateExchangesIdToNullForChatRooms(Long eId) {
        List<ChatRooms> chatRooms = chatRoomsRepository.findByExchangesId(eId);
        for (ChatRooms chatRoom : chatRooms) {
            chatRoom.setGoods(null);
        }
    }

    // 구매, 판매, 교환한, 받은 목록
    public List<GetGoodsRes> getSaleByUser(Long id) {
        List<ChatRooms> saleList = chatRoomsRepository.findSaleByUser(id);
        List<Goods> goodsList = new ArrayList<>();

        for (ChatRooms chatRoom : saleList) {
            Long goodsId = chatRoom.getGoods().getId(); // ChatRooms에 있는 goods_id 추출
            Goods goods = goodsRepository.findById(goodsId)
                    .orElse(null); // goods_id를 사용하여 상품을 가져옴
            if (goods != null) {
                goodsList.add(goods);
            }
        }

        List<GetGoodsRes> GetGoodsRes = goodsList.stream()
                .map(goods -> new GetGoodsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getImageUrl(), goods.getSale_price(), goods.getSale_percent(), goods.getName(), goods.getUsers_id().getAddress().getZipCode(), goods.getUsers_id().getAddress().getDetailAddress(), goods.getUsers_id().getSeller_or_buyer()))
                .collect(Collectors.toList());
        return GetGoodsRes;
    }

    public List<GetGoodsRes> getPurchaseByUser(Long id) {
        List<ChatRooms> purchaseList = chatRoomsRepository.findPurchaseByUser(id);
        List<Goods> goodsList = new ArrayList<>();

        for (ChatRooms chatRoom : purchaseList) {
            Long goodsId = chatRoom.getGoods().getId();
            Goods goods = goodsRepository.findById(goodsId)
                    .orElse(null);
            if (goods != null) {
                goodsList.add(goods);
            }
        }

        List<GetGoodsRes> GetGoodsRes = goodsList.stream()
                .map(goods -> new GetGoodsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getImageUrl(), goods.getSale_price(), goods.getSale_percent(), goods.getName(), goods.getUsers_id().getAddress().getZipCode(), goods.getUsers_id().getAddress().getDetailAddress(), goods.getUsers_id().getSeller_or_buyer()))
                .collect(Collectors.toList());
        return GetGoodsRes;
    }

    public List<PostExchangesRes> getExchangesMeByUser(Long id) {
        List<ChatRooms> saleList = chatRoomsRepository.findExchangesMeByUser(id);
        List<Exchanges> exchangesList = new ArrayList<>();

        for (ChatRooms chatRoom : saleList) {
//            Long exchangesid = chatRoom.getExchanges();
//            Exchanges exchanges = exchangesRepository.findById(exchangesid)
//                    .orElse(null);
            Exchanges exchanges = chatRoom.getExchanges();
            if (exchanges != null) {
                exchangesList.add(exchanges);
            }
        }

        List<PostExchangesRes> response = exchangesList.stream()
                .map(newExchanges -> new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser(),  newExchanges.getUser().getAddress().getZipCode(), newExchanges.getUser().getAddress().getDetailAddress()))
                .collect(Collectors.toList());
        return response;
    }

    public List<PostExchangesRes> getExchangesOtherByUser(Long id) {
        List<ChatRooms> saleList = chatRoomsRepository.findExchangesOtherByUser(id);
        List<Exchanges> exchangesList = new ArrayList<>();

        for (ChatRooms chatRoom : saleList) {
            Long exchangesid = chatRoom.getExchanges().getId();
            Exchanges exchanges = exchangesRepository.findById(exchangesid)
                    .orElse(null);
            if (exchanges != null) {
                exchangesList.add(exchanges);
            }
        }

        List<PostExchangesRes> response = exchangesList.stream()
                .map(newExchanges -> new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser(), newExchanges.getUser().getAddress().getZipCode(), newExchanges.getUser().getAddress().getDetailAddress()))
                .collect(Collectors.toList());
        return response;
    }

    // 채팅방 id로 채팅방 조회
    public GetChatRoomByChatRoomIdRes getChatRoomByChatRoomId(Long chatId){
        ChatRooms chatRooms = chatRoomsRepository.findChatRoomsById(chatId);
        if(chatRooms.getGoods() != null) {
            GetChatRoomByChatRoomIdRes response = new GetChatRoomByChatRoomIdRes(chatRooms.getSeller().getId(), chatRooms.getBuyer().getId(),0, chatRooms.getGoods().getId(), chatRooms.getSeller_or_not(), chatRooms.getBuyer_or_not(), chatRooms.getCreated_at());
            return response;
        } else{
            GetChatRoomByChatRoomIdRes response = new GetChatRoomByChatRoomIdRes(chatRooms.getSeller().getId(), chatRooms.getBuyer().getId(),1, chatRooms.getExchanges().getId(), chatRooms.getSeller_or_not(), chatRooms.getBuyer_or_not(), chatRooms.getCreated_at());
            return response;
        }
    }


}



