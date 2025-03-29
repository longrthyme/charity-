package com.charity.charity.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "donation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "code", length = 255, nullable = false, unique = true)
    private String code;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "organization_name", length = 255)
    private String organizationName;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "start_date", length = 255)
    private String startDate;

    @Column(name = "end_date", length = 255)
    private String endDate;

    @Column(name = "money")
    private Integer money;

    @Column(name = "status")
    private Integer status;

    @Column(name = "created", length = 255)
    private String created;

    // Getters and Setters
}
