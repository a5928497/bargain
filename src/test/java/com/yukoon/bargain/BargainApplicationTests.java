package com.yukoon.bargain;

import com.yukoon.bargain.entities.Advertisement;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.*;
import com.yukoon.bargain.services.BargainService;
import com.yukoon.bargain.services.GameService;
import com.yukoon.bargain.services.RedeemCodeService;
import com.yukoon.bargain.services.UserService;
import com.yukoon.bargain.utils.PageableUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
	@Autowired
	private RedeemCodeRepo redeemCodeRepo;
	@Autowired
	private RedeemCodeService redeemCodeService;
	@Autowired
	private AdvertisementRepo advertisementRepo;
	@Autowired
	private HelperInfoRepo helperInfoRepo;
	@Autowired
	private AddressRepo addressRepo;
	@Test
	public void contextLoads() {
		System.out.println(addressRepo.findByGameInfoId(5).size());
		}
}
