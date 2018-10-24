var logout = function () {
    $.post("/logout", function () {
        $("#user").html('');
        $(".unauthenticated").show();
        $(".authenticated").hide();
    });
    return true;
};

$.get("/getinfo", function (data) {
    $("#user").html(data.userAuthentication.details.name);
    $(".unauthenticated").hide();
    $(".authenticated").show();
});

$.ajaxSetup({
    beforeSend: function (xhr, settings) {
        if (settings.type == 'POST' || settings.type == 'PUT' || settings.type == 'DELETE') {

            // Only send the token to relative URLs i.e. locally.
            if (!(/^http:.*/.test(settings.url) || /^https:.*/.test(settings.url))) {
                xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get('XSRF-TOKEN'));
            }
        }
    }
});