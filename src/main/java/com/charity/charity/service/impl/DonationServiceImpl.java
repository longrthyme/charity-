package com.charity.charity.service.impl;

import com.charity.charity.dto.DonationDTO;
import com.charity.charity.entity.Donation;
import com.charity.charity.repository.DonationRepository;
import com.charity.charity.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DonationServiceImpl extends DonationService {



    @Autowired
    private DonationRepository donationRepository;

    @Override
    public List<Donation> getDonations(String search) {
        if (search != null && !search.isEmpty()) {
            return donationRepository.findByNameContainingIgnoreCase(search);
        }
        return donationRepository.findAll();
    }

    @Override
    public Donation createDonation(DonationDTO donationDTO) {
        Donation donation = new Donation();
        donation.setName(donationDTO.getName());
        donation.setStartDate(donationDTO.getStartDate());
        donation.setEndDate(donationDTO.getEndDate());
        donation.setCharityOrganization(donationDTO.getCharityOrganization());
        donation.setPhone(donationDTO.getPhone());
        donation.setContent(donationDTO.getContent());

        return donationRepository.save(donation);
    }



}
