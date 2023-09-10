package com.example.umc_insider.repository;

import com.example.umc_insider.domain.Category;
import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.id = :categoryId")
    Category findCategoryByCategoryId(@Param("categoryId") Long categoryId);

}
