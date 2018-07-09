package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@Entity
public class RedeemCode {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "CODE")
    private String code;

    @JoinColumn(name = "REWARD_ID")
    @ManyToOne
    private Reward reward;

    @JoinColumn(name = "WINNER_ID")
    @ManyToOne
    private User winner;

    @JoinColumn(name = "ACT_ID")
    @ManyToOne
    private Activity activity;

    @Column(name = "AWARD_DATE")
    private Date awardDate;
}
