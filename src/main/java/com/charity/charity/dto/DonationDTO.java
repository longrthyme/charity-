package com.charity.charity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String charityOrganization;
    private String phone;
    private String content;
}
