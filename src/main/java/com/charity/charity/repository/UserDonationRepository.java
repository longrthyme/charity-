package com.charity.charity.repository;

import com.charity.charity.entity.UserDonation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDonationRepository extends JpaRepository<UserDonation, Integer> {
    List<UserDonation> findByDonationId(Integer donationId);
}