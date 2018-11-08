$(function () {
    $share_a = $("#share_a");
    var msg = $("#msg").val();
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $wrapper = $("#main_wrapper");
    $helpBTN = $("#help_BTN");
    $joinBTN = $("#join_BTN");
    $shareBTN = $("#share_BTN");
    $recordBTN = $("#record_BTN");
    //初始化图片元素
    $wrapper.css("background-image","url("+ localhostPaht +"/basic_images/details_pg/details_background.jpg)");
    $helpBTN.css("background-image","url("+ localhostPaht +"/basic_images/details_pg/help_btn.png)");
    $joinBTN.css("background-image","url("+ localhostPaht +"/basic_images/details_pg/details_join_btn.png)");
    $shareBTN.css("background-image","url("+ localhostPaht +"/basic_images/details_pg/share_btn.png)");
    $recordBTN.css("background-image","url("+ localhostPaht +"/basic_images/details_pg/records_btn.png)");
    if (msg != "") {
        alert(msg);
    }
    //分享图片地址
    $share_a.attr("href","/share?url="+curWwwPath+"&gameInfo_id="+$("#gameInfoId").val());
})