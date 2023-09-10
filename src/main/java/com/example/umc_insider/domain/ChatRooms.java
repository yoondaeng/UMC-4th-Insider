package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @Column(nullable = false)
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Users seller; // FK

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Users buyer; //  FK

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods;

    @ManyToOne
    @JoinColumn(name = "exchanges_id")
    private Exchanges exchanges;


    @Column(nullable=true)
    private Boolean seller_or_not;

    @Column(nullable = true)
    private Boolean buyer_or_not;

    public ChatRooms createChatRooms(Long sellerIdx, Long buyerIdx, Integer status, Long idx){
        this.seller = new Users();
        this.seller.setId(sellerIdx);
        this.buyer = new Users();
        this.buyer.setId(buyerIdx);

        if(status == 0){
            this.goods = new Goods();
            this.goods.setId(idx);
        }
        else{
            this.exchanges = new Exchanges();
            this.exchanges.setId(idx);
        }
        this.seller_or_not = false;
        this.buyer_or_not = false;

        this.created_at = new Timestamp(System.currentTimeMillis());
        return this;
    }


    public void setSeller(Users user) {
        this.seller = user;
    }
    public void setBuyer(Users user){this.buyer = user;}
    public void setGoods(Goods goods){this.goods = goods;}
    public void setExchanges(Exchanges exchanges){this.exchanges = exchanges;}
    public void setSeller_or_not(Boolean b){this.seller_or_not = b;}
    public void setBuyer_or_not(Boolean b){this.buyer_or_not = b;}

    public Goods getGoods() {
        return this.goods;
    }

}
