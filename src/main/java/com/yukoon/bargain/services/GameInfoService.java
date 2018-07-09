package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.repository.GameInfoRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GameInfoService {
	@Autowired
	private GameInfoRepo gameInfoRepo;

	@Transactional
	public Page getPageableByActid(Integer act_id,Integer pageNo,Integer pageSize) {
		return PageableUtil.page(pageNo,pageSize,gameInfoRepo.findGameInfoByActivity_Id(act_id));
	}

	@Transactional
	public Page searchUser(Integer act_id,Integer pageNo,Integer pageSize,String username) {
		return PageableUtil.page(pageNo,pageSize,gameInfoRepo.searchGameInfoByUsernameAndActid("%"+username+"%",act_id));
	}

	//查询某一活动下获奖者
	@Transactional
	public Page getWinnersByActid(Integer pageNo,Integer pageSize,Integer act_id) {
		return PageableUtil.page(pageNo,pageSize,gameInfoRepo.whoswin(act_id));
	}

	//查询某一活动下未有兑换码的获奖者
	@Transactional
	public Page getUncashWinnersByActid(Integer pageNo,Integer pageSize,Integer act_id) {
		List<GameInfo> list = gameInfoRepo.whoswin(act_id);
		List<GameInfo> list_new = new ArrayList<>();
		for (GameInfo gi : list) {
			if (gi.getRedeemCode() == null) {
				list_new.add(gi);
			}
		}
		return PageableUtil.page(pageNo,pageSize,list_new);
	}

	//查询某一活动下已有兑换码的获奖者
	@Transactional
	public Page getCashWinnersByActid(Integer pageNo,Integer pageSize,Integer act_id) {
		List<GameInfo> list = gameInfoRepo.whoswin(act_id);
		List<GameInfo> list_new = new ArrayList<>();
		for (GameInfo gi : list) {
			if (gi.getRedeemCode() != null) {
				list_new.add(gi);
			}
		}
		return PageableUtil.page(pageNo,pageSize,list_new);
	}
}
