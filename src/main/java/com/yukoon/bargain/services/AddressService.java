package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Address;
import com.yukoon.bargain.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
	@Autowired
	private AddressRepo addressRepo;

	//对应游戏记录下是否存在收货地址
	public boolean addressExisted(Integer gameinfo_id) {
		return addressRepo.findByGameInfoId(gameinfo_id).size() > 0;
	}

	//保存某一游戏记录的收货地址
	public void addAddress (Address address) {
		addressRepo.saveAndFlush(address);
	}
}
