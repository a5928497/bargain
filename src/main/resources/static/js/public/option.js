$(function () {
    $reward_img = $("#reward_img");
    $rewards = $(".rewards");
    $reward_name = $("#reward_name");
    $reward_price = $("#reward_price");
    $reward_id = $("#reward_id");
    $arrow_left = $("#arrow_left_BTN");
    $arrow_right = $("#arrow_right_BTN");
    $backBTN = $("#backBTN");
    $reward_left = $("#reward_left");
    var size = $rewards.size();
    var location = 0;
    var body_container_height = $("#body_container").height();
    var image_size = body_container_height * 0.66;
    //初始化图片
    $reward_img.attr("src","/reward_images/reward"+$(".rewards:eq(0)").val() +".png")
        // .css("width",image_size).css("height",image_size).css("margin-top","11%");
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