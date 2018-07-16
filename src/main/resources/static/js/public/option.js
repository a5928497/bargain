$(function () {
    $reward_img = $("#reward_img");
    $rewards = $(".rewards");
    $reward_img.attr("src","/reward_images/reward"+$(".rewards:eq(0)").val() +".png")
})