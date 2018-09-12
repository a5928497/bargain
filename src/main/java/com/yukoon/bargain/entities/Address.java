package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "CONTACT_NAME")
	private String contactName;

	@Column(name = "PHONE_NUM")
	private String phoneNum;

	@Column(name = "ADDRESS_DETAILS")
	private String addressDetails;

	@JoinColumn(name = "GAMEINFO_ID")
	@OneToOne
	private GameInfo gameInfo;
}
