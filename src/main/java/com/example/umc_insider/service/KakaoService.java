package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.response.PostKakaoRes;
import com.example.umc_insider.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KakaoService {
    private final UserRepository userRepository;

    public KakaoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void signUpKakaoUser(String nickname, String userId, String email) {

        // Users 엔티티 생성
        Users user = new Users(nickname, userId, email);

        // DB에 저장
        userRepository.save(user);
    }



}
