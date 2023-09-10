package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "wish_lists")
public class WishLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false)
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    // 게시물 삭제되면 wishlist도 삭제(상품)
    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL)
    private List<WishListHasGoods> wishListHasGoodsList = new ArrayList<>();

    // 게시물 삭제되면 wishlist도 삭제(교환)
    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL)
    private List<WishListHasGoods> wishListHasGoodsList2 = new ArrayList<>();


    public void setCreatedAt(){
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
