package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.Role;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.ActivityRepo;
import com.yukoon.bargain.repository.UserRepo;
import com.yukoon.bargain.utils.EncodeUtil;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ActivityRepo activityRepo;

	@Transactional
	public boolean addUser(User user) {
		boolean flag = false;
		//判断用户是否已经注册
		if (userRepo.vaildateUsername(user.getUsername()) == null) {
			user.setRole(new Role().setId(1));
			user.setPassword(EncodeUtil.encodePassword(user.getPassword(),user.getUsername()));
			userRepo.saveAndFlush(user);
			flag = true;
		}
		return flag;
	}

	@Transactional
	public User login(User user) {
		User user_temp = userRepo.login(user.getUsername());
		//验证密码
		if (user_temp != null && user_temp.getPassword().equals(user.getPassword())) {
			return user_temp;
		}
		return null;
	}

	@Transactional
	public User autoLogin(String username) {
		return userRepo.login(username);
	}

	@Transactional
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Transactional
	public User findById(Integer id) {
		return userRepo.findOne(id);
	}

	@Transactional
	public Integer findIdByUsername(String username) {
		return userRepo.findIdByUsername(username);
	}

}
