let frontNamePH;
let selectedCategoryIdPH;
let fileList;
const postingFormHobbyAndRest = document.getElementById('visibleElement2');
const saveButtonPH = document.getElementById('saveButton');
const approvedExtensions = ["jpeg", "png", "gif", "pjpeg", "jpg"];
const maxFileSize = 10485760;
const urlPhotoUpload = "/api/posting/saveMiniatures";

function deletePhoto(event) {
    //определяем индекс удаляемого элемента по id его div
    let id = (event.currentTarget.id).slice(event.currentTarget.id.lastIndexOf('_') + 1);
    fileList.splice(id, 1, null);

    //скрываем его со страницы
    let divToHide = event.currentTarget.parentNode;
    divToHide.classList.add('hidden');
}

function checkFiles() {
    fileList = Array.from(choosenFiles.files);
    for (let i = 0; i < fileList.length; i++) {

        let fileExtension = fileList[i].name.slice(fileList[i].name.lastIndexOf(".") + 1).toLowerCase();
        if (approvedExtensions.indexOf(fileExtension) === -1) {
            alert("Неверное расширение");
            $('#alert').show();
            fileList.splice(i, 1);
            i--;
            continue;
        }
        if (fileList[i].size > maxFileSize) {
            alert("Слишком большой файл");
            fileList.splice(i, 1);
            i--;
        }
    }
    showPhotoOnPage(fileList);
}

// async function showPhotoOnPage(photos) {
//     const formData = new FormData();
//     photos.forEach(photo => formData.append('file', photo));
//     const response = await fetch(urlPhotoUpload, {
//         method: 'POST',
//         body: formData
//     })
//
//     if (response.ok) {
//         let imgUrls = await response.json();
//         addImgToList(imgUrls.data);
//     }
// }
function showPhotoOnPage(fileList) {
    let photoList = document.getElementById('photoList');

    fileList.forEach(function (photo, index) {
        let divPhoto = document.createElement('div');
        let photoURL = URL.createObjectURL(photo);
        let turnIDBtn = 'turnImg_' + index;
        let deleteIDBtn = 'deleteImg_' + index;

        divPhoto.innerHTML =
            '<div class="clickable-photo" ' +
            'style="background: url(' + photoURL + ') center no-repeat; background-size: contain" ' +
            'id="photo_' + index + '"> ' +
            '<div class="turn-image" id="' + turnIDBtn + '"></div> ' +
            '<div class="delete-image" id="' + deleteIDBtn + '"></div> ' +
            '</div> ';
        divPhoto.smt = {name: 'data'};
        photoList.append(divPhoto);


        // document.getElementById(deleteIDBtn).addEventListener('click', function (ev) {

    });
    $('.delete-image').on('click', deletePhoto);
    // document.getElementsByClassName('turn-image').addEventListener('click', test);

    // document.getElementById(deleteIDBtn).onclick = () => fileList.slice(index, 1);
    // document.getElementById(deleteIDBtn).onclick(ev, rev);

}

//
// function addImgToList(imgUrls) {
//     let photoList = document.getElementById('photoList');
//
//     imgUrls.forEach(function (currentValue) {
//         let newPhoto = document.createElement('div');
//         newPhoto.className = 'clickable-photo';
//         let cu = currentValue.replaceAll('\\', '\/')
//         let path = 'background: url(.\/' + cu + ') center no-repeat;';
//         newPhoto.style.cssText = path;
//         photoList.append(newPhoto);
//     })
// }

async function sentForHobbyAndRestPosting() {
    let url = '/api/posting/new/' + frontNamePH + '/' + selectedCategoryIdPH;
    const formData = new FormData();

    for (let i = 0; i < fileList.length; i++) {
        if (fileList[i] == null) {
            continue
        }
        formData.append('photos', fileList  [i]);
    }
    let price = window.postPrice.value;
    formData.append('title', window.postTitle.value);
    formData.append('state', document.querySelector('input[name="state"]:checked').value);
    formData.append('type', window.postType.value);
    formData.append('description', window.postDescription.value);
    formData.append('price', price === "" ? 0 : price);
    formData.append('linkYouTube', window.postLinkYouTube.value);
    formData.append('meetingAddress', window.inputAddress.value);
    formData.append('contactEmail', window.inputEmail.value);
    formData.append('contact', window.inputPhone.value);
    formData.append('communicationType', document.querySelector('input[name="communication"]:checked').value);

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

function getHobbyAndRestForm(frontName, selectedCategoryId) {
    frontNamePH = frontName;
    selectedCategoryIdPH = selectedCategoryId;
    saveButtonPH.onclick = () => isAllRequiredFieldFilled() ? sentForHobbyAndRestPosting() : window.scrollTo(0, 0);

    postingFormHobbyAndRest.innerHTML = '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postTitle" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="postTitle" maxlength="100" type="text" class="form-control form-control-sm required">\n' +
        '                        <p id="errorFor-postTitle" class="hidden error-text" data-toggle="tooltip" data-placement="top">Введите название объявления</p>\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Велосипед Stels Navigator 700 MD» или «Электрогитара Ibanez GSA60»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postState" class="col-sm-2 col-form-label">Состояние</label>\n' +
        '                    <div class="col-sm-10">\n' +
        '                        <div id="postState" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                            <label class="btn btn-sm btn-outline-secondary">\n' +
        '                                <input type="radio" name="state" id="noInUsage" value="Новое" checked>Новое' +
        '                            </label>\n' +
        '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
        '                                <input type="radio" name="state" id="pastInUsage" value="Б/у">Б/у' +
        '                            </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postType" class="col-sm-2 col-form-label">Вид объявления</label>\n' +
        '                    <div class="col-sm-3">\n' +
        '                        <select id="postType" name="postType" class="custom-select custom-select-sm" required>\n' +
        '                            <option value="Продаю своё">Продаю своё</option>\n' +
        '                            <option value="Товар приобретён на продажу">Товар приобретён на продажу</option>\n' +
        '                            <option value="Товар от производителя">Товар от производителя</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postDescription" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <textarea id="postDescription" name="postDescription" rows="6" maxlength="5000" style="height: 130px;" class="form-control required"></textarea>\n' +
        '                        <p id="errorFor-postDescription" class="hidden error-text" data-toggle="tooltip" data-placement="top">Пожалуйста, заполните описание</p>\n' +
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
        '                    <div id="photoList" class="listOfPhoto col-sm-6 d-flex">\n' +
        '                       <div class="clickable-photo">' +
        '                               <div class="turn-image"></div>' +
        '                               <div class="delete-image"></div>' +
        '                    </div>' +
        '                        <label for="postPhotos" type="button" class="photo-upload" data-marker="add">\n' +
        '                            <input id="postPhotos" type="file" value="" multiple class="hidden" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input">\n' +
        // '                            <input id="postPhotos" type="file" value="" multiple style="display: block " class="hidden" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input">\n' +
        '                            <div id="uploadPhotos"></div>' +
        '                            <p id="errorFor-postPhotos" class="hidden error-text" data-toggle="tooltip" data-placement="top">Загрузите хотя бы одну фотографию</p>\n' +
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
    // choosenFiles.onchange = checkFiles();
    choosenFiles.addEventListener('change', checkFiles, false);
}