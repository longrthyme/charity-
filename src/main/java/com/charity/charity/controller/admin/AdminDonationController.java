package com.charity.charity.controller.admin;

import com.charity.charity.dto.DonationDTO;
import com.charity.charity.entity.Donation;
import com.charity.charity.service.impl.DonationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminDonationController {

    @Autowired
    private DonationServiceImpl donationService;


    @GetMapping("/admin/donation/list")
    public String getAllDonations(@RequestParam(required = false) String search, Model model,  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");



        List<Donation> donations = donationService.getDonations(search, PageRequest.of(page, size)).stream()
                .map(donation -> {
                    try {
                        donation.setStartDate(outputFormat.format(inputFormat.parse(donation.getStartDate())));
                        donation.setEndDate(outputFormat.format(inputFormat.parse(donation.getEndDate())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return donation;
                })
                .collect(Collectors.toList());;
        model.addAttribute("donations", donations);
        return "donations_list";  // Tên file Thymeleaf (donations_list.html)
    }


    @PostMapping("/admin/donations/add")
    public ResponseEntity<?> createDonation(@RequestBody DonationDTO donationDTO) {
        try {
            Donation newDonation = donationService.createDonation(donationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newDonation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Lỗi khi tạo đợt quyên góp: " + e.getMessage());
        }
    }


}
