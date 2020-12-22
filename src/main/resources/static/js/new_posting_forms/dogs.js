let postingFormDogs = document.getElementById('visibleElement2');
let saveButtonDogs = document.getElementById('saveButton');

async function sentDogsPosting(frontName, selectedCategoryId) {

    let url = '/api/posting/new/' + frontName + '/' + selectedCategoryId;
    const formData = new FormData();
    const fileField = fileList;

    for (let i = 0; i < fileField.length; i++) {
        formData.append('photos', fileField[i]);
    }
    let price = window.postPrice.value.replace(/\s/g, '');;
    formData.append('title', window.postTitle.value);
    formData.append('breed', window.breed.breed.value);
    formData.append('type', window.postType.value);
    formData.append('description', window.postDescription.value);
    formData.append('price', price === "" ? 0 : price);
    formData.append('linkYouTube', window.postLinkYouTube.value);
    formData.append('meetingAddress', window.inputAddress.value);
    formData.append('contactEmail', window.inputEmail.value);
    formData.append('contact', window.inputPhone.value);
    formData.append('communicationType', document.querySelector('input[name="communication"]:checked').value);

    if (!checkInputFields(formData)) {
        return false;
    }

    await sendFile(formData, url);
    window.location.href = '/';
}

async function sendFile(body, url) {

    try {
        const response = await fetch(url, {
            method: 'POST',
            body: body
        });
        const result = await response.json();
        console.log(JSON.stringify(result));
    } catch (error) {
        console.error('Ошибка:', error);
    }
}

async function getDogBreeds() {
    let response = await fetch("/api/posting/getdogbreeds", {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
    return (await response.json()).data;
}

async function getDogsForm(frontName, selectedCategoryId) {
    let DogsBreeds = await getDogBreeds();
    let dropmenu = '<select class="custom-select custom-select-sm" required>\n';
    for (var i = 0; i < DogsBreeds.length; i++) {
        dropmenu += '<option id="breed" name="breed" value="' + DogsBreeds[i].breed + '">' + DogsBreeds[i].breed + '</option>\n';
    }
    dropmenu += '</select>\n';
    dropmenu += '</div>';
    saveButtonDogs.onclick = () => sentDogsPosting(frontName, selectedCategoryId);
    postingFormDogs.innerHTML = '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postTitle" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="postTitle" name="postTitle" title="Введите название объявления" maxlength="100" type="text" class="form-control form-control-sm">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Щенок джек-рассел-терьера» или «Новый аквариум 30 л»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postState" class="col-sm-2 col-form-label">Порода</label>\n' +
        '                    <div class="col-sm-3">\n' +
        dropmenu +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postType" class="col-sm-2 col-form-label">Вид объявления</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="postType" name="postType" class="custom-select custom-select-sm" required>\n' +
        '                            <option value="Отдаю даром">Отдаю даром</option>\n' +
        '                            <option value="Продаю как заводчик">Продаю как заводчик</option>\n' +
        '                            <option value="Продаю как частное лицо">Продаю как частное лицо</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postDescription" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="postDescription" name="postDescription" title="Пожалуйста, заполните описание" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
        '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPrice" class="col-sm-2 col-form-label">Цена</label>\n' +
        '                    <div class="col-sm-2 d-flex">\n' +
        '                        <input id="postPrice" inputmode="numeric" placeholder="₽" type="number" class="form-control form-control-sm" value="">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPhotos" class="col-sm-2 col-form-label">Фотографии</label>\n' +
        '\n' +
        '                    <div id="photoList" class="listOfPhoto col-sm-6 d-flex flex-wrap">\n' +
        '                        <label for="postPhotos" type="button" class="photo-upload" data-marker="add">\n' +
        '                            <input id="postPhotos" type="file" value="" multiple class="d-none" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input">\n' +
        '                            <div id="uploadPhotos"></div>' +
        '                        </label>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postLinkYouTube" class="col-sm-2 col-form-label">Видео c YouTube</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <input  id="postLinkYouTube" type="url" class="form-control form-control-sm" placeholder="Например: https://www.youtube.com/watch?v=qPeVoi6OmRc">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </form>\n' +
        '        </div>\n' +
        '    </div>'

    choosenFiles = document.getElementById('postPhotos');
    choosenFiles.addEventListener('change', checkFiles, false);
}
