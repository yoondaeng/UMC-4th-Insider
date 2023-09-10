package com.example.umc_insider.repository;

import com.example.umc_insider.domain.Goods;
import com.example.umc_insider.domain.Reviews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long>{
    List<Reviews> findByOrderByPointDesc();
}

