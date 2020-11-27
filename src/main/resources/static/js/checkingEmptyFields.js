//для проверки обязательных к заполнению полей, помеченных классом required
function isAllRequiredFieldFilled() {

    let noBlankFields = true;

    $('.required').each(function () {
        if ($(this).val() == '') {
            noBlankFields = false;
            $(this).addClass('error-field');
            let errorId = '#errorFor-' + $(this).attr('id');
            $(errorId).removeClass('hidden');
        }
    });

    if ($('#postPhotos').prop('files').length < 1) {
        noBlankFields = false;
        $('#errorFor-postPhotos').removeClass('hidden');
    }

    return noBlankFields;
}

//прячем сообщения об ошибках на тех полях, где начинают вводить текст
$(document).on('keydown', '.required', function (event) {
    $(event.currentTarget).removeClass('error-field');
    let errorId = '#errorFor-' + $(event.currentTarget).attr('id');
    $(errorId).addClass('hidden');
});

//то же для фото
$(document).on('click', '#postPhotos', function () {
    $('#postPhotos').removeClass('error-field');
    $('#errorFor-postPhotos').addClass('hidden');
});