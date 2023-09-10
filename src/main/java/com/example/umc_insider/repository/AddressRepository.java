package com.example.umc_insider.repository;

import com.example.umc_insider.domain.Address;
import com.example.umc_insider.domain.ChatRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
