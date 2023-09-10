package com.example.umc_insider.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(nullable = false)
    private String user_id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true) // kakao user의 경우 pw 필요 X
    private String pw;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "id")
    @JsonIgnore // 무한 재귀를 방지하기 위한 설정
    private Address address;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp created_at;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updated_at;

    @Column(nullable = true)
    private String image_url;

    @Column(nullable = true)
    private Integer seller_or_buyer = 0; // 1: 판매자, 0: 구매자
    // 디폴트 0으로 설정

    @Column(nullable = true)
    private Long register_number;

    public Users(String nickname, String userId, String email) {
        this.nickname = nickname;
        this.user_id = userId;
        this.email = email;
        this.created_at = new Timestamp(System.currentTimeMillis());
        this.updated_at = new Timestamp(System.currentTimeMillis());
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setUser_id(String id) {this.user_id = id;}
    public void setEmail(String email) {this.email = email;}
    public void setNickname(String nickname) {this.nickname = nickname;}
    public void setPw(String pw) {this.pw = pw;}
    public void setCreated_at(){this.created_at =  new Timestamp(System.currentTimeMillis());}
    public void setUpdated_at(){this.updated_at =  new Timestamp(System.currentTimeMillis());}
    public void setAddress(Address address) {this.address = address; }
    public void setImageUrl(String url){
        this.image_url = url;
    }

    public Integer getSellerOrBuyer() {
        return seller_or_buyer;
    }

    public void setSellerOrBuyer(Integer sellerOrBuyer) {
        this.seller_or_buyer = sellerOrBuyer;
    }

}
