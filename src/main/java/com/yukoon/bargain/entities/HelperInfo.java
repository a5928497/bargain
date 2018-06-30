package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
public class HelperInfo {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @JoinColumn(name = "GAMEINFO_ID")
    @ManyToOne
    private GameInfo gameInfo;

    @JoinColumn(name = "HELPER_ID")
    @ManyToOne
    private User helper;

    @Column(name = "BARGIN_PRICE")
    private Double barginPrice;

}
