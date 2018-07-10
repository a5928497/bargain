package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class GameInfoExport {
    private String username;
    private String actName;
    private String rewardName;
    private String redeemCode;
    private Double priceLeft;
    private Integer timesLeft;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRedeemCode() {
        return redeemCode;
    }

    public void setRedeemCode(String redeemCode) {
        this.redeemCode = redeemCode;
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
}
