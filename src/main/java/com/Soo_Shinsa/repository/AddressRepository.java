package com.Soo_Shinsa.repository;

import com.Soo_Shinsa.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository  extends JpaRepository<Address, Long> {
}
