package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
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

	@JoinColumn(name = "CODE_ID")
	@OneToOne
	private RedeemCode redeemCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Reward getReward() {
		return reward;
	}

	public void setReward(Reward reward) {
		this.reward = reward;
	}

	public Double getPriceLeft() {
		return priceLeft;
	}

	public void setPriceLeft(Double priceLeft) {
		this.priceLeft = priceLeft;
	}

	public Integer getTimesLeft() {
		return timesLeft;
	}

	public void setTimesLeft(Integer timesLeft) {
		this.timesLeft = timesLeft;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public RedeemCode getRedeemCode() {
		return redeemCode;
	}

	public void setRedeemCode(RedeemCode redeemCode) {
		this.redeemCode = redeemCode;
	}
}
