package com.example.umc_insider.dto.request;
import com.example.umc_insider.domain.ChatRooms;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReviewsReq {
    private Goods goods_id;
    private Users buyer_id;
    private String content;
    private Integer point;
    private ChatRooms chatRoomsId;

    public ChatRooms getChatRoomsId() {
        return chatRoomsId;
    }

    public void setChatRoomsId(ChatRooms chatRoomsId) {
        this.chatRoomsId = chatRoomsId;
    }

    public Goods getGoodsId() {
        return goods_id;
    }

    public void setGoodsId(Goods goods_id) {
        this.goods_id = goods_id;
    }
}
