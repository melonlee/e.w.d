
var curItemID ;

function cacheItem(id){

    curItemID = id;


}

function deleteItem(){

    $.ajax({
        url: '../keys/delete.do',

        type: 'GET',

        data: {
            id: curItemID
        },

        dataType: 'json',

        timeout: 1000,

        error: function () {
            alert('您的网络不稳定请稍后再试,请稍后重试！');
        },
        success: function (result) {
            if (result.status == 1000) {
                window.location.href = "../keys/list.do?s=2"
            } else if (result.status == 1001) {
                alert('您的网络不稳定请稍后再试,请稍后重试！');
            }
        }
    });
}



$(function () {

    $("#key_url_div").hide();
    $("#key_thumb_div").hide();
    $("#msg_thumb").hide();


    $("#key_msgType").change(function() {
        if($("#key_msgType").val()==1){
            $("#key_url_div").hide();
            $("#key_thumb_div").hide();

        }else if ($("#key_msgType").val()==2){

            $("#key_url_div").show();
            $("#key_thumb_div").show();

            $("#key_url").val("");
            $("#key_imageUrl").val("");
        }

    });


});

function getItem(){

    $
        .ajax({
            url: '../account/list.do',

            type: 'GET',

            data: {},

            dataType: 'json',

            timeout: 1000,

            error: function () {
                alert('您的网络不稳定请稍后再试,请稍后重试！');
            },
            success: function (callBack) {
                if (callBack.status == 1000) {
                    $("#key_account").empty();

                    for (var loop = 0; loop < callBack.result.length; loop++) {

                            $("#key_account").append('<option value="'+callBack.result[loop].id+'">'+callBack.result[loop].title+'</option>')
                    }

                } else if (callBack.status == 1001) {

                }
            }
        });
}