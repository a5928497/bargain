$(function () {
    $reward_img = $("#reward_img");
    $rewards = $(".rewards");
    $reward_name = $("#reward_name");
    $reward_price = $("#reward_price");
    $reward_id = $("#reward_id");
    $arrow_left = $("#arrow_left_BTN");
    $arrow_left_container = $("#left_arrow_container");
    $arrow_right = $("#arrow_right_BTN");
    $arrow_right_container  = $("#right_arrow_container");
    $backBTN = $("#backBTN");
    $reward_left = $("#reward_left");
    $wrapper = $("#main_wrapper");
    $submitBTN = $("#submitBTN");
    var size = $rewards.size();
    var location = 0;
    var body_container_height = $("#body_container").height();
    var image_size = body_container_height * 0.66;
    //完整路径
    var curWwwPath=window.document.location.href;
    //后缀
    var pathName=window.document.location.pathname;
    //前缀
    var localhostPaht=curWwwPath.substring(0,curWwwPath.indexOf(pathName));
    //初始化图片
    $wrapper.css("background-image","url("+ localhostPaht +"/basic_images/option_pg/option_background.jpg)");
    $arrow_left.css("background-image","url("+ localhostPaht +"/basic_images/option_pg/arrows_left.png)");
    $arrow_right.css("background-image","url("+ localhostPaht +"/basic_images/option_pg/arrows_right.png)");
    $backBTN.css("background-image","url("+ localhostPaht +"/basic_images/option_pg/back_btn.png)");
    $submitBTN.css("background-image","url("+ localhostPaht +"/basic_images/option_pg/select_btn.png)");
    $reward_img.attr("src","/reward_images/reward"+$(".rewards:eq(0)").val() +".png");
    $reward_name.text($(".rewards:eq(0)").attr("name"));
    $reward_price.text($(".rewards:eq(0)").attr("price")+"刀");
    $reward_left.text("商品剩余"+$(".rewards:eq(0)").attr("surplus")+"件");
    $reward_id.val($(".rewards:eq(0)").val());
    $arrow_left.click(function () {
        location = location -1;
        if (location < 0) {
            location = size -1;
        }
        $reward_img.attr("src","/reward_images/reward"+$(".rewards:eq("+location+")").val() +".png")
        $reward_name.text($(".rewards:eq("+location+")").attr("name"));
        $reward_price.text($(".rewards:eq("+location+")").attr("price")+"刀");
        $reward_left.text("商品剩余"+$(".rewards:eq("+location+")").attr("surplus")+"件");
        $reward_id.val($(".rewards:eq("+location+")").val());
       return false;
    });
    $arrow_right.click(function () {
        location = location +1;
        if (location >= size) {
            location = 0;
        }
        $reward_img.attr("src","/reward_images/reward"+$(".rewards:eq("+location+")").val() +".png")
        $reward_name.text($(".rewards:eq("+location+")").attr("name"));
        $reward_price.text($(".rewards:eq("+location+")").attr("price")+"刀");
        $reward_left.text("商品剩余"+$(".rewards:eq("+location+")").attr("surplus")+"件");
        $reward_id.val($(".rewards:eq("+location+")").val());
        return false;
    });
    $backBTN.click(function () {
        var uri = $(this).attr("back_url");
        window.location.replace(uri);
        return false;
    });
})