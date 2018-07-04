package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Entity
public class GameInfo {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;

	@JoinColumn(name = "USER_ID")
	@ManyToOne
	private User user;

	@JoinColumn(name = "REWARD_ID")
	@ManyToOne
	private Reward reward;

	@Column(name = "PRICE_LEFT")
	private Double priceLeft;

	@Column(name = "TIMES_LEFT")
	private Integer timesLeft;

	@Column(name = "IS_CASH")
	private Integer isCash;

	@Column(name = "CASHING_DATE")
	private Date cashingDate;

	@JoinColumn(name = "ACT_ID")
	@ManyToOne
	private Activity activity;
}
