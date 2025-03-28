package com.charity.charity.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_donation")
public class UserDonation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "money")
    private Integer money;

    @Column(name = "status")
    private Integer status;

    @Column(name = "text", length = 255)
    private String text;

    @Column(name = "created", length = 255)
    private String created;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "donation_id", nullable = false)
    private Donation donation;

    // Getters and Setters
}

