package com.example.umc_insider.repository;

import java.util.List;

import com.example.umc_insider.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

    List<Users> findAll();
    @Query("select m from Users m")
    List<Users> findUsers();
    List<Users> findAllById(long id);

    @Query("select m from Users m where m.id = :id")
    Users findUsersById(@Param("id") long id);

    @Query("select m from Users m where m.user_id = :userId")
    Users findUserByUserId(@Param("userId") String userId);

    @Query("select m from Users m where m.email = :email")
    Users findUserByEmail(@Param("email") String email);

    @Query("select m from Users m where m.nickname = :nickname")
    Users findUserByNickname(@Param("nickname") String nickname);

    @Query("select count(*) from Users m where m.email = :email")
    Integer findByEmailCount(@Param("email") String email);



}
