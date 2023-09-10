package com.example.umc_insider.dto.request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.example.umc_insider.domain.Goods;

@Getter
@AllArgsConstructor
public class PostExchangesReq {
    private String title;
    private String name;
    private Integer count;
    private String wantItem;
    private String weight;
    private String shelfLife;
    private Long categoryId;
    private Long userId;
}
