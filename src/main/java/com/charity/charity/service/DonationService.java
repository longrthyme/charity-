package com.charity.charity.service;

import com.charity.charity.dto.DonationDTO;
import com.charity.charity.dto.UserDonationRequest;
import com.charity.charity.entity.Donation;
import com.charity.charity.entity.User;
import com.charity.charity.entity.UserDonation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class DonationService {
//    public abstract List<Donation> getDonations(String search);

    public abstract Page<Donation> getDonations(String search, Pageable pageable);

    public abstract Donation getDonationById(String id);

    public abstract List<UserDonation> getDonorsByDonationId(Integer donationId);

    public abstract UserDonation createDonation(UserDonationRequest request, User loggedUser);

    public abstract Donation createDonation(DonationDTO donationDTO);
}
