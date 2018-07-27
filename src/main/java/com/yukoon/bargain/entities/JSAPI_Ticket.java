package com.yukoon.bargain.entities;

import com.alibaba.druid.filter.AutoLoad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Accessors(chain = true)
public class JSAPI_Ticket {
    private String ticket;
    private String errmsg;
    private Integer errcode;
    private Integer expires_in;
}
