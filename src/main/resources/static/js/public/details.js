$(function () {
    $shareBTN = $("#share_BTN");
    $share_msg = $("#share_msg");
    $share_QR = $("#share_QR");
    $BTNs = $("#BTNs_container");
    $helpers = $("#helper_container");
    $advs = $("#adv_container");
    var msg = $("#msg").val();
    $share_msg.hide();
    $share_QR.hide();
    //完整路径
    var curWwwPath=window.document.location.href;
    var url = "http://b.bshare.cn/barCode?site=weixin&url=" + curWwwPath;
    if (msg != "") {
        alert(msg);
    }
    $shareBTN.click(function () {
        //点击分享按钮，清空元素
        $BTNs.remove();
        $helpers.remove();
        $advs.remove();
        $share_msg.text("识别图中二维码来帮我一把！").css("margin-left","30%").css("color","white").show();
        $share_QR.attr("src",url).css("margin-left","35%").show();
       return false;
    });
})