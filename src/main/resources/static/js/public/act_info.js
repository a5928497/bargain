$(function () {
    $joinBTN = $("#join_BTN");
    $joinForm = $("#joinForm");
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    $("#join_back_url").val(pathName);
    //添加参与活动按钮监听
    $joinBTN.click(function () {
        $joinForm.submit();
        return false;
    })
})