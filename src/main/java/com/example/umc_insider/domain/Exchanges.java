package com.example.umc_insider.domain;

import com.example.umc_insider.dto.request.PostExchangesReq;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "exchanges")
public class Exchanges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category; // FK

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user; // FK

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private String wantItem;

    @Column(nullable = false)
    private String weight;

    @Column(nullable = false)
    private String shelfLife;

    @Column(nullable = false)
    private Timestamp created_at;

    // 게시물 삭제되면 wishlist도 삭제
    @OneToMany(mappedBy = "exchanges", cascade = CascadeType.ALL)
    private List<WishListHasGoods> wishListHasGoodsList2 = new ArrayList<>();

    public Exchanges(PostExchangesReq postExchangesReq, Users user, Category category){
        super();
        this.user = user;
        this.category = category;
        this.count = postExchangesReq.getCount();
        this.title = postExchangesReq.getTitle();
        this.name = postExchangesReq.getName();
        this.shelfLife = postExchangesReq.getShelfLife();
        this.wantItem = postExchangesReq.getWantItem();
        this.weight = postExchangesReq.getWeight();
        this.created_at =  new Timestamp(System.currentTimeMillis());
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }
    public void setTitle(String t){this.title = t;}
    public void setName(String n){this.name = n;}
    public void setWeight(String w){this.weight = w;}
    public void setCount(Integer c){this.count = c;}
    public void setWantItem(String w){this.wantItem = w;}
    public void setShelfLife(String s){this.shelfLife = s;}
    public void setId(Long id){this.id = id;}
    public void setCategory(Category c){this.category = c;}

}
