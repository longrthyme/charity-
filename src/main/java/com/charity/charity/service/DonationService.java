package com.charity.charity.service;

import com.charity.charity.dto.DonationDTO;
import com.charity.charity.entity.Donation;

import java.util.List;

public abstract class DonationService {
    public abstract List<Donation> getDonations(String search);

   public abstract Donation createDonation(DonationDTO donationDTO);
}
