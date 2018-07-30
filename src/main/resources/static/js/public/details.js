$(function () {
    $shareBTN = $("#share_BTN");
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
        //点击分享按钮
       return false;
    });

})