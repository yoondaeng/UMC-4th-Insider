package com.example.umc_insider.repository;

import java.util.List;

import com.example.umc_insider.domain.Markets;
import com.example.umc_insider.domain.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketsRepository {
    @Query("select m from Markets m")
    List<Users> findMarkets();

    @Query("select m from Users m where m.id = :id")
    Markets findMartketsById(@Param("id") long id);
}
