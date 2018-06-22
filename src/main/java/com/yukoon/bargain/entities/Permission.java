package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Entity
public class Permission {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer Id;

    @Column(name = "PERM_NAME")
    private String permName;

    @JoinColumn(name = "ROLE_ID")
    @ManyToOne
    private Role role;
}
