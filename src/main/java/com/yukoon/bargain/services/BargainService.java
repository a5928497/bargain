package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.repository.GameInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;

@Service
public class BargainService {
	@Autowired
	private GameInfoRepo gameInfoRepo;
	//砍价数额平均值的倍数，用于生成随机值
	private final static Double MULTIPLE = 1.5;
	//砍价数额平均值的百分数，用于生成随机值
    private final static Double PRECENT = 0.6;

	//随机生成砍价数额，减价数额在平均值的MULTIPLE倍和PRECENT倍之间
	public Double getRandomValue(Double priceLeft,int timesLeft) {
		DecimalFormat df1 = new DecimalFormat("#.##");
		DecimalFormat df2 = new DecimalFormat("#");
		Random random = new Random();
		Double average = (priceLeft/timesLeft)*MULTIPLE;
		Double result;
		if (average>=1.0) {
			Integer range_max = Integer.parseInt(df2.format(average));
			Integer range_min = Integer.parseInt(df2.format(range_max*PRECENT));
			if (range_max - range_min > 1) {
				result = Double.parseDouble(df1.format(random.nextInt(range_max-range_min)+range_min+Math.random()));
				return result;
			}else {
				result = 0.0;
				while (result <= 0) {
					result = Double.parseDouble(df1.format(Math.random()));
				}
				return result;
			}
		}else {
			result = 0.0;
			while (result <= 0) {
				result = Double.parseDouble(df1.format(Math.random()));
			}
			return result;
		}
	}

	//获取最终砍价结果
	public Double getBargain(Integer gameInfoId) {
		Double result;
		GameInfo gi = gameInfoRepo.findOne(gameInfoId);
		Integer timesLeft = gi.getTimesLeft();
		Double priceLeft = gi.getPriceLeft();
		if (timesLeft > 1) {
			//若不是最后一次砍价
			 result = getRandomValue(priceLeft,timesLeft);
			while (result >= priceLeft) {
				//若获取的砍价数大于等于剩余价值数，则重新获取随机数
				result = getRandomValue(priceLeft,timesLeft);
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
}
