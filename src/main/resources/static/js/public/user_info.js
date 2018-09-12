$(function () {
    $codeInfoBTN = $(".codeInfoBTN");
    $addressForm = $("#addressForm");
    $addressForm.hide();
    $codeInfoBTN.click(function () {
        $this = $(this);
        var gameinfo = $this.attr("gameinfo");
        var address_uri = "/addressexist/" + gameinfo;
        var msg = ""
        //检查该获奖游戏记录下是否存在收货地址，0为不存在，1为已存在
        $.get(address_uri,function (data) {
            //若不存在，显示地址表单，并修改action
            if (data == "0") {
                msg = "请先在下方填写收货地址后按保存确认！";
                $addressForm.show();
                $("#gameinfo_input").val(gameinfo);
            }
        });

        var code_uri = "/getcodeinfo/"+gameinfo;
        $.get(code_uri,function (data) {
            $(".code_msg").remove();
            $this.parent().parent().after("<tr class='code_msg'><td colspan='4' style='text-align: left'>"+data+msg+"</td></tr>");
        });
        return false;
    });
})