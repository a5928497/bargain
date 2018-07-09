package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.repository.GameInfoRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	@Transactional
	public Page getWinnersByActid(Integer pageNo,Integer pageSize,Integer act_id) {
		return PageableUtil.page(pageNo,pageSize,gameInfoRepo.whoswin(act_id));
	}

}
