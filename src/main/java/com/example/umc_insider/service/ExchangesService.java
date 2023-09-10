package com.example.umc_insider.service;

import com.example.umc_insider.config.BaseException;
import com.example.umc_insider.config.BaseResponseStatus;
import com.example.umc_insider.domain.Category;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.dto.request.PostExchangesReq;
import com.example.umc_insider.dto.request.PostGoodsReq;
import com.example.umc_insider.dto.response.GetGoodsRes;
import com.example.umc_insider.dto.response.PostExchangesRes;
import com.example.umc_insider.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.umc_insider.domain.Exchanges;
import com.example.umc_insider.domain.Goods;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangesService {
    private ExchangesRepository exchangesRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private S3Service s3Service;
    private ChatRoomsRepository chatRoomsRepository;
    private WishListsRepository wishListsRepository;
    private WishListHasGoodsRepository wishListHasGoodsRepository;
    private ChatRoomsService chatRoomsService;

    @Autowired
    public ExchangesService(ExchangesRepository exchangesRepository, ChatRoomsService chatRoomsService, UserRepository userRepository, CategoryRepository categoryRepository, S3Service s3Service, ChatRoomsRepository chatRoomsRepository,  WishListHasGoodsRepository wishListHasGoodsRepository) {
        this.exchangesRepository = exchangesRepository;
        this.chatRoomsService = chatRoomsService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.s3Service = s3Service;
        this.chatRoomsRepository = chatRoomsRepository;
        this.wishListHasGoodsRepository = wishListHasGoodsRepository;
    }

    public Exchanges createNewExchangesInstance(PostExchangesReq postExchangesReq, MultipartFile file) {
        Users user = userRepository.findUsersById(postExchangesReq.getUserId());
        Category category = categoryRepository.findCategoryByCategoryId(postExchangesReq.getCategoryId());
        Exchanges exchanges = new Exchanges(postExchangesReq, user, category);

        exchangesRepository.save(exchanges);

        // S3에 이미지 업로드 및 URL 받기
        String imageUrl = s3Service.uploadExchangesS3(file, exchanges);

        // 이미지 URL 설정 후, 객체 업데이트
        exchanges.setImageUrl(imageUrl);

        exchangesRepository.save(exchanges);

        return exchanges;
    }

    // 모든 교환 조회
    public List<PostExchangesRes> getExchanges() throws BaseException{
        List<Exchanges> eList = exchangesRepository.findAllWithUsers();
        List<PostExchangesRes> response = eList.stream()
                .map(newExchanges -> new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser(),  newExchanges.getUser().getAddress().getZipCode(),newExchanges.getUser().getAddress().getDetailAddress()))
                .collect(Collectors.toList());
        return response;
    }

    // 특정 교환 조회
    public List<PostExchangesRes> getExchangesByTitle(String title) throws BaseException{
        List<Exchanges> eList = exchangesRepository.findByTitleContaining(title);
        List<PostExchangesRes> response = eList.stream()
                .map(newExchanges -> new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser(), newExchanges.getUser().getAddress().getZipCode(),newExchanges.getUser().getAddress().getDetailAddress()))
                .collect(Collectors.toList());
        return response;
    }

    // id로 교환하기 조회
    public PostExchangesRes getExchangesById(Long id) {
        Exchanges newExchanges = exchangesRepository.findExchangesById(id);
        return new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser(), newExchanges.getUser().getAddress().getZipCode(),newExchanges.getUser().getAddress().getDetailAddress());
    }

    // category_id로 goods 조회
    public List<PostExchangesRes> getExchangesByCategoryId(Long category_id) {
        List<Exchanges> eList = exchangesRepository.findByCategory_Id(category_id);
        List<PostExchangesRes> postExchangesRes = eList.stream()
                .map(newExchanges -> new PostExchangesRes(newExchanges.getId(), newExchanges.getTitle(), newExchanges.getImageUrl(), newExchanges.getName(), newExchanges.getCount(), newExchanges.getWantItem(), newExchanges.getWeight(), newExchanges.getShelfLife(),newExchanges.getCreated_at() ,newExchanges.getCategory().getId(), newExchanges.getUser(),  newExchanges.getUser().getAddress().getZipCode(),newExchanges.getUser().getAddress().getDetailAddress()))
                .collect(Collectors.toList());
        return postExchangesRes;
    }

    // 모든 항목 수정
    public Exchanges update(Long id, PostExchangesReq exchanges) throws BaseException {
        Exchanges e = exchangesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다. id=" + id));

        e.setTitle(exchanges.getTitle());
        e.setName(exchanges.getName());
        e.setCount(exchanges.getCount());
        e.setWeight(exchanges.getWeight());
        e.setShelfLife(exchanges.getShelfLife());
        e.setWantItem(exchanges.getWantItem());
        Category category = categoryRepository.findCategoryByCategoryId(exchanges.getCategoryId());
        e.setCategory(category);
        return exchangesRepository.save(e);

    }


    // 교환하기 삭제
    @Transactional
    public void deleteExchanges(long id) {
        // 1. exchanges_id에 해당하는 wishlist_has_goods 행 삭제
        wishListHasGoodsRepository.deleteByExchangesId(id);

        // 2. exchanges 테이블에서 행 삭제
        exchangesRepository.deleteById(id);
    }






}
