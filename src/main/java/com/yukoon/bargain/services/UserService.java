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
	public void addUser(User user) {
		userRepo.saveAndFlush(user);
	}
}
