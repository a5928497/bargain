$(function () {
    $codeInfoBTN = $(".codeInfoBTN");
    $codeInfoBTN.click(function () {
        $this = $(this);
        var uri = $this.attr("uri");
        $.get(uri,function (data) {
            $this.parent().after("<p class='code_msg'>"+data+"</p>");
        })
        return false;
    });
})