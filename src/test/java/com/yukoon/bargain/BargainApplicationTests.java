package com.yukoon.bargain;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.*;
import com.yukoon.bargain.services.BargainService;
import com.yukoon.bargain.services.GameService;
import com.yukoon.bargain.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Set;

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

	}

}
