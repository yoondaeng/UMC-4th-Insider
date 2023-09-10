package com.example.umc_insider.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id; // PK

    @Column(name = "zip_code")
    private Integer zipCode;

    @Column(name = "detail_address")
    private String detailAddress;

    @OneToOne(mappedBy = "address", fetch = FetchType.LAZY, cascade=CascadeType.ALL)
    private Users user;

    public void setZipCode(Integer zipCode){this.zipCode = zipCode;}
    public void setDetailAddress(String detailAddress){this.detailAddress = detailAddress;}

    public void setUser(Users user){this.user = user;}

}
