package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Category;
import com.example.umc_insider.domain.Markets;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetWishListsRes {
    private Long id;
    private Category category_id;
    private Users users_id;
    private Markets markets_id;
    private String title;
    private String price;
    private String weight;
    private Integer rest;
    private String shelf_life;
    private Integer sale;
    private String img_url;
    private String name;
    private Timestamp createdAt;
    private Integer sale_price;
    private Integer sale_percent;

    public GetWishListsRes(Long id, Category category, Users usersId, Markets marketsId, String title, String price, String weight, Integer rest, String shelfLife, String imageUrl, Integer salePrice, Integer salePercent, String name, Timestamp createdAt) {
        this.id = id;
        this.category_id = category;
        this.users_id = usersId;
        this.markets_id = marketsId;
        this.title = title;
        this.price = price;
        this.weight = weight;
        this.rest = rest;
        this.shelf_life = shelfLife;
        this.img_url = imageUrl;
        this.sale_price = salePrice;
        this.sale_percent = salePercent;
        this.name = name;
        this.createdAt = createdAt;
    }

    public void setSale_price(Integer salePrice) {
        this.sale_price = salePrice;
    }

    public Integer getSale_price() {
        return sale_price;
    }
}
