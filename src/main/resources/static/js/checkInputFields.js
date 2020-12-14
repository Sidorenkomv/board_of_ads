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
    removeErrors("select");

// Проверка полей
    let arr = ["sleeper", "numberOfBeds", "view", "loggia", "totalArea", "floorsInHouse", "floor", "typeOfHouse", "rooms", "experienceValue", "duties", "frequency",
        "schedule", "size", "typeAd", "description", "title", "meetingAddress", "contactEmail", "contact"]; // Какие поля с именами проверять
    var arrRealNames = new Map([
        ['sleeper', 'sleeper'],
        ['numberOfBeds', 'numberOfBeds'],
        ['view', 'view'],
        ['loggia', 'loggia'],
        ['totalArea', 'TotalArea'],
        ['floorsInHouse', 'floorIsHome'],
        ['floor', 'floor'],
        ['typeOfHouse', 'tip'],
        ['rooms', 'planirovka'],
        ['experienceValue', 'postExperienceValue'],
        ['frequency', 'postFrequency'],
        ['duties', 'postDuties'],
        ['size', 'postSize'],
        ['schedule', 'postSchedule'],
        ['description', 'postDescription'],
        ['title', 'postTitle'],
        ['meetingAddress', 'dealAddress'],
        ['contactEmail', 'email'],
        ['contact', 'inputPhone'],
        ['typeAd', 'typeAd']
    ]);

    $(".form-control").removeClass('redBorder');
    for (const key of formData.keys()) {
        if (arr.includes(key)) {
            let value = formData.get(key).trim();
            if ((value === '') || (value === 'null')) {
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
                    $("html, body").animate({scrollTop: 0}, 50);
                }
            }
        }
    }
    if ($(".form-control.redBorder").length > 0) {
        return false;
    }
    return true;
}