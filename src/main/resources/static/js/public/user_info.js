$(function () {
    $codeInfoBTN = $(".codeInfoBTN");
    $codeInfoBTN.click(function () {
        $this = $(this);
        var uri = $this.attr("uri");
        $.get(uri,function (data) {
            $this.parent().parent().after("<tr class='code_msg'><td colspan='4' style='text-align: left'>"+data+"</td></tr>");
        })
        return false;
    });
})