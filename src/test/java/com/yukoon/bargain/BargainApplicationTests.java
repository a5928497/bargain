package com.yukoon.bargain;

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
	private RewardRepo rewardRepo;
	@Test
	public void contextLoads() {
		System.out.println(rewardRepo.findByActid(1));
	}

}
