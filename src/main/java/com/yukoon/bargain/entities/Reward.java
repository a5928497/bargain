package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
public class Reward {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "REWARD_NAME")
    private String rewardName;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "TIMES")
    private Integer times;

    @Column(name = "TOTAL")
    private Integer total;

    @Column(name = "SURPLUS")
    private Integer surplus;

    @JoinColumn(name = "ACT_ID")
    @ManyToOne
    private Activity activity;
}
