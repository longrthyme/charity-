package com.charity.charity.service.impl;

import com.charity.charity.dto.DonationDTO;
import com.charity.charity.entity.Donation;
import com.charity.charity.repository.DonationRepository;
import com.charity.charity.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        donation.setStartDate(donationDTO.getStartDate().format(formatter)); // Chuyển LocalDate thành String


        donation.setName(donationDTO.getName());
//        donation.setStartDate(donationDTO.getStartDate().to);
        donation.setEndDate(donationDTO.getEndDate().format(formatter));
        donation.setOrganizationName(donationDTO.getCharityOrganization());
        donation.setPhoneNumber(donationDTO.getPhone());
        donation.setDescription(donationDTO.getContent());

        return donationRepository.save(donation);
    }



}
