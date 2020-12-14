let id_business;

let btn2 = document.getElementById("saveButton");

btn2.addEventListener("click", () => {

        const formData = new FormData();
        const fileField = document.querySelector('input[type="file"][multiple]');

        for (let i = 0; i < fileField.files.length; i++) {
            formData.append('photos', fileField.files[i]);
        }
        let price = window.price.value;
        let state;
        if (document.getElementById("state") !== null) {
            state = document.querySelector('input[name="state"]:checked').value;
        } else {
            state = "";
        }

        formData.append('title', window.postTitle.value);
        formData.append('state', state === "" ? null : state);
        formData.append('description', window.postDescription.value);
        formData.append('price', price === "" ? 0 : price);
        formData.append('linkYouTube', window.linkYouTube.value);
        formData.append('meetingAddress', window.inputAddress.value);
        formData.append('contactEmail', window.inputEmail.value);
        formData.append('contact', window.inputPhone.value);
        formData.append('communicationType', document.querySelector('input[name="communication"]:checked').value);

        if (!checkInputFields(formData)) {
            return false;
        }

        fetch('/api/posting/business/' + id_business, {
            method: 'POST',
            body: formData
        }).then(() => window.location.href = '/');
});

function showReadyBusinessForm(fName, ident) {
    id_business = ident;
    document.getElementById('visibleElement2').innerHTML =
        '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="title" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input id="postTitle" name="postTitle" title="Введите название объявления" maxlength="100" type="text" class="form-control form-control-sm">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Интернет-магазин игрушек» или «Фрезерный станок с ЧПУ»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="description" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="postDescription" name="postDescription" title="Пожалуйста, заполните описание" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
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


function showEquipmentForBusinessForm(fName, ident) {
    id_business = ident;
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
        '                    <label for="title" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input id="postTitle" name="postTitle" title="Введите название объявления" maxlength="100" type="text" class="form-control form-control-sm">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Интернет-магазин игрушек» или «Фрезерный станок с ЧПУ»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="description" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="postDescription" name="postDescription" title="Пожалуйста, заполните описание" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
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
