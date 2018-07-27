package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.AccessToken;
import com.yukoon.bargain.entities.JSAPI_Ticket;
import com.yukoon.bargain.entities.WeChatConfig;
import com.yukoon.bargain.utils.KeyUtil;
import com.yukoon.bargain.utils.SHA1Util;
import lombok.experimental.Accessors;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeChatService {
    private final static String APPID = "wx0e5d8871f3db5159";
    private final static String APPSECRET = "31f26fe06da3d360b85deeb8e70f1fb4";
    private final static String ACCESSTOKENURI = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&" +
            "appid="+ APPID +"&secret=" + APPSECRET;
    private final static String TICKETURI = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=";

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
        System.out.println(responseEntity.getBody());
        return responseEntity.getBody();
    }

    public WeChatConfig signature(JSAPI_Ticket ticket, String url) {
        String jsapi_ticket = ticket.getTicket();
        String nonceStr = KeyUtil.getKey(16);
        String timestamp = createTimestamp();
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        signature = SHA1Util.encode(string1);
        WeChatConfig weChatConfig = new WeChatConfig();
        weChatConfig.setAppid(APPID).setJsapi_ticket(jsapi_ticket).setTimestamp(timestamp).setNonceStr(nonceStr)
                .setSignature(signature);
        System.out.println(signature);
        return weChatConfig;
    }

    public String createTimestamp() {
        return Long.toString(System.currentTimeMillis() /1000);
    }

    public WeChatConfig getWeChatConfig(String url) {
        return signature(getTicket(),url);
    }
    public static void main(String[] args) {
       WeChatService weChatService = new WeChatService();
       weChatService.signature(weChatService.getTicket(),"www.baidu.com");
    }
}
