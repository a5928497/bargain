$(function () {
    $reward_img = $("#reward_img");
    $rewards = $(".rewards");
    $reward_name = $("#reward_name");
    $reward_price = $("#reward_price");
    $reward_id = $("#reward_id");
    $arrow_left = $("#arrow_left_BTN");
    $arrow_right = $("#arrow_right_BTN");
    var size = $rewards.size();
    var location = 0;
    //初始化图片
    $reward_img.attr("src","/reward_images/reward"+$(".rewards:eq(0)").val() +".png")
    $reward_name.text($(".rewards:eq(0)").attr("name"));
    $reward_price.text("价值："+$(".rewards:eq(0)").attr("price")+"元");
    $reward_id.val($(".rewards:eq(0)").val());
    $arrow_left.click(function () {
        location = location -1;
        if (location < 0) {
            location = size -1;
        }
        $reward_img.attr("src","/reward_images/reward"+$(".rewards:eq("+location+")").val() +".png")
        $reward_name.text($(".rewards:eq("+location+")").attr("name"));
        $reward_price.text("价值："+$(".rewards:eq("+location+")").attr("price")+"元");
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
        $reward_price.text("价值："+$(".rewards:eq("+location+")").attr("price")+"元");
        $reward_id.val($(".rewards:eq("+location+")").val());
        return false;
    });
})