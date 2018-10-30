$(function () {
    $joinBTN = $("#join_BTN");
    $joinForm = $("#joinForm");
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $wrapper = $("#main_wrapper");
    //初始化图片元素
    $wrapper.css("background-image","url("+ localhostPaht +"/basic_images/act_info_pg/actinfo_background.jpg)");
    $joinBTN.css("background-image","url("+ localhostPaht +"/basic_images/act_info_pg/join_btn.png)");

    $("#join_back_url").val(pathName);
    //添加参与活动按钮监听
    $joinBTN.click(function () {
        $joinForm.submit();
        return false;
    })
})