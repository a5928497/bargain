package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class WeChatConfig {
	private String signature;
	private String nonceStr;
	private String timestamp;
	private String appid;
	private String jsapi_ticket;
	private String imgName;
	private String desc;
	private String title;
}
