package com.example.umc_insider.repository;

import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.Users;
import com.example.umc_insider.domain.WishLists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListsRepository extends JpaRepository<WishLists, Long> {
    List<WishLists> findByUserId(Long userId);


    @Query("SELECT w.goods.id FROM WishListHasGoods w GROUP BY w.goods.id ORDER BY COUNT(w.goods.id) DESC")
    List<Long> findHotGoods();



}
