package com.yukoon.bargain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
@Entity
public class WeChatConfig {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Integer id;
	private String nonceStr;
	private String timestamp;
	private String appid;
	private String jsapi_ticket;
	@Transient
	private String signature;
	@Transient
	private String imgName;
	@Transient
	private String desc;
	@Transient
	private String title;
}
