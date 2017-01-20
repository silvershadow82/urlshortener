$(function() {
    var $form = $('#urlCreateForm');

    $form.on('submit', function(e) {
        e.preventDefault();
        var $url = $(this).find('#url');
        var $shortUrl = $(this).find('#shortUrl');
        $.ajax({
            type: 'POST',
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            url: $(this).attr('action'),
            data: JSON.stringify({ url: $url.val() }),
            success: function (data) {
                $shortUrl.val(data.shortURL);
            }
        });
    });
});