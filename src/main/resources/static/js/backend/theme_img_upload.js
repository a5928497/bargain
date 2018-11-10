$(function () {
    $backBTN = $(".backBTN");
    $fileinput = $(".fileinput");
    $fileinput.css("width",300);
    $backBTN.click(function () {
        var uri = $backBTN.attr("back_uri");
        window.location.replace(uri);
        return false;
    });

    var curWwwPath=window.document.location.href;
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    var localhostPaht=curWwwPath.substring(0,pos);
    //根据路径获取图片
    var img_url  = localhostPaht + "/basic_images/";
    $themeImg = $("#theme_img");
    $themeImg.attr("src",img_url+$(":selected").val()).css("width",resizeImg($(this).width())).one("error",function (e) {
        // $(this).parent().remove();
    });
    console.log(resizeImg(12));
    $("select").change(function () {
        $themeImg.removeProp("width")
            .attr("src",img_url+$(":selected").val())
            // .css("width",resizeImg($themeImg.width()));
    });
    //调整图片函数
    function resizeImg(width) {
        if (width > 320) {
            return 320;
        }else {
            return width;
        }
    }



});