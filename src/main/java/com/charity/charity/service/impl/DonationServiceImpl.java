package com.charity.charity.service.impl;

import com.charity.charity.dto.DonationDTO;
import com.charity.charity.dto.UserDonationRequest;
import com.charity.charity.entity.Donation;
import com.charity.charity.entity.User;
import com.charity.charity.entity.UserDonation;
import com.charity.charity.repository.DonationRepository;
import com.charity.charity.repository.UserDonationRepository;
import com.charity.charity.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DonationServiceImpl extends DonationService {



    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserDonationRepository userDonationRepository;

//    @Override
//    public List<Donation> getDonations(String search) {
//        if (search != null && !search.isEmpty()) {
//            return donationRepository.findByNameContainingIgnoreCase(search);
//        }
//        return donationRepository.findAll();
//    }

    @Override
    public Page<Donation> getDonations(String search, Pageable pageable) {
        if (search == null || search.isEmpty()) {
            return donationRepository.findAll(pageable);
        }
        return donationRepository.findByNameContainingIgnoreCase(search, pageable);
    }

    @Override
    public Donation getDonationById(String id) {
        return donationRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NoSuchElementException("Donation not found with ID: " + id));
    }

    @Override
    public List<UserDonation> getDonorsByDonationId(Integer donationId) {
        return userDonationRepository.findByDonationId(donationId);
    }

    @Override
    public UserDonation createDonation(UserDonationRequest request, User loggedUser) {
        Donation donation = donationRepository.findById(request.getDonationId().longValue())
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        UserDonation userDonation = new UserDonation();
        userDonation.setDonation(donation);
        userDonation.setUser(loggedUser);  // Set user from session
        userDonation.setName(request.getName());
        userDonation.setMoney(request.getMoney());
        userDonation.setText(request.getText());
        userDonation.setStatus(1); // Default status for new donations
        userDonation.setCreated(LocalDateTime.now().toString());

        return userDonationRepository.save(userDonation);
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
        donation.setCode(donationDTO.getCode());

        return donationRepository.save(donation);
    }



}
