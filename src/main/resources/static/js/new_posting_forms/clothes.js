let frontName;
let id;

function showForm(fName, ident) {

    frontName = fName;
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
        '                        <select id="size" name="size" class="custom-select custom-select-sm" required>\n' +
        '                            <option value="" data-marker="option">—</option>\n' +
        '                            <option value="44-46(S)">44-46(S)</option>\n' +
        '                            <option value="46-48(M)">46-48(M)</option>\n' +
        '                            <option value="48-50(L)">48-50(L)</option>\n' +
        '                            <option value="50-52(XL)">50-52(XL)</option>\n' +
        '                            <option value="52-54(XXL)">52-54(XXL)</option>\n' +
        '                            <option value=">54(XXXL)">>54(XXXL)</option>\n' +
        '                            <option value="Без размера">Без размера</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="state" class="col-sm-2 col-form-label">Состояние</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <div id="state" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                            <label class="btn btn-sm btn-outline-secondary">\n' +
        '                                <input type="radio" name="state" value="Новое" id="noInUsage" autocomplete="off"> Новое\n' +
        '                            </label>\n' +
        '                            <label class="btn btn-sm btn-outline-secondary">\n' +
        '                                <input type="radio" name="state" value="Б/у" id="pastInUsage" autocomplete="off"> Б/у\n' +
        '                            </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="typeAd" class="col-sm-2 col-form-label">Вид объявления</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="typeAd" name="typeAd" class="custom-select custom-select-sm" required>\n' +
        '                            <option value="" data-marker="option">—</option>\n' +
        '                            <option value="Продаю своё">Продаю своё</option>\n' +
        '                            <option value="Товар приобретён на продажу">Товар приобретён на продажу</option>\n' +
        '                            <option value="Товар от производителя">Товар от производителя</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +

        '                <div class="form-group row">\n' +
        '                    <label for="title" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="title" maxlength="100" type="text" class="form-control form-control-sm" value="Другое">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Комбинезон зимний Reima 104 см» или «Apple Watch 3 стальной ремешок»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="description" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="description" name="description" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
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
        '                    <label for="photos" class="col-sm-2 col-form-label">Фотографии</label>\n' +
        '\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <label class="photo-upload" data-marker="add">\n' +
        '                            <input id="photos" type="file" value="" multiple="" style="display: none" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input">\n' +
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


let btn = document.getElementById("saveButton");
btn.addEventListener("click", () => {

    let posting = {
        size: $('#size').val(),
        state: document.querySelector('input[name="state"]:checked').value,
        typeAd: $('#typeAd').val(),
        title: $('#title').val(),
        description: $('#description').val(),
        price: $('#price').val(),
        linkYouTube: $('#linkYouTube').val(),
        meetingAddress: $('#meetingAddress').val(),
        contact: $('#inputPhone').val()

    };

    fetch('/api/posting/clothes/' + id, {

        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(posting)
    });

})

