package com.example.umc_insider.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class PostModifyPriceReq {
    long id;
    String price;
}
