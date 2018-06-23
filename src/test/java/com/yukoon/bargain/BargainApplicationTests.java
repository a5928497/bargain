package com.yukoon.bargain;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.ActivityRepo;
import com.yukoon.bargain.repository.RewardRepo;
import com.yukoon.bargain.services.GameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BargainApplicationTests {
	@Autowired
	private ActivityRepo activityRepo;
	@Autowired
	private RewardRepo rewardRepo;
	@Autowired
	private GameService gameService;

	@Test
	public void contextLoads() {
		User user = new User();
		user.setId(1);
		Reward reward = new Reward();
		reward.setId(2);
		Activity activity = new Activity();
		activity.setId(1);
		GameInfo gameInfo = new GameInfo();
		gameInfo.setReward(reward).setUser(user).setActivity(activity);
		gameService.joinIn(gameInfo);
	}

}
