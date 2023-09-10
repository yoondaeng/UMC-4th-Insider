package com.example.umc_insider.repository;

import com.example.umc_insider.domain.WishListHasGoods;
import com.example.umc_insider.domain.WishLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListHasGoodsRepository extends JpaRepository<WishListHasGoods, Long> {
    @Query("SELECT w.goods.id FROM WishListHasGoods w WHERE w.wishList.id = :wishListId")
    List<Long> findGoodsIdsByWishListId(@Param("wishListId") Long wishListId);

    @Query("SELECT w.exchanges.id FROM WishListHasGoods w WHERE w.wishList.id = :wishListId")
    List<Long> findExchangesIdsByWishListId(@Param("wishListId") Long wishListId);


    @Query("SELECT wh FROM WishListHasGoods wh JOIN wh.wishList w WHERE w.user.id = :userId AND wh.goods.id = :goodsId")
    List<WishListHasGoods> findByUserIdToWishList(@Param("userId") Long userId, @Param("goodsId") Long goodsId);



    @Query("select wh from WishLists w, WishListHasGoods wh where w.user.id= :userId and wh.exchanges.id = :goodsId")
    List<WishListHasGoods> findByUserIdToWishListExchanges(@Param("userId") Long userId, @Param("goodsId") Long goodsId);

    @Query("select wh from WishLists w, WishListHasGoods wh where wh.wishList.id = :wishListId")
    WishListHasGoods findByWishListId(@Param("wishListId") Long wishListId);

    @Query("SELECT wh FROM WishListHasGoods wh WHERE wh.wishList.user.id = :userId AND wh.goods.id = :goodsId")
    WishListHasGoods findByUserIdToWishListObject(@Param("userId") Long userId, @Param("goodsId") Long goodsId);


    @Query("select wh from WishLists w, WishListHasGoods wh where wh.wishList.user.id= :userId and wh.exchanges.id = :exchangesId")
    WishListHasGoods findByUserIdToWishListObjectExchanges(@Param("userId") Long userId, @Param("exchangesId") Long exchangesId);

    @Query("SELECT w FROM WishLists w, WishListHasGoods wh WHERE w.user.id = :userId AND wh.exchanges.id = null")
    List<WishLists> findByUserIdGoods(Long userId);

    @Query("SELECT w FROM WishLists w, WishListHasGoods wh WHERE w.user.id = :userId and wh.goods.id = null")
    List<WishLists> findByUserIdExchanges(Long userId);

    void deleteWishListHasGoodsByExchangesId(Long exchangesId);

    void deleteByExchangesId(long id);
}
