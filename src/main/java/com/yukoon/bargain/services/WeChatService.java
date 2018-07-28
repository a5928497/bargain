package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.AccessToken;
import com.yukoon.bargain.entities.JSAPI_Ticket;
import com.yukoon.bargain.entities.WeChatConfig;
import com.yukoon.bargain.repository.WeChatConfigRepo;
import com.yukoon.bargain.utils.KeyUtil;
import com.yukoon.bargain.utils.SHA1Util;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeChatService {
    /*
    * 若需要修改APPID和APPSECRET，请先删除数据库中WechatConfig的记录
     */
    private final static String APPID = "wx0e5d8871f3db5159";
    private final static String APPSECRET = "31f26fe06da3d360b85deeb8e70f1fb4";
    private final static String ACCESSTOKENURI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&" +
            "appid="+ APPID +"&secret=" + APPSECRET;
    private final static String TICKETURI = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";
    @Autowired
    private WeChatConfigRepo weChatConfigRepo;

    public AccessToken getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessToken> responseEntity = restTemplate.getForEntity(ACCESSTOKENURI,AccessToken.class);
        return responseEntity.getBody();
    }

    public JSAPI_Ticket getTicket() {
        AccessToken accessToken = getAccessToken();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<JSAPI_Ticket> responseEntity = restTemplate
                .getForEntity(TICKETURI+ accessToken.getAccess_token()+"&type=jsapi",JSAPI_Ticket.class);
        return responseEntity.getBody();
    }

    public WeChatConfig signature(String url) {
        //获取配置信息，使用list防止意外插入
        String jsapi_ticket;
        List<WeChatConfig> list = weChatConfigRepo.findAll();
        WeChatConfig weChatConfig;
        if (list.size() > 0 ) {
            //比较时间戳
            weChatConfig = list.get(0);
            Long expiresTime = Long.valueOf(weChatConfig.getTimestamp()) + 7140;
            Long currentTime = System.currentTimeMillis() / 1000;
            if (currentTime >= expiresTime) {
                //若接近过期（在过期时间1min以内），则重新获取token和ticket
                jsapi_ticket = getTicket().getTicket();
                String nonceStr = KeyUtil.getKey(16);
                String timestamp = createTimestamp();
                String signature = "";
                //注意这里参数名必须全部小写，且必须有序
                String string1 = "jsapi_ticket=" + jsapi_ticket +
                        "&noncestr=" + nonceStr +
                        "&timestamp=" + timestamp +
                        "&url=" + url;
                signature = SHA1Util.encode(string1);
                weChatConfig.setAppid(APPID).setJsapi_ticket(jsapi_ticket).setTimestamp(timestamp).setNonceStr(nonceStr)
                        .setSignature(signature);
                System.out.println(signature);
                weChatConfig =  weChatConfigRepo.saveAndFlush(weChatConfig);
            }else {
                //若没过期，直接根据url签名
                String string1 = "jsapi_ticket=" + weChatConfig.getJsapi_ticket() +
                        "&noncestr=" + weChatConfig.getNonceStr() +
                        "&timestamp=" + weChatConfig.getTimestamp() +
                        "&url=" + url;
                weChatConfig.setSignature(SHA1Util.encode(string1));
            }
        }else {
            //若还没创建WeChatConfig记录
            jsapi_ticket = getTicket().getTicket();
            String nonceStr = KeyUtil.getKey(16);
            String timestamp = createTimestamp();
            String signature = "";
            //注意这里参数名必须全部小写，且必须有序
            String string1 = "jsapi_ticket=" + jsapi_ticket +
                    "&noncestr=" + nonceStr +
                    "&timestamp=" + timestamp +
                    "&url=" + url;
            signature = SHA1Util.encode(string1);
            weChatConfig = new WeChatConfig();
            weChatConfig.setAppid(APPID).setJsapi_ticket(jsapi_ticket).setTimestamp(timestamp).setNonceStr(nonceStr)
                    .setSignature(signature);
            System.out.println(signature);
            weChatConfig =  weChatConfigRepo.saveAndFlush(weChatConfig);
        }
        return weChatConfig;
    }

    public String createTimestamp() {
        return Long.toString(System.currentTimeMillis() /1000);
    }


    public static void main(String[] args) {
       WeChatService weChatService = new WeChatService();
       weChatService.signature("www.baidu.com");
    }
}
