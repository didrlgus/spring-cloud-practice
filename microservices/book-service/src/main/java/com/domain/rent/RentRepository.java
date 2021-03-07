package com.domain.rent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentRepository extends JpaRepository<Rent, Long> {

    Page<Rent> findAllByIdentifier(String identifier, Pageable pageable);

}
