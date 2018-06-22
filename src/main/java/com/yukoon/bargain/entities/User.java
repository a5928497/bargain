package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class User {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @JoinColumn(name = "ACT_ID")
    @ManyToOne
    private Activity activity;

    @Column(name = "TIMES_LEFT")
    private Integer timesLeft;

    @Column(name = "PRICE_LEFT")
    private double priceLeft;

    @JoinColumn(name = "ROLE_ID")
    @OneToOne
    private Role role;
}
