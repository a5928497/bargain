package com.yukoon.bargain;

import com.yukoon.bargain.repository.ActivityRepo;
import com.yukoon.bargain.repository.RewardRepo;
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

	@Test
	public void contextLoads() {
		System.out.println(activityRepo.findOne(2));
	}

}
