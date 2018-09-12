package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Address;
import com.yukoon.bargain.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AddressController {
	@Autowired
	private AddressService addressService;

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
			addressService.addAddress(address);
		return "redirect:/myrecords";
	}
}
