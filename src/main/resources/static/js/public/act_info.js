$(function () {
    var username = $("#username").val();
    $loginFormContainer = $("#loginForm_container");
    $joinBTN = $("#join_BTN");
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $("#join_back_url").val(pathName);
    $loginFormContainer.hide();

    //添加参与活动按钮监听
    $joinBTN.click(function () {
        if (username == "") {
            //若还没登录，显示登录按钮
            $loginFormContainer.show();
            $("#login_back_url").val(pathName);
        }else {
            //若已经登录
            $.ajax({
                type: "POST",   //提交的方法
                url:"/joinIn", //提交的地址
                data:$("#joinForm").serialize(),// 序列化表单值
                async: false,
                error: function (request) {
                    alert("连接超时！");
                },
                success: function (data) {
                    console.log(data);
                }
            });
        }
    })
})