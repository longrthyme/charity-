package com.charity.charity.repository;

import com.charity.charity.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
//    List<Donation> findByNameContainingIgnoreCase(String name);

    Page<Donation> findByNameContainingIgnoreCase(String title, Pageable pageable);

}
