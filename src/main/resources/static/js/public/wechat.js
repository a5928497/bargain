$(function () {
    new_element=document.createElement("script");
    new_element.setAttribute("type","text/javascript");
    new_element.setAttribute("src","http://res.wx.qq.com/open/js/jweixin-1.2.0.js");
    var url = window.document.location.href;//url不能写死
    var pathName=window.document.location.pathname;
    var pos=url.indexOf(pathName);
    var localhostPaht=url.substring(0,pos);
    var gameInfo_id = $("#gameInfoId").val();
    console.log(localhostPaht)
    $.ajax({
        type : "get",
        url : "/getwechatconfig",
        dataType : "json",
        async : false,
        data:{url:url,gameInfo_id:gameInfo_id},
        success : function(data) {
            wx.config({
                debug: false,////生产环境需要关闭debug模式
                appId: data.appid,//appId通过微信服务号后台查看
                timestamp: data.timestamp,//生成签名的时间戳
                nonceStr: data.nonceStr,//生成签名的随机字符串
                signature: data.signature,//签名
                jsApiList: [//需要调用的JS接口列表
                    'checkJsApi',//判断当前客户端版本是否支持指定JS接口
                    'onMenuShareTimeline',//分享给好友
                    'onMenuShareAppMessage'//分享到朋友圈
                ]
            });

            wx.ready(function () {

                // 微信分享的数据
                var shareData = {
                    "imgUrl" : pathName+"/thanks.png",    // 分享显示的缩略图地址
                    "link" : window.location.href,    // 分享地址
                    "desc" : data.desc,   // 分享描述
                    "title" : data.title,   // 分享标题
                    success : function () {
                        }
                    };
                //分享朋友圈
                wx.onMenuShareTimeline(shareData);
                //分享给好友
                wx.onMenuShareAppMessage(shareData);
                wx.error(function (res) {
                    alert(res.errMsg);
                });
            });
        },
        error: function(xhr, status, error) {
            //alert(status);
            //alert(xhr.responseText);
        }
    });
});