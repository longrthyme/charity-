package com.charity.charity.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDonationRequest {

    private Integer donationId;
    private String name;
    private Integer money;
    private String text;
}
