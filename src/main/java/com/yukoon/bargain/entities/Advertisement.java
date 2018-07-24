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
public class Advertisement {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column(name = "ADV_NAME")
    private String adv_name;

    @Column(name = "ADV_LINK")
    private String adv_link;

    @Column(name = "CLICKS")
    private Integer clicks;

    @JoinColumn(name = "ACT_ID")
    @ManyToOne
    private Activity activity;
}
