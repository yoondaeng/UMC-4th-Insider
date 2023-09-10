package com.example.umc_insider.dto.response;

import com.example.umc_insider.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetExchangeRes {
    private Goods mineGoods;
    private Goods yoursGoods;
    private String status;
}
