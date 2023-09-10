package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wish_lists_has_goods")
//@IdClass(WishListHasGoodsId.class)
public class WishListHasGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wish_lists_id")
    private WishLists wishList;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Goods goods = null;

    @ManyToOne
    @JoinColumn(name = "exchanges_id")
    private Exchanges exchanges = null;

    public void setWishList(WishLists w){this.wishList = w;}
    public void setGoods(Goods g){this.goods = g;}
    public void setExchanges(Exchanges e){this.exchanges = e;}
}
