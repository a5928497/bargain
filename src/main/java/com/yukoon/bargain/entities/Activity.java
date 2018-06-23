package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Activity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;

    @Column(name = "ACT_NAME")
    private String act_name;

    @Column(name = "ACT_STATUS")
    private Integer act_status;

    @Column(name = "cashingInfo")
    private String cashingInfo;

    //中间表被控方
    @ManyToMany(mappedBy = "actList",cascade = CascadeType.ALL)
    private Set<User> userList;
}
