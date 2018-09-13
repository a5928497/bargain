package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Address;
import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.services.AddressService;
import com.yukoon.bargain.services.GameService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AddressController {
	@Autowired
	private AddressService addressService;
	@Autowired
	private GameService gameService;

	//前台判断是否已存在收货地址
	@ResponseBody
	@GetMapping("/addressexist/{gameinfo_id}")
	public Integer isExist(@PathVariable("gameinfo_id")Integer gameinfo_id) {
		Integer flag = 1;
		if (!addressService.addressExisted(gameinfo_id)) {
			flag = 0;
		}
		return flag;
	}

	//保存前台存入的收货地址
	@PostMapping("/address")
	public String add(Address address) {
		GameInfo gi = gameService.findById(address.getGameInfo().getId());
		if (null != gi && gi.getPriceLeft() <= 0) {
			address.setRewardName(gi.getReward().getRewardName());
			addressService.addAddress(address);
		}
		return "redirect:/myrecords";
	}

	//后台查询某一活动所有奖品的收货地址
	@RequiresRoles("admin")
	@GetMapping("/address/{act_id}")
	public String findAllByAct_id(@PathVariable("act_id") Integer act_id, Map<String,Object> map) {
		List<Address> addresses = addressService.findAllByActId(act_id);
		map.put("addresses",addresses);
		return "backend/address_list";
	}
}
