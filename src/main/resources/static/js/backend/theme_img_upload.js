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
    // $prevImg = $("#prevImg");
    // if ($prevImg.length != 0) {
    //     $("option[value='"+ $prevImg.val()+"']").attr("selected","selected");
    //
    // }
    var img_url  = localhostPaht + "/basic_images/";
    $themeImg = $("#theme_img");
    $themeImg.attr("src",img_url+$(":selected").val()+"?"+Math.random()).css("width",resizeImg($(this).width()));

    //切换选项时切换图片
    $("select").change(function () {
        $themeImg.removeAttr("style")
            .attr("src",img_url+$(":selected").val()+"?"+Math.random())
            .load(function () {
                $themeImg.css("width",resizeImg($(this).width()));
            });
    });
    //调整图片函数
    function resizeImg(width) {
        if (width > 320) {
            return 320;
        }else {
            return width;
        }
    }
    //调整背景颜色
    $switch = $("#bgc_switch");
    var flag = true;
    $switch.click(function () {
        flag = !flag;
        if (flag) {
            $("#img_container").css("background","red");
            $switch.text("关闭背景颜色");
        }else {
            $("#img_container").removeAttr("style");
            $switch.text("开启背景颜色");
        }
    });

});