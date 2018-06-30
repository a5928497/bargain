package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.GameInfoRepo;
import com.yukoon.bargain.repository.RewardRepo;
import com.yukoon.bargain.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.Set;

@Service
public class GameService {
	@Autowired
	private GameInfoRepo gameInfoRepo;
	@Autowired
	private RewardRepo rewardRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private BargainService bargainService;

	//用户加入活动
	@Transactional
	public boolean joinIn(Integer user_id,Integer act_id) {
		boolean flag = false;
		//user和act 必须从数据库查出，这样能直接去重
		User user = userService.findById(user_id);
		Activity act = activityService.findById(act_id);
		if (null != user && null != act) {
			user.getActList().add(act);
			userRepo.saveAndFlush(user);
			flag = true;
		}
		return flag;
	}

	//通过活动id与用户id查询游戏记录
	@Transactional
	public GameInfo findByActIdAndUserId(Integer act_id,Integer user_id) {
		return gameInfoRepo.findGameInfoByActIdAndUserId(act_id,user_id);
	}

	//新加入游戏用户新开记录
	@Transactional
	public void newRecord(GameInfo gameInfo) {
		Reward reward = rewardRepo.findOne(gameInfo.getReward().getId());
		User user = userRepo.findOne(gameInfo.getUser().getId());
		if (reward != null && user != null) {
			gameInfo.setReward(reward);
			gameInfo.setPriceLeft(reward.getPrice());
			gameInfo.setTimesLeft(reward.getTimes());
			gameInfoRepo.saveAndFlush(gameInfo);
		}
	}

	//进行砍价
	@Transactional
	public void bargain(GameInfo gameInfo) {
		gameInfo = gameInfoRepo.findOne(gameInfo.getId());
		if (gameInfo != null) {
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			//获得减价随机数
			Double bargainPrice = bargainService.getBargain(gameInfo.getId());
			//保留两位小数并更新数据
			Double priceLeft = Double.parseDouble(df.format(gameInfo.getPriceLeft() - bargainPrice));
			gameInfo.setPriceLeft(priceLeft);
			gameInfo.setTimesLeft(gameInfo.getTimesLeft()-1);
			gameInfoRepo.saveAndFlush(gameInfo);
			//记录砍价者信息
		}

	}
}
