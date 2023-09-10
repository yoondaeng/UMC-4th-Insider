package com.example.umc_insider.repository;
import com.example.umc_insider.domain.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface MessagesRepository extends JpaRepository<Messages, Long> {



    @Query("SELECT m FROM Messages m WHERE m.chatRoom.id = :chatRoomId ORDER BY m.created_at ASC")
    List<Messages> findByChatRoomIdOrderByCreated_atAsc(@Param("chatRoomId") Long chatRoomId);

    @Query("SELECT m FROM Messages m WHERE m.chatRoom.id = :chatRoomId ORDER BY m.created_at DESC")
    List<Messages> findByChatRoomIdOrderByCreated_atDesc(Long chatRoomId);


}
