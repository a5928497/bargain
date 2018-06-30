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
            $.ajax({
                type: "POST",   //提交的方法
                url:"/joinIn", //提交的地址
                data:$("#joinForm").serialize(),// 序列化表单值
                async: false,
                error: function (request) {
                    alert("连接超时！");
                },
                success: function (data) {
                    if (data == true) {
                        //若加入活动成功且未加入活动，跳转至挑选奖品
                    }else if (data == false){
                        //若已经加入活动，则跳转至活动详情
                    }else {
                        //若加入活动失败，则返回错误信息并刷新页面
                        alert("加入活动失败，请重试！");
                        window.location.replace(pathName);
                    }
                }
            });
    })
})