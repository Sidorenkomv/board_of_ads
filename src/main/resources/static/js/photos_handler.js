const approvedExtensions = ["jpeg", "png", "gif", "pjpeg", "jpg"];
const maxFileSize = 10485760;
let fileList = [];
let choosenFiles;
let currentPhotoCount = 0;
let maximumPhotoCount = 10; //для некоторых категорий можно больше


function deletePhoto(event) {
    //определяем индекс удаляемого элемента по id его div
    let id = (event.currentTarget.id).slice(event.currentTarget.id.lastIndexOf('_') + 1);
    fileList.splice(id, 1, null);

    //скрываем его со страницы
    let divToHide = event.currentTarget.parentNode;
    divToHide.classList.add('d-none');

    checkCount(-1);
}

function rotatePhoto(event) {
    //поворачивает фото в canvas, заменяет в списке файлов на перевернутую версию
    let id = (event.currentTarget.id).slice(event.currentTarget.id.lastIndexOf('_') + 1);
    let img = new Image();
    let canvas = document.createElement('canvas');
    let divOfPhoto = document.getElementById('photo_' + id);
    let context = canvas.getContext('2d');

    img.src = URL.createObjectURL(fileList[id]);
    img.onload = function () {
        canvas.width = img.height; //высота canvas = ширине фото, чтобы не обрезалось при перевороте
        canvas.height = img.width;
        context.translate(canvas.width / 2, canvas.height / 2);
        context.rotate(90 * Math.PI / 180);
        context.drawImage(img, (0 - img.width) / 2, (0 - img.height) / 2);

        canvas.toBlob(function (blob) {
            fileList.splice(id, 1, new File([blob], fileList[id].name));
            divOfPhoto.style.cssText = 'background: url(' + URL.createObjectURL(blob) + ') center no-repeat; background-size: cover';
        })
    }
}

function checkFiles() {
    let currentStartIndex = fileList.length;
    fileList = fileList.concat(Array.from(choosenFiles.files));
    choosenFiles.value = "";

    for (let i = currentStartIndex; i < fileList.length; i++) {
        let fileExtension = fileList[i].name.slice(fileList[i].name.lastIndexOf(".") + 1).toLowerCase();
        if (approvedExtensions.indexOf(fileExtension) === -1) {
            $('#alert-extension').show();
            fileList.splice(i, 1);
            i--;
            continue;
        }
        if (fileList[i].size > maxFileSize) {
            $('#alert-size').show();
            fileList.splice(i, 1);
            i--;
            continue;
        }
        if (currentPhotoCount + 1 > maximumPhotoCount) {
            $('#alert-count').show();
            fileList.splice(i, fileList.length - maximumPhotoCount + 1);
            break;
        }
        checkCount(1);
    }
    showPhotosOnPage(fileList, currentStartIndex);
}

function showPhotosOnPage(fileList, currentStartIndex) {
    let photoList = document.getElementById('photoList');

    for (let i = currentStartIndex; i < fileList.length; i++) {
        let divPhoto = document.createElement('div');
        let photoURL = URL.createObjectURL(fileList[i]);
        let turnIDBtn = 'turnImg_' + i;
        let deleteIDBtn = 'deleteImg_' + i;

        divPhoto.innerHTML =
            '<div class="clickable-photo" ' +
            'style="background: url(' + photoURL + ') center no-repeat; background-size: cover" ' +
            'id="photo_' + i + '"> ' +
            '<div class="turn-image" id="' + turnIDBtn + '"></div> ' +
            '<div class="delete-image" id="' + deleteIDBtn + '"></div> ' +
            '</div> ';
        photoList.prepend(divPhoto);
    }

    $('.delete-image').on('click', deletePhoto);
    $('.turn-image').on('click', rotatePhoto);
}

function checkCount(addedCount) {
    currentPhotoCount += addedCount;
    let addPhotoBtn = document.getElementsByClassName('photo-upload')[0];

    if (currentPhotoCount >= maximumPhotoCount && !addPhotoBtn.classList.contains('d-none')) {
        addPhotoBtn.classList.add('d-none');
    }

    if (currentPhotoCount < maximumPhotoCount && addPhotoBtn.classList.contains('d-none')) {
        addPhotoBtn.classList.remove('d-none');
    }
}

//поведение кнопки закрыть на alert bootstrap
$('.alert').on('close.bs.alert', function (event) {
    event.preventDefault();
    $(this).css('display', 'none');
});