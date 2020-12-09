function removeErrors(element) {
    $(element).on("input change", function () {
        const chkField = $("[name='" + this.name + "']");

        if ($(chkField).next().next().is('.text-muted')) {
            $(chkField).next().next().css("visibility", "visible");
        }
        $('#' + "chk_" + this.name).remove();
        $(this).removeClass('redBorder');
    });
}

function checkInputFields(formData) {

// Удаление красного бордера при заполнении поля

    removeErrors("input");
    removeErrors("textarea");

// Проверка полей
    let arr = ["typeOfHousing", "description", "title", "meetingAddress", "contactEmail", "contact"]; // Какие поля с именами проверять
    var arrRealNames = new Map([
        ['description', 'postDescription'],
        ['title', 'postTitle'],
        ['meetingAddress', 'dealAddress'],
        ['contactEmail', 'email'],
        ['contact', 'inputPhone'],
        ['typeOfHousing', 'estate']
    ]);

    $(".form-control").removeClass('redBorder');
    for (const key of formData.keys()) {
        if (arr.includes(key)) {
            let value = formData.get(key).trim();
            if (value === '') {
                var nameField = arrRealNames.get(key);
                if (typeof nameField != "undefined") {
                    var title = "Заполните поле";
                    var field = "[name='" + nameField + "']";
                    if(typeof $(field).attr('title') !== 'undefined' && $(field).attr('title').length > 0)
                        title = $(field).attr('title');
                        if ($(field).next().is('.text-muted')) {
                            console.log($(field).next());
                            $(field).next().css("visibility", "hidden");
                        }
                    $("[name='" + nameField + "']").addClass('redBorder').after("<div id='chk_" + nameField + "' class='cFM_wrong'>" + title + "</div>");
                    $("html, body").animate({scrollTop: 0}, 600);
                }
            }
        }
    }

    if ($(".form-control.redBorder").length > 0) {
        console.log($(".form-control.redBorder").length)
        return false;
    }
    return true;
}