$(function () {
    $share_a = $("#share_a");
    var msg = $("#msg").val();
    //完整路径
    var curWwwPath=window.document.location.href;
    if (msg != "") {
        alert(msg);
    }
    //分享图片地址
    $share_a.attr("href","/share?url="+curWwwPath+"&gameInfo_id="+$("#gameInfoId").val());
})