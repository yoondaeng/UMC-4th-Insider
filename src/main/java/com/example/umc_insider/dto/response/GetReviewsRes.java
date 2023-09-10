package com.example.umc_insider.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.example.umc_insider.domain.Goods;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewsRes {
    private Long id;
    private Goods goodsId;
    private String content;
    private Integer point;
    private Timestamp created_at;
}

