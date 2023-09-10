package com.example.umc_insider.dto.request;

import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PutUserReq {
    private Long id;
    private String userId;
    private String nickname;
    private String pw;
    private String email;
    private Integer zipCode;
    private String detailAddress;

    public Users modifyUserWithAddress() {
        Users newUser = new Users();
        newUser.setUser_id(this.userId);
        newUser.setCreated_at();
        newUser.setUpdated_at();
        newUser.setEmail(this.email);
        newUser.setPw(this.pw);
        newUser.setNickname(this.nickname);

        Address newAddress = new Address();
        newAddress.setZipCode(this.getZipCode());
        newAddress.setDetailAddress(this.getDetailAddress());
        newUser.setAddress(newAddress);

        return newUser;
    }
}
