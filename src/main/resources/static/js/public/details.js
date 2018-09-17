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
    //转发跳转
    var pageName = "zh_lcyy_liujia_xnlc2018";
    getAppVersion();
    pageLoadAppLog();
    CMBLS.socialShare = {};
    CMBLS.socialShare.successCallback = function(id, strXML) {}
    CMBLS.socialShare.failCallback = function(id, strXML) {}
    function ShareHot(channel) {
        var title = "";
        var content = "";
        var gameInfo_id = $("#gameInfoId").val();
        $.ajax({
            type : "get",
            url : "/getsharedetails",
            dataType : "json",
            async : false,
            data:{gameInfo_id:gameInfo_id},
            success : function(data) {
                title = encodeURIComponent(data.title);
                content = encodeURIComponent(data.desc);
            },
            error: function(xhr, status, error) {
            }
        });
        var curWwwPath = window.document.location.href;
        window.location.href = "http://CMBLS/socialShare?id=0&type=url&text=" + content + "&title=" + titles + "&channel=" + channel + "&url=" + curWwwPath;
    }
})