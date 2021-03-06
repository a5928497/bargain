package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.*;
import com.yukoon.bargain.repository.*;
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
	private HelperInfoRepo helperInfoRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private BargainService bargainService;
	@Autowired
	private HelperInfoService helperInfoService;
	@Autowired
	private ActivityRepo activityRepo;

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

	//通过id查询游戏记录
    @Transactional
    public GameInfo findById(Integer id) {
	    return gameInfoRepo.findOne(id);
    }

	//新加入游戏用户新开记录
	@Transactional
	public GameInfo newRecord(GameInfo gameInfo) {
		Reward reward = rewardRepo.findOne(gameInfo.getReward().getId());
		User user = userRepo.findOne(gameInfo.getUser().getId());
		if (reward != null && user != null) {
			gameInfo.setReward(reward);
			gameInfo.setPriceLeft(reward.getPrice());
			gameInfo.setTimesLeft(reward.getTimes());
			return gameInfoRepo.saveAndFlush(gameInfo);
		}else {
			return null;
		}
	}

//	private final static String SUCCESS_SUFFIX = "太厉害了，您成功的砍下了";
//	private final static String SUCCESS_PREFIX = "元！";
	private final static String BARGAIN_SUCCESS = "您已成功助力！";
	private final static String COMPLETED = "恭喜您，你成功砍下了最后一刀！";
	private final static String NOT_FOUND = "没有这条记录！";
	private final static String HAD_BARGAIN = "您已经砍过了，不要贪心哟！";
	private final static String FINISHED = "已经免费送您了，你还想我怎样，要怎样.....";
	private final static String ERROR = "哎呀，发生错误了，请稍后再来或联系管理员！";
	private final static String OVER_BARGAIN = "您已经帮太多人砍过了，请休息一下吧！";
	//进行砍价
	@Transactional
	public String bargain(HelperInfo helperInfo) {
		GameInfo gameInfo = gameInfoRepo.findOne(helperInfo.getGameInfo().getId());
		Integer people_chain = activityRepo.getPeopleChainById(gameInfo.getActivity().getId());
		Integer bargainedTimes = helperInfoRepo.findByActIdAndHelperId(gameInfo.getActivity().getId(),helperInfo.getHelper().getId()).size();
		String msg = null;
		boolean hadBargain  = helperInfoService.hadBargain(gameInfo.getId(),helperInfo.getHelper().getId());
		boolean notFinished  = gameInfo.getPriceLeft() >0;
		/*
		* 砍价条件，需要完全符合才触发砍价：
		* 记录存在
		* 礼品数量足够（在GameController处控制）
		* 砍价者没帮这个用户进行过砍价
		* 还没砍到0元
		* 没有超出该活动的砍价次数限制
		 */
		if (gameInfo != null && !hadBargain && notFinished &&(people_chain == null || people_chain > bargainedTimes)) {
			DecimalFormat df = new DecimalFormat("#.##");
			df.setMaximumFractionDigits(2);
			//获得减价随机数
			Double bargainPrice = bargainService.getBargain(gameInfo.getId());
			//保留两位小数并更新数据
			Double priceLeft = Double.parseDouble(df.format(gameInfo.getPriceLeft() - bargainPrice));
			gameInfo.setPriceLeft(priceLeft);
			//若砍价者不是本人才扣减人数
			if(gameInfo.getUser().getId() != helperInfo.getHelper().getId()) {
				gameInfo.setTimesLeft(gameInfo.getTimesLeft()-1);
			}
			gameInfoRepo.saveAndFlush(gameInfo);
			//记录砍价者信息
			helperInfo.setBarginPrice(bargainPrice);
			helperInfoRepo.saveAndFlush(helperInfo);
			msg = BARGAIN_SUCCESS;
			//若砍价后完成游戏，即砍完，扣减
			if(gameInfo.getPriceLeft() == 0) {
				msg = msg + COMPLETED;
				Reward reward = rewardRepo.findOne(gameInfo.getReward().getId());
				reward.setSurplus(reward.getSurplus()-1);
				rewardRepo.saveAndFlush(reward);
			}
		}
		else if (gameInfo == null) {
			msg = NOT_FOUND;
		}else if (hadBargain) {
			msg = HAD_BARGAIN;
		}else if (!notFinished) {
			msg = FINISHED;
		}else if (bargainedTimes >= people_chain){
			msg = OVER_BARGAIN;
		}else {
			msg = ERROR;
		}
		return msg;
	}
}
