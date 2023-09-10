package com.example.umc_insider.repository;

import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomsRepository extends JpaRepository<ChatRooms, Long> {

    @Query("select m from ChatRooms m where m.id = :id")
    ChatRooms findChatRoomsById(@Param("id") long id);

    @Query("SELECT c FROM ChatRooms c WHERE c.seller.id = :id OR c.buyer.id = :id")
    List<ChatRooms> findBySellerIdOrBuyerId(Long id);

    @Query("SELECT c FROM ChatRooms c WHERE c.seller.id = :id and c.buyer_or_not=True and c.seller_or_not=True and c.exchanges=null")
    List<ChatRooms> findSaleByUser(Long id); // 판매

    @Query("SELECT c FROM ChatRooms c WHERE c.buyer.id = :id and c.buyer_or_not=True and c.seller_or_not=True and c.exchanges=null")
    List<ChatRooms> findPurchaseByUser(Long id); // 구매

    @Query("SELECT c FROM ChatRooms c WHERE c.seller.id = :id and c.buyer_or_not=True and c.seller_or_not=True and c.goods=null")
    List<ChatRooms> findExchangesMeByUser(Long id); // 내가 교환

    @Query("SELECT c FROM ChatRooms c WHERE c.buyer.id = :id and c.buyer_or_not=True and c.seller_or_not=True and c.goods=null")
    List<ChatRooms> findExchangesOtherByUser(Long id); // 내가 교환 받음






    ChatRooms findBySellerIdAndBuyerIdAndGoodsId(Long sellerId, Long buyerId, Long goodsId);
    ChatRooms findBySellerIdAndBuyerIdAndExchangesId(Long sellerId, Long buyerId, Long exchangesId);

    // 해당 상품을 참조하는 모든 채팅방 레코드를 가져오는 메서드
    List<ChatRooms> findByGoodsId(Long goodsId);
    List<ChatRooms> findByExchangesId(Long eId);

    // 레코드의 상품 ID를 NULL로 업데이트하는 메서드
    @Modifying
    @Query("UPDATE ChatRooms c SET c.goods.id = NULL WHERE c.goods.id = :goodsId")
    void updateGoodsIdToNull(@Param("goodsId") Long goodsId);


}
