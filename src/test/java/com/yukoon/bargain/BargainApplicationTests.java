package com.yukoon.bargain;

import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.*;
import com.yukoon.bargain.services.BargainService;
import com.yukoon.bargain.services.GameService;
import com.yukoon.bargain.services.UserService;
import com.yukoon.bargain.utils.PageableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BargainApplicationTests {
	@Autowired
	private ActivityRepo activityRepo;
	@Autowired
	private RewardRepo rewardRepo;
	@Autowired
	private GameService gameService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private PermissionRepo permissionRepo;
	@Autowired
	private GameInfoRepo gameInfoRepo;
	@Autowired
	private BargainService bargainService;
	@Autowired
	private UserService userService;

	@Test
	public void contextLoads() {
		System.out.println(gameInfoRepo.findGameInfoByActivity_Id(2).size());
	}

}
