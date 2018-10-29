$(function () {
    $vaildateBTN =$("#vaildateBTN");
    $submitBTN = $("#submitBTN");
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $wrapper = $("#main_wrapper");
    //初始化图片元素
    $wrapper.css("background-image","url("+ localhostPaht +"/basic_images/login_pg/login_background.jpg)");
    $vaildateBTN.css("background-image","url("+ localhostPaht +"/basic_images/login_pg/vaildate_btn.png)");
    $submitBTN.css("background-image","url("+ localhostPaht +"/basic_images/login_pg/login_btn.png)");

    $submitBTN.click(function () {
        $("#loginForm").submit();
        return false;
    });

    $vaildateBTN.click(function () {
        $("#phone_number").val($("#username").val());
        $.ajax({
            type: "POST",   //提交的方法
            url:"/vaildatecode", //提交的地址
            data:$("#vaildateForm").serialize(),// 序列化表单值
            async: false,
            error: function (request) {
                alert("连接超时！");
            },
            success: function (data) {
                if (data == true) {
                    alert("发送成功！");
                }else {
                    alert("发送失败！");
                }
            }
        });
        return false;
    });
})