package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;

	@Transactional
	public boolean addUser(User user) {
		boolean flag = false;
		//判断用户是否已经注册
		if (userRepo.vaildateUsername(user.getUsername()) == null) {
			userRepo.saveAndFlush(user);
			flag = true;
		}
		return flag;
	}
}
