package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.repository.GameInfoRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public Page searchUser(Integer act_id,Integer pageSize,String username) {
		return PageableUtil.page(1,pageSize,gameInfoRepo.searchGameInfoByUsernameAndActid("%"+username+"%",act_id));
	}
}
