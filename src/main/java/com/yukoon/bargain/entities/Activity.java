package com.yukoon.bargain.entities;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Accessors(chain = true)
public class Activity {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Getter
    @Setter
    private Integer id;

    @Column(name = "ACT_NAME")
    @Getter
    @Setter
    private String act_name;

    @Column(name = "ACT_STATUS")
    @Getter
    @Setter
    private Integer act_status;

    @Column(name = "cashingInfo")
    @Getter
    @Setter
    private String cashingInfo;

    @Column(name = "PEOPLE_CHAIN")
    @Getter
    @Setter
    private Integer people_chain;

    //中间表被控方
    @ManyToMany(mappedBy = "actList",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @Getter
    @Setter
    private Set<User> userList;
}
