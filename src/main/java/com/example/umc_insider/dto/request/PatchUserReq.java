package com.example.umc_insider.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatchUserReq {
    public Long id;
    public Long registerNum;
}
