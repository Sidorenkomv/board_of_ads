$(document).ready(function () {
    $("#inputPhone").mask("+7 (999) 999-99-99");
});

let id;

let btn = document.getElementById("saveButton");

btn.addEventListener("click", () => {

    if (validateForm()) {

        const formData = new FormData();
        const fileField = document.querySelector('input[type="file"][multiple]');

        for (let i = 0; i < fileField.files.length; i++) {
            formData.append('photos', fileField.files[i]);
        }
        let price = window.price.value;
        let size;
        if (document.getElementById("size") !== null) {
            size = window.size.value;
        } else {
            size = "";
        }

        formData.append('title', window.title.value);
        formData.append('state', document.querySelector('input[name="state"]:checked').value);
        formData.append('typeAd', window.typeAd.value);
        formData.append('size', size === "" ? null : size);
        formData.append('description', window.description.value);
        formData.append('price', price === "" ? 0 : price);
        formData.append('linkYouTube', window.linkYouTube.value);
        formData.append('meetingAddress', window.dealAddress.value);
        formData.append('contactEmail', window.inputEmail.value);
        formData.append('contact', window.inputPhone.value);
        formData.append('communicationType', document.querySelector('input[name="communication"]:checked').value);


        fetch('/api/posting/clothes/' + id, {

            method: 'POST',
            // headers: {
            //     'Accept': 'application/json',
            //     'Content-Type': 'application/json'
            // },
            body: formData
        }).then(() => window.location.href = '/');

    }
});


function validateForm() {
    if (document.getElementById("size") !== null && document.getElementById("size").options.selectedIndex == 0) {
        let alert_size = "Выберите значение из списка";
        document.getElementById("fill_size").innerHTML = alert_size;
        document.getElementById("size").focus();
        // $('#fill_size').fadeOut();
        return false;

    } else if (document.getElementById("typeAd").options.selectedIndex == 0) {
        let alert_typeAd = "Выберите значение из списка";
        document.getElementById("fill_typeAd").innerHTML = alert_typeAd;
        document.getElementById("typeAd").focus();

        return false;

    } else if (document.getElementById("title").value == "") {

        let alert_title = "Введите название объявления";
        document.getElementById("fill_title").innerHTML = alert_title;
        document.getElementById("title").focus();
        return false;

    } else if (document.getElementById("description").value == "") {

        let alert_description = "Пожалуйста, заполните описание";
        document.getElementById("fill_description").innerHTML = alert_description;
        document.getElementById("description").focus();
        return false;

    } else if (document.getElementById("postPhotos").files.length == 0) {
        let alert_photo = "Загрузите хотя бы 1 фотографию";
        document.getElementById("fill_photo").innerHTML = alert_photo;
        document.getElementById("postPhotos").focus();
        return false;

    } else if (document.getElementById("dealAddress").value == "") {

        let alert_address = "Укажите место сделки";
        document.getElementById("fill_address").innerHTML = alert_address;
        document.getElementById("meetingAddress").focus();
        return false;

    } else if (document.getElementById("inputPhone").value == "") {

        let alert_phone = "Укажите телефон";
        document.getElementById("fill_phone").innerHTML = alert_phone;
        document.getElementById("inputPhone").focus();
        return false;
    }

    return true;
}


function showClothesForm(fName, ident) {
    id = ident;
    document.getElementById('visibleElement2').innerHTML =
        '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +

        '                <div class="form-group row">\n' +
        '                    <label for="size" class="col-sm-2 col-form-label">Размер</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="size" name="size" class="custom-select custom-select-sm"  >\n' +
        '                            <option value="" data-marker="option">—</option>\n' +
        '                            <option value="44-46(S)">44-46(S)</option>\n' +
        '                            <option value="46-48(M)">46-48(M)</option>\n' +
        '                            <option value="48-50(L)">48-50(L)</option>\n' +
        '                            <option value="50-52(XL)">50-52(XL)</option>\n' +
        '                            <option value="52-54(XXL)">52-54(XXL)</option>\n' +
        '                            <option value=">54(XXXL)">>54(XXXL)</option>\n' +
        '                            <option value="Без размера">Без размера</option>\n' +
        '                        </select>\n' +
        '                        <p id="fill_size"></p>\n' +
        '                       </div>\n' +
        '                </div>\n' +

        '                <div class="form-group row">\n' +
        '                    <label for="state" class="col-sm-2 col-form-label">Состояние</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <div id="state" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                            <label class="btn btn-sm btn-outline-secondary">\n' +
        '                                <input type="radio" name="state" value="Новое" id="noInUsage" value="Новое"> Новое\n' +
        '                            </label>\n' +
        '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
        '                                <input type="radio" name="state" value="Б/у" id="pastInUsage" value="Б/у" checked> Б/у\n' +
        '                            </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="typeAd" class="col-sm-2 col-form-label">Вид объявления</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="typeAd" name="typeAd" class="custom-select custom-select-sm"  >\n' +
        '                            <option value="" data-marker="option">—</option>\n' +
        '                            <option value="Продаю своё">Продаю своё</option>\n' +
        '                            <option value="Товар приобретён на продажу">Товар приобретён на продажу</option>\n' +
        '                            <option value="Товар от производителя">Товар от производителя</option>\n' +
        '                        </select>\n' +
        '                        <p  id="fill_typeAd"></p>\n' +

        '                    </div>\n' +
        '                </div>\n' +
        '\n' +

        '                <div class="form-group row">\n' +
        '                    <label for="title" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="title" maxlength="100" type="text" class="form-control form-control-sm" value="Другое"  >\n' +
        '                        <p  id="fill_title"></p>\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Комбинезон зимний Reima 104 см» или «Apple Watch 3 стальной ремешок»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="description" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="description" name="description"   rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
        '                        <p  id="fill_description"></p>\n' +
        '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="price" class="col-sm-2 col-form-label">Цена</label>\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <input id="price" inputmode="numeric" placeholder="₽" type="text" class="form-control form-control-sm" value="">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPhotos" class="col-sm-2 col-form-label">Фотографии</label>\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <label class="" data-marker="add">\n' +
        '                            <input id="postPhotos" type="file" value="" multiple style="display: block" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input" >\n' +
        '                            <div id="uploadPhotos"></div>' +
        '                        <p  id="fill_photo"></p>\n' +
        '                        </label>\n' +
        '                    </div>\n' +
        '                </div>\n' +

        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="linkYouTube" class="col-sm-2 col-form-label">Видео c YouTube</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <input  id="linkYouTube" type="url" class="form-control form-control-sm" placeholder="Например: https://www.youtube.com/watch?v=qPeVoi6OmRc">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </form>\n' +
        '        </div>\n' +
        '    </div>'
}

function showShoesForm(fName, ident) {
    id = ident;
    document.getElementById('visibleElement2').innerHTML =

        '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +

        '                <div class="form-group row">\n' +
        '                    <label for="size" class="col-sm-2 col-form-label">Размер</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="size" name="size" class="custom-select custom-select-sm" >\n' +
        '                            <option value="" data-marker="option">—</option>\n' +
        '                            <option value="35"><35</option>\n' +
        '                            <option value="36">36</option>\n' +
        '                            <option value="37">37</option>\n' +
        '                            <option value="38">38</option>\n' +
        '                            <option value="39">39</option>\n' +
        '                            <option value="40">40</option>\n' +
        '                            <option value="41">41</option>\n' +
        '                            <option value="42">42</option>\n' +
        '                            <option value="43">43</option>\n' +
        '                            <option value="44">44</option>\n' +
        '                            <option value="45">45</option>\n' +
        '                            <option value=">46">>46</option>\n' +
        '                        </select>\n' +
        '                        <p id="fill_size"></p>\n' +

        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="state" class="col-sm-2 col-form-label">Состояние</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <div id="state" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                            <label class="btn btn-sm btn-outline-secondary">\n' +
        '                                <input type="radio" name="state" value="Новое" id="noInUsage" value="Новое"> Новое\n' +
        '                            </label>\n' +
        '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
        '                                <input type="radio" name="state" value="Б/у" id="pastInUsage" value="Б/у" checked> Б/у\n' +
        '                            </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="typeAd" class="col-sm-2 col-form-label">Вид объявления</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="typeAd" name="typeAd" class="custom-select custom-select-sm" >\n' +
        '                            <option value="" data-marker="option">—</option>\n' +
        '                            <option value="Продаю своё">Продаю своё</option>\n' +
        '                            <option value="Товар приобретён на продажу">Товар приобретён на продажу</option>\n' +
        '                            <option value="Товар от производителя">Товар от производителя</option>\n' +
        '                        </select>\n' +
        '                        <p  id="fill_typeAd"></p>\n' +

        '                    </div>\n' +
        '                </div>\n' +
        '\n' +

        '                <div class="form-group row">\n' +
        '                    <label for="title" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="title" maxlength="100" type="text" class="form-control form-control-sm" value="Другое">\n' +
        '                        <p  id="fill_title"></p>\n' +

        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Комбинезон зимний Reima 104 см» или «Apple Watch 3 стальной ремешок»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="description" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="description" name="description" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
        '                        <p  id="fill_description"></p>\n' +
        '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="price" class="col-sm-2 col-form-label">Цена</label>\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <input id="price" inputmode="numeric" placeholder="₽" type="text" class="form-control form-control-sm" value="">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPhotos" class="col-sm-2 col-form-label">Фотографии</label>\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <label class="" data-marker="add">\n' +
        '                            <input id="postPhotos" type="file" value="" multiple style="display: block" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input" >\n' +
        '                            <div id="uploadPhotos"></div>' +
        '                        <p  id="fill_photo"></p>\n' +
        '                        </label>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="linkYouTube" class="col-sm-2 col-form-label">Видео c YouTube</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <input  id="linkYouTube" type="url" class="form-control form-control-sm" placeholder="Например: https://www.youtube.com/watch?v=qPeVoi6OmRc">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </form>\n' +
        '        </div>\n' +
        '    </div>'

}

function showOtherClothesForm(fName, ident) {
    id = ident;
    document.getElementById('visibleElement2').innerHTML =

        '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="state" class="col-sm-2 col-form-label">Состояние</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <div id="state" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                            <label class="btn btn-sm btn-outline-secondary">\n' +
        '                                <input type="radio" name="state" value="Новое" id="noInUsage" value="Новое"> Новое\n' +
        '                            </label>\n' +
        '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
        '                                <input type="radio" name="state" value="Б/у" id="pastInUsage" value="Б/у" checked> Б/у\n' +
        '                            </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="typeAd" class="col-sm-2 col-form-label">Вид объявления</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="typeAd" name="typeAd" class="custom-select custom-select-sm" >\n' +
        '                            <option value="" data-marker="option">—</option>\n' +
        '                            <option value="Продаю своё">Продаю своё</option>\n' +
        '                            <option value="Товар приобретён на продажу">Товар приобретён на продажу</option>\n' +
        '                            <option value="Товар от производителя">Товар от производителя</option>\n' +
        '                        </select>\n' +
        '                        <p  id="fill_typeAd"></p>\n' +

        '                    </div>\n' +
        '                </div>\n' +
        '\n' +

        '                <div class="form-group row">\n' +
        '                    <label for="title" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="title" maxlength="100" type="text" class="form-control form-control-sm" value="Другое">\n' +
        '                        <p  id="fill_title"></p>\n' +

        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Комбинезон зимний Reima 104 см» или «Apple Watch 3 стальной ремешок»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="description" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="description" name="description" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
        '                        <p  id="fill_description"></p>\n' +
        '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="price" class="col-sm-2 col-form-label">Цена</label>\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <input id="price" inputmode="numeric" placeholder="₽" type="text" class="form-control form-control-sm" value="">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPhotos" class="col-sm-2 col-form-label">Фотографии</label>\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <label class="" data-marker="add">\n' +
        '                            <input id="postPhotos" type="file" value="" multiple style="display: block" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input" >\n' +
        '                            <div id="uploadPhotos"></div>' +
        '                        <p  id="fill_photo"></p>\n' +
        '                        </label>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="linkYouTube" class="col-sm-2 col-form-label">Видео c YouTube</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <input  id="linkYouTube" type="url" class="form-control form-control-sm" placeholder="Например: https://www.youtube.com/watch?v=qPeVoi6OmRc">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </form>\n' +
        '        </div>\n' +
        '    </div>'

}
