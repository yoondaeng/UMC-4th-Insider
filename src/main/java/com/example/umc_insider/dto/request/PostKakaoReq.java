package com.example.umc_insider.dto.request;

import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
public class PostKakaoReq {
    private String userId;
    private String nickname;
    private String email;
    private Integer zipCode;
    private String detailAddress;
    private Integer sellerOrBuyer;
    private Long registerNum;

    public Users createUserWithAddress() {
        Users newUser = new Users();
        newUser.setUser_id(this.userId);
        newUser.setNickname(this.nickname);
        newUser.setCreated_at();
        newUser.setUpdated_at();
        newUser.setEmail(this.email);
        Address newAddress = new Address();
        newAddress.setZipCode(this.getZipCode());
        newAddress.setDetailAddress(this.getDetailAddress());
        newUser.setAddress(newAddress);
        newUser.setSeller_or_buyer(this.sellerOrBuyer);
        if(newUser.getSeller_or_buyer() == 1){
            newUser.setRegister_number(this.registerNum);
        } else{
            newUser.setRegister_number(0L);
        }

        return newUser;
    }
}
