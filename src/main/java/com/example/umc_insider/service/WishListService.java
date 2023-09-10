package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.*;
import com.example.umc_insider.dto.request.PostWishListsReq;
import com.example.umc_insider.dto.response.*;
import com.example.umc_insider.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.umc_insider.config.BaseResponseStatus.FAILED_TO_LOGIN;

@Service
@RequiredArgsConstructor
public class WishListService {
    private WishListsRepository wishListsRepository;
    private WishListHasGoodsRepository wishListHasGoodsRepository;
    private UserRepository userRepository;
    private GoodsRepository goodsRepository;
    private ExchangesRepository exchangesRepository;
    @Autowired
    public WishListService(WishListsRepository wishListsRepository, WishListHasGoodsRepository wishListHasGoodsRepository, UserRepository userRepository, GoodsRepository goodsRepository, ExchangesRepository exchangesRepository){
        this.wishListsRepository = wishListsRepository;
        this.wishListHasGoodsRepository = wishListHasGoodsRepository;
        this.userRepository = userRepository;
        this.goodsRepository = goodsRepository;
        this.exchangesRepository = exchangesRepository;
    }

    @Transactional
    public PostWishListsRes addGoodsToWishList(PostWishListsReq postWishListsReq) throws BaseException {
        if(postWishListsReq.getUserId() != null && postWishListsReq.status==0 && postWishListsReq.getGoodsOrExchangesId() != null){
            Users user = userRepository.findUsersById(postWishListsReq.getUserId());
            Goods goods = goodsRepository.findGoodsById(postWishListsReq.getGoodsOrExchangesId());

            // 중복 체크
            WishListHasGoods existingEntry = wishListHasGoodsRepository.findByUserIdToWishListObject(user.getId(), goods.getId());
            if (existingEntry != null) {
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }

            WishLists wishList = new WishLists();
            wishList.setUser(user);
            wishList.setCreatedAt();

            WishLists savedWishList = wishListsRepository.save(wishList);

            WishListHasGoods wishListHasGoods = new WishListHasGoods();
            wishListHasGoods.setWishList(savedWishList);
            wishListHasGoods.setGoods(goods);
            wishListHasGoodsRepository.save(wishListHasGoods);
            return new PostWishListsRes(wishList.getId(), wishList.getUser().getId(), wishListHasGoods.getGoods().getId(), wishList.getCreatedAt(), 0);
        }
        else if(postWishListsReq.getUserId() != null && postWishListsReq.status==1 && postWishListsReq.getGoodsOrExchangesId() != null){
            Users user = userRepository.findUsersById(postWishListsReq.getUserId());
            Exchanges exchanges = exchangesRepository.findExchangesById(postWishListsReq.getGoodsOrExchangesId());

            // 중복 체크
            WishListHasGoods existingEntry = wishListHasGoodsRepository.findByUserIdToWishListObjectExchanges(user.getId(), exchanges.getId());
            if (existingEntry != null) {
                throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
            }

            WishLists wishList = new WishLists();
            wishList.setUser(user);
            wishList.setCreatedAt();

            WishLists savedWishList = wishListsRepository.save(wishList);

            WishListHasGoods wishListHasGoods = new WishListHasGoods();
            wishListHasGoods.setWishList(savedWishList);
            wishListHasGoods.setExchanges(exchanges);
            wishListHasGoodsRepository.save(wishListHasGoods);
            return new PostWishListsRes(wishList.getId(), wishList.getUser().getId(), wishListHasGoods.getExchanges().getId(), wishList.getCreatedAt(), 1);
        }
        else {
            // 예외 처리: 요청이 잘못된 경우
            throw new IllegalArgumentException("요청이 잘못되었습니다.");
        }
    }

    // 유저의 위시리스트 조회
    @Transactional
    public List<GetWishListsRes> getGoodsInWishList(Long userId) {
        List<GetWishListsRes> goodsList = new ArrayList<>();
        List<WishLists> wishLists = wishListHasGoodsRepository.findByUserIdGoods(userId);

        List<Long> goodsIds = wishLists.stream()
                .map(wishList -> wishListHasGoodsRepository.findGoodsIdsByWishListId(wishList.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        Integer i = 0;
        for (Long goodsId : goodsIds) {
            Goods goods = goodsRepository.findGoodsById(goodsId);

            if (goods != null) {
                // GetGoodsRes 객체를 생성합니다.
                GetWishListsRes goodsDTO = new GetWishListsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(),
                        goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(),
                        goods.getImageUrl(), goods.getSale_price(), goods.getSale_percent(), goods.getName(), wishLists.get(i).getCreatedAt());

                goodsDTO.setId(goods.getId());
                goodsDTO.setTitle(goods.getTitle());
                goodsDTO.setName(goods.getName());
                goodsDTO.setPrice(goods.getPrice());
                goodsDTO.setRest(goods.getRest());
                goodsDTO.setSale_price(goods.getSale_price());
                goodsDTO.setSale_percent(goods.getSale_percent());
                goodsDTO.setCategory_id(goods.getCategory());
                goodsDTO.setImg_url(goods.getImageUrl());
                goodsDTO.setMarkets_id(goods.getMarkets_id());
                goodsDTO.setShelf_life(goods.getShelf_life());
                goodsDTO.setUsers_id(goods.getUsers_id());
                goodsDTO.setWeight(goods.getWeight());
                goodsDTO.setCreatedAt(wishLists.get(i).getCreatedAt());

                goodsList.add(goodsDTO);
                i = i + 1;
            }
        }

        return goodsList;
    }

    public List<PostExchangesRes> getExchangesInWishList(Long userId){
        List<PostExchangesRes> eList = new ArrayList<>();
        List<WishLists> wishLists = wishListHasGoodsRepository.findByUserIdExchanges(userId);

        List<Long> eIds = wishLists.stream()
                .map(wishList -> wishListHasGoodsRepository.findExchangesIdsByWishListId(wishList.getId()))
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Integer i = 0;
        for (Long eId : eIds) {
            Exchanges exchanges = exchangesRepository.findExchangesById(eId);

            if (exchanges != null) {
                // GetGoodsRes 객체를 생성합니다.
                PostExchangesRes exchangeDTO = new PostExchangesRes(exchanges.getId(), exchanges.getTitle(), exchanges.getImageUrl(), exchanges.getName(), exchanges.getCount(), exchanges.getWantItem(), exchanges.getWeight(), exchanges.getShelfLife(), exchanges.getCreated_at(), exchanges.getCategory().getId(), exchanges.getUser(), exchanges.getUser().getAddress().getZipCode(), exchanges.getUser().getAddress().getDetailAddress());
                exchangeDTO.setId(exchangeDTO.getId());
                exchangeDTO.setTitle(exchangeDTO.getTitle());
                exchangeDTO.setImageUrl(exchanges.getImageUrl());
                exchangeDTO.setName(exchangeDTO.getName());
                exchangeDTO.setCount(exchangeDTO.getCount());
                exchangeDTO.setWantItem(exchangeDTO.getWantItem());
                exchangeDTO.setWeight(exchangeDTO.getWeight());
                exchangeDTO.setShelfLife(exchanges.getShelfLife());
                exchangeDTO.setCreatedAt(wishLists.get(i).getCreatedAt());
                exchangeDTO.setCategoryId(exchangeDTO.getCategoryId());
                exchangeDTO.setUser(exchangeDTO.getUser());
                eList.add(exchangeDTO);
                i = i + 1;
            }
        }

        return eList;

    }


    // 위시리스트 삭제
    public PostWishListsRes deleteWishList(Long userId, Long goodsId, Integer status) throws BaseException  {
        Users user = userRepository.findUsersById(userId);
        if(status == 0) { // goods
            Goods goods = goodsRepository.findGoodsById(goodsId);
            if (user != null && goods != null) {
                List<WishListHasGoods> wlhg = wishListHasGoodsRepository.findByUserIdToWishList(userId, goodsId);
                if (wlhg.isEmpty()) {
                    throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
                }
                WishLists wishList = wlhg.get(0).getWishList();

                if (wishList == null) {
                    throw new RuntimeException("WishList is null");
                }

                Users users = wishList.getUser();
                if (users == null) {
                    throw new RuntimeException("User is null");
                }

                WishListHasGoods temp = wishListHasGoodsRepository.findByWishListId(wishList.getId());
                wishListHasGoodsRepository.delete(temp);
//                wishListsRepository.delete(wishList);
                return new PostWishListsRes(wishList.getId(), wishList.getUser().getId(), temp.getGoods().getId(), wishList.getCreatedAt(), 0);
            }
        }
        else if(status == 1){
            Exchanges exchanges = exchangesRepository.findExchangesById(goodsId);
            if (user != null && exchanges != null) {
                List<WishListHasGoods> wlhg = wishListHasGoodsRepository.findByUserIdToWishListExchanges(userId, goodsId);
                if (wlhg.isEmpty()) {
                    throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
                }
                WishLists wishList = wlhg.get(0).getWishList();

                if (wishList == null) {
                    throw new RuntimeException("WishList is null");
                }

                Users users = wishList.getUser();
                if (users == null) {
                    throw new RuntimeException("User is null");
                }

                WishListHasGoods temp = wishListHasGoodsRepository.findByWishListId(wishList.getId());
                wishListHasGoodsRepository.delete(temp);
//                wishListsRepository.delete(wishList);
                return new PostWishListsRes(wishList.getId(), wishList.getUser().getId(), temp.getExchanges().getId(), wishList.getCreatedAt(), 1);
            }
        }

        throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
    }

    // 위시리스트 체크
    public boolean checkWishList(Long userId, Long goodsOrExchangesId, Integer status){
        Users user = userRepository.findUsersById(userId);
        if(status == 0) {
            Goods goods = goodsRepository.findGoodsById(goodsOrExchangesId);

            if (user != null && goods != null) {
                List<WishListHasGoods> wlhg = wishListHasGoodsRepository.findByUserIdToWishList(userId, goodsOrExchangesId);
                if (wlhg.isEmpty()) {
                    return false;
                }
                WishLists wishList = wlhg.get(0).getWishList();

                if (wishList == null) {
                    throw new RuntimeException("WishList is null");
                }

                Users users = wishList.getUser();
                if (users == null) {
                    throw new RuntimeException("User is null");
                }

                WishListHasGoods temp = wishListHasGoodsRepository.findByWishListId(wishList.getId());
                if (temp != null && wishList != null) {
                    return true;
                }
            }
        }
        else if(status == 1) {
            Exchanges exchanges = exchangesRepository.findExchangesById(goodsOrExchangesId);

            if (user != null && exchanges != null) {
                List<WishListHasGoods> wlhg = wishListHasGoodsRepository.findByUserIdToWishListExchanges(userId, goodsOrExchangesId);
                if (wlhg.isEmpty()) {
                    return false;
                }
                WishLists wishList = wlhg.get(0).getWishList();

                if (wishList == null) {
                    throw new RuntimeException("WishList is null");
                }

                Users users = wishList.getUser();
                if (users == null) {
                    throw new RuntimeException("User is null");
                }

                WishListHasGoods temp = wishListHasGoodsRepository.findByWishListId(wishList.getId());
                if (temp != null && wishList != null) {
                    return true;
                }
            }
        }
        return false;
    }

    // hot Goods
    public List<GetGoodsRes> hotGoods(){
        List<Long> hotGoodsIds = wishListsRepository.findHotGoods();
        List<Goods> popularGoods = new ArrayList<>();
        for (Long goodsId : hotGoodsIds) {
            Goods goods = goodsRepository.findGoodsById(goodsId);
            if (goods != null) {
                popularGoods.add(goods);
            }
        }
        List<GetGoodsRes> GetGoodsRes = popularGoods.stream()
            .map(goods -> new GetGoodsRes(goods.getId(), goods.getCategory(), goods.getUsers_id(), goods.getMarkets_id(), goods.getTitle(), goods.getPrice(), goods.getWeight(), goods.getRest(), goods.getShelf_life(), goods.getImageUrl(), goods.getSale_price(), goods.getSale_percent(), goods.getName(), goods.getUsers_id().getAddress().getZipCode(), goods.getUsers_id().getAddress().getDetailAddress(), goods.getUsers_id().getSeller_or_buyer()))
            .collect(Collectors.toList());
        return GetGoodsRes;

    }

}
