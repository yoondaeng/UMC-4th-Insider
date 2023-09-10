package com.example.umc_insider.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reviews")

public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods_id; // FK

    @OneToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRooms chat_room_id; // FK

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer point;

    @Column(nullable = false)
    private Timestamp created_at;

//    public Reviews createReviews(Goods goods_id, String content, Integer point){
//        this.goods_id = goods_id;
//        this.content = content;
//        this.point = point;
//        this.created_at = new Timestamp(System.currentTimeMillis());
//
//        return this;
//    }

    public Reviews createReviews(Goods goodsId, ChatRooms chatRoomId, String content, Integer point){
        this.goods_id = goodsId;
        this.chat_room_id = chatRoomId; // Just set the ChatRooms object.
        this.content = content;
        this.point = point;
        this.created_at = new Timestamp(System.currentTimeMillis());

        return this;
    }





    public void setChatRoomsId(Long chatRoomsId) {
        this.chat_room_id=chat_room_id;
    }
}
