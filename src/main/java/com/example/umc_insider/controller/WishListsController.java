package com.example.umc_insider.controller;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponse;
import com.example.umc_insider.dto.request.PostWishListsReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.GetWishListsRes;
import com.example.umc_insider.dto.response.PostExchangesRes;
import com.example.umc_insider.dto.response.PostWishListsRes;
import com.example.umc_insider.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
public class WishListsController {
    public WishListService wishListService;

    @Autowired
    public WishListsController(WishListService wishListService){
        this.wishListService = wishListService;
    }


    @PostMapping("/create")
    public BaseResponse<PostWishListsRes> addGoodsToWishList(@RequestBody PostWishListsReq postWishListsReq) throws BaseException {
        PostWishListsRes response = wishListService.addGoodsToWishList(postWishListsReq);
        return new BaseResponse<>(response);
    }

    // 유저별 위시리스트 조회
    @GetMapping("/goods/{userId}")
    public ResponseEntity<List<GetWishListsRes>>getGoodsInWishList(@PathVariable Long userId) {
        List<GetWishListsRes> goodsList = wishListService.getGoodsInWishList(userId);
        return new ResponseEntity<>(goodsList, HttpStatus.OK);
    }

    @GetMapping("/exchanges/{userId}")
    public ResponseEntity<List<PostExchangesRes>>getExchangesInWishList(@PathVariable Long userId) {
        List<PostExchangesRes> exchangesList = wishListService.getExchangesInWishList(userId);
        return new ResponseEntity<>(exchangesList, HttpStatus.OK);
    }

    // 위시리스트 삭제
    @DeleteMapping("/delete/{userId}/{goodsOrExchangesId}/{status}")
    public BaseResponse<PostWishListsRes>  deleteWishList(@PathVariable Long userId, @PathVariable Long goodsOrExchangesId, @PathVariable Integer status) throws BaseException{
        PostWishListsRes response = wishListService.deleteWishList(userId, goodsOrExchangesId, status);
        return new BaseResponse<>(response);
    }

    // userId, goodsOrExchangesId로 위시리스트에 있는지 true false 반환
    // status 0: goods, 1: exchanges
    @GetMapping("/check/{userId}/{goodsOrExchangesId}/{status}")
    public boolean checkWishList(@PathVariable Long userId, @PathVariable Long goodsOrExchangesId, @PathVariable Integer status){
        boolean checked = wishListService.checkWishList(userId, goodsOrExchangesId, status);
        if(checked){
            return true;
        } else{
            return false;
        }
    }

    // 인기있는 goods
    @GetMapping("/hot")
    public  BaseResponse<List<GetGoodsRes>> getHotGoods(){
        return new BaseResponse<>(wishListService.hotGoods());
    }
}
