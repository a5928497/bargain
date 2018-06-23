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

	@JoinColumn(name = "ACT_ID")
	@ManyToOne
	private Activity activity;
}
