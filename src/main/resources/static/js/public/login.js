$(function () {
    $vaildateBTN =$("#vaildateBTN");
    $submitBTN = $("#submitBTN");

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