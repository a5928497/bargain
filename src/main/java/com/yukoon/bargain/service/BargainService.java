package com.yukoon.bargain.service;

import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class BargainService {
    private Double price;
    private int times;
	private int timesLeft;
	private Double priceLeft;
	//砍价数额平均值的倍数，用于生成随机值
	private final static Double MULTIPLE = 1.5;
	private final static DecimalFormat INTFORMAT = new DecimalFormat("######0");
	private final static DecimalFormat DOUBLEFORMAT = new DecimalFormat("######0.00");

    //获得各项参数
	public void initParams() {
		this.price =100.2;
		this.times = 20;
		this.timesLeft =19;
		this.priceLeft = 88.45;
	}

	//随机生成砍价数额
	public Double getRandomValue() {
		Random random = new Random();
		Double average = (price/times)*MULTIPLE;
		Integer range_max = Integer.parseInt(INTFORMAT.format(average));
		Integer range_min = Integer.parseInt(INTFORMAT.format(range_max*0.5));
		Double result = Double.parseDouble(DOUBLEFORMAT.format(random.nextInt(range_max-range_min)+range_min+Math.random()));
		return result;
	}

	//获取最终砍价结果
	public Double getBargain() {
		Double result;
//		initParams();
		if (timesLeft > 1) {
			//若不是最后一次砍价
			 result = getRandomValue();
			while (result > priceLeft) {
				result = getRandomValue();
			}
		}else if (timesLeft == 1){
			//若是最后一次减价，直接减去余数
			result = priceLeft;
		}else {
			//防止异常情况，小于1时返回0
			result = 0.0;
		}
		System.out.println(result);
		return result;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getTimesLeft() {
		return timesLeft;
	}

	public void setTimesLeft(int timesLeft) {
		this.timesLeft = timesLeft;
	}

	public Double getPriceLeft() {
		return priceLeft;
	}

	public void setPriceLeft(Double priceLeft) {
		this.priceLeft = priceLeft;
	}

	public static void main(String[] args) {
		BargainService bs = new BargainService();
		bs.initParams();
		int timesleft = bs.getTimesLeft();
		System.out.println(timesleft);
		for (int i = 1;i<=timesleft ;i++) {
			Double result = bs.getBargain();
			bs.setPriceLeft(Double.parseDouble(DOUBLEFORMAT.format(bs.getPriceLeft()-result)));
			System.out.println(i+"price:"+bs.getPriceLeft());
			bs.setTimesLeft(bs.getTimesLeft()-1);
			System.out.println(i+"times:"+bs.getTimesLeft());
		}

	}
}
