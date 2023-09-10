package com.example.umc_insider.repository;

import java.util.List;

import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.dto.response.GetGoodsRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, Long> {

    @Query("select g from Goods g")
    List<Goods> findGoods();

    @Query("select count(g) from Goods g")
    Integer findByGoodsCount();

    @Query("select g from Goods g where g.title = :title")
    List<Goods> findGoodsByTitle(@Param("title") String title);

    List<Goods> findByTitleContaining(String title);

    @Query("select g from Goods g where g.id=:id")
    Goods findGoodsById(@Param("id") Long id);

    List<Goods> findByCategory_Id(Long category_id);

    @Query("SELECT g FROM Goods g LEFT JOIN FETCH g.users_id WHERE g.title LIKE %:title%")
    List<Goods> findByTitleContainingWithUsers(@Param("title") String title);

    @Query("SELECT g FROM Goods g LEFT JOIN FETCH g.users_id")
    List<Goods> findAllWithUsers();

    @Query("select g from Goods g where g.sale_price != null")
    public abstract List<Goods> findAllBySale_priceIsNotNull();



}
