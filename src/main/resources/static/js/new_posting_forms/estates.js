async function sentSellEstatePosting(frontName, categoryId) {
    console.log(frontName + " " + categoryId)
    let url = '/api/posting/new/' + frontName + '/' + categoryId;
    console.log(url)
    const formData = new FormData();
    const fileField = fileList;

    for (let i = 0; i < fileField.length; i++) {
        formData.append('photos', fileField[i]);
    }
    let price = window.postPrice.value.replace(/\s/g, '');;
    formData.append('title', window.postTitle.value);
    formData.append("typeOfHousing", document.querySelector('input[name="estate"]:checked').value)
    formData.append("ownership", document.querySelector('input[name="ownership"]:checked').value)
    formData.append("status", document.querySelector('input[name="statusOf"]:checked').value)
    formData.append("rooms", window.planirovka.value)
    formData.append("typeOfHouse", window.tip.value)
    formData.append("floor", window.floor.value)
    formData.append("floorsInHouse", window.floorIsHome.value)
    formData.append("totalArea", window.TotalArea.value)
    formData.append("kitchenArea", window.kitchenArea.value)
    formData.append("livingArea", window.LivingArea.value)
    formData.append("loggia", window.loggia.value)
    formData.append("view", window.view.value)
    formData.append("contactEmail", window.inputEmail.value)
    formData.append("linkYouTube", window.postLinkYouTube.value)
    formData.append("communicationType", document.querySelector('input[name="communication"]:checked').value)
    formData.append('description', window.postDescription.value);
    formData.append('price', price === "" ? 0 : price);
    formData.append('contact', window.inputPhone.value);

    if (!checkInputFields(formData)) {
        return false;
    }

    await sendFile(formData, url);
    window.location.href = '/';
}

async function sentBuyEstatePosting(frontName, categoryId) {
    console.log(frontName + " " + categoryId)
    let url = '/api/posting/new/' + frontName + '/' + categoryId;
    console.log(url)
    const formData = new FormData();

    formData.append('title', window.postTitle.value);
    formData.append("rooms", window.planirovka.value)
    formData.append("contactEmail", window.inputEmail.value)
    formData.append("communicationType", document.querySelector('input[name="communication"]:checked').value)
    formData.append('description', window.postDescription.value);
    formData.append('contact', window.inputPhone.value);

    if (!checkInputFields(formData)) {
        return false;
    }

    await sendFile(formData, url)

    window.location.href = '/';
}

async function sentRentAnEstatePosting(frontName, categoryId) {
    let url = '/api/posting/new/' + frontName + '/' + categoryId;
    const formData = new FormData();
    const fileField = fileList;

    for (let i = 0; i < fileField.length; i++) {
        formData.append('photos', fileField[i]);
    }
    let price = window.postPrice.value.replace(/\s/g, '');;
    formData.append('title', window.postTitle.value);
    formData.append("typeOfHousing", document.querySelector('input[name="estate"]:checked').value)
    formData.append("ownership", document.querySelector('input[name="ownership"]:checked').value)
    formData.append("status", document.querySelector('input[name="statusOf"]:checked').value)
    formData.append("rooms", window.planirovka.value)
    formData.append("typeOfHouse", window.tip.value)
    formData.append("floor", window.floor.value)
    formData.append("floorsInHouse", window.floorIsHome.value)
    formData.append("totalArea", window.TotalArea.value)
    formData.append("kitchenArea", window.kitchenArea.value)
    formData.append("livingArea", window.LivingArea.value)
    formData.append("loggia", window.loggia.value)
    formData.append("view", window.view.value)
    formData.append("contactEmail", window.inputEmail.value)
    formData.append("linkYouTube", window.postLinkYouTube.value)
    formData.append("communicationType", document.querySelector('input[name="communication"]:checked').value)
    formData.append('description', window.postDescription.value);
    formData.append('price', price === "" ? 0 : price);
    formData.append('contact', window.inputPhone.value);
    formData.append('numberOfBeds', window.numberOfBeds.value);
    formData.append('sleeper', window.sleeper.value);
    formData.append('wifi', window.wifi.value);
    formData.append('wifi', document.getElementById("wifi").checked);
    formData.append('tv', document.getElementById("tv").checked);
    formData.append('cable', document.getElementById("cable").checked);
    formData.append('cooker', document.getElementById("cooker").checked);
    formData.append('microwave', document.getElementById("microwave").checked);
    formData.append('fridge', document.getElementById("fridge").checked);
    formData.append('washingMachine', document.getElementById("washingMachine").checked);
    formData.append('hairdryer', document.getElementById("hairdryer").checked);
    formData.append('flatiron', document.getElementById("flatiron").checked);
    formData.append('nurslings', document.getElementById("nurslings").checked);
    formData.append('children', document.getElementById("children").checked);
    formData.append('events', document.getElementById("events").checked);
    formData.append('flatiron', document.getElementById("flatiron").checked);
    formData.append('smoke', document.getElementById("smoke").checked);

    if (!checkInputFields(formData)) {
        return false;
    }

    await sendFile(formData, url);
    window.location.href = '/';
}

async function sentGetAnEstatePosting(frontName, categoryId) {
    let url = '/api/posting/new/' + frontName + '/' + categoryId;
    const formData = new FormData();
    const fileField = fileList;

    for (let i = 0; i < fileField.length; i++) {
        formData.append('photos', fileField[i]);
    }
    let price = window.postPrice.value.replace(/\s/g, '');;
    formData.append('title', window.postTitle.value);
    formData.append("rooms", window.planirovka.value)
    formData.append("contactEmail", window.inputEmail.value)
    formData.append("communicationType", document.querySelector('input[name="communication"]:checked').value)
    formData.append('description', window.postDescription.value);
    formData.append('price', price === "" ? 0 : price);
    formData.append('contact', window.inputPhone.value);
    formData.append('numberOfBeds', window.numberOfBeds.value);
    formData.append('sleeper', window.sleeper.value);
    formData.append('wifi', window.wifi.value);
    formData.append('wifi', document.getElementById("wifi").checked);
    formData.append('tv', document.getElementById("tv").checked);
    formData.append('cable', document.getElementById("cable").checked);
    formData.append('cooker', document.getElementById("cooker").checked);
    formData.append('microwave', document.getElementById("microwave").checked);
    formData.append('fridge', document.getElementById("fridge").checked);
    formData.append('washingMachine', document.getElementById("washingMachine").checked);
    formData.append('hairdryer', document.getElementById("hairdryer").checked);
    formData.append('flatiron', document.getElementById("flatiron").checked);
    formData.append('nurslings', document.getElementById("nurslings").checked);
    formData.append('children', document.getElementById("children").checked);
    formData.append('events', document.getElementById("events").checked);
    formData.append('flatiron', document.getElementById("flatiron").checked);
    formData.append('smoke', document.getElementById("smoke").checked);

    if (!checkInputFields(formData)) {
        return false;
    }

    await sendFile(formData, url);
    window.location.href = '/';
}

async function sendFile(body, url) {
    console.log(body)
    console.log(body)
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


async function getEstate(frontName, id) {
    console.log(frontName)
    let saveButton = document.getElementById('saveButton');
    let postingForm2 = document.getElementById('visibleElement2');

    if (frontName === "sellEstate") {

        postingForm2.innerHTML = '<div id="parameters" class="main-container">\n' +
            '        <div class="category-head-container">\n' +
            '            <div class="category-head-text">Параметры</div>\n' +
            '        </div>\n' +
            '        <div>\n' +
            '            <form id="parametersForm">\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postTitle" class="col-sm-2 col-form-label">Название объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <input  id="postTitle" name="postTitle" title="Введите название объявления" maxlength="100" type="text" class="form-control form-control-sm">\n' +
            '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Квартира однушка, Двухкомнатная квартира...»</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postState" class="col-sm-2 col-form-label">Вид жилья</label>\n' +
            '                    <div class="col-sm-10">\n' +
            '                        <div id="postState" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
            '                            <label class="btn btn-sm btn-outline-secondary">\n' +
            '                                <input type="radio" name="estate" id="noNewEstate" value="Вторичка">Вторичка' +
            '                            </label>\n' +
            '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
            '                                <input type="radio" name="estate" id="newEstate" value="Новостройка" checked>Новостройка' +
            '                            </label>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postState2" class="col-sm-2 col-form-label">Право собственности</label>\n' +
            '                    <div class="col-sm-10">\n' +
            '                        <div id="postState2" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
            '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
            '                                <input type="radio" name="ownership" id="owner" value="Собственник" checked>Собственник' +
            '                            </label>\n' +
            '                            <label class="btn btn-sm btn-outline-secondary">\n' +
            '                                <input type="radio" name="ownership" id="intermediary" value="Посредник">Посредник' +
            '                            </label>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postState3" class="col-sm-2 col-form-label">Статус</label>\n' +
            '                    <div class="col-sm-10">\n' +
            '                        <div id="postState3" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
            '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
            '                                <input type="radio" name="statusOf" id="statusOfApartment" value="Квартира" checked>Квартира' +
            '                            </label>\n' +
            '                            <label class="btn btn-sm btn-outline-secondary">\n' +
            '                                <input type="radio" name="statusOf" id="statusOfState" value="Апартаменты">Апартаменты' +
            '                            </label>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +


            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Количество комнат</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="planirovka" name="planirovka" title="Укажите количество комнат" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Студия">Студия</option>\n' +
            '                            <option value="Своб. планировка">Своб. планировка</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="9">9</option>\n' +
            '                            <option value=">9">>9</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postType2" class="col-sm-2 col-form-label">Тип дома</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="tip" name="tip" title="Укажите тип дома" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Кирпичный">Кирпичный</option>\n' +
            '                            <option value="Панельный">Панельный</option>\n' +
            '                            <option value="Блочный">Блочный</option>\n' +
            '                            <option value="Монолитный">Монолитный</option>\n' +
            '                            <option value="Деревянный">Деревянный</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +


            '                <div class="form-group row">\n' +
            '                    <label for="floor" class="col-sm-2 col-form-label">Этаж</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="floor" name="floor" title="Укажите этаж" rows="1" maxlength="4" style="height: 40px; width: 70px" class="form-control" required></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="floorIsHome" class="col-sm-2 col-form-label">Этажей в доме</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="floorIsHome" name="floorIsHome" title="Укажите количество этажей в доме" rows="1" maxlength="4" style="height: 40px; width: 70px" class="form-control" required></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="TotalArea" class="col-sm-2 col-form-label">Общая площадь(м²)</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="TotalArea" name="TotalArea" title="Укажите общую площадь" rows="1" maxlength="6" style="height: 40px; width: 70px" class="form-control" required></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="kitchenArea" class="col-sm-2 col-form-label">Площадь кухни(м²)</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="kitchenArea" name="kitchenArea" rows="1" maxlength="6" style="height: 40px; width: 70px" class="form-control"></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="LivingArea" class="col-sm-2 col-form-label">Жилая площадь(м²)</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="LivingArea" name="LivingArea" rows="1" maxlength="6" style="height: 40px; width: 70px" class="form-control"></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="loggiaF" class="col-sm-2 col-form-label">Балкон или лоджия</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="loggia" name="loggia" title="Укажите, балкон или лоджия" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Балкон">Балкон</option>\n' +
            '                            <option value="Лоджия">Лоджия</option>\n' +
            '                            <option value="Нет">Нет</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="viewF" class="col-sm-2 col-form-label">Вид из окна</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="view" name="view" title="Укажите вид из окна" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="На улицу">На улицу</option>\n' +
            '                            <option value="Во двор">Во двор</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postDescription" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="postDescription" name="postDescription" title="Укажите описание объявления" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
            '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postPrice" class="col-sm-2 col-form-label">Цена</label>\n' +
            '                    <div class="col-sm-2 d-flex">\n' +
            '                        <input id="postPrice" placeholder="₽" type="text" class="form-control form-control-sm" value="">\n' +
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

        saveButton.onclick = () => sentSellEstatePosting(frontName, id);


    } else if (frontName === "buyEstate") {

        postingForm2.innerHTML = '<div id="parameters" class="main-container">\n' +
            '        <div class="category-head-container">\n' +
            '            <div class="category-head-text">Параметры</div>\n' +
            '        </div>\n' +
            '        <div>\n' +
            '            <form id="parametersForm">\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postTitle" class="col-sm-2 col-form-label">Название объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <input id="postTitle" name="postTitle" title="Укажите название объявления" maxlength="100" type="text" class="form-control form-control-sm">\n' +
            '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Квартира однушка, Двухкомнатная квартира...»</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +


            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Количество комнат</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="planirovka" name="planirovka" title="Укажите количество комнат" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Студия">Студия</option>\n' +
            '                            <option value="Своб. планировка">Своб. планировка</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="9">9</option>\n' +
            '                            <option value=">9">>9</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postDescription" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="postDescription" name="postDescription" title="Укажите описание объявления" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
            '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '            </form>\n' +
            '        </div>\n' +
            '    </div>'

        choosenFiles = document.getElementById('postPhotos');
        choosenFiles.addEventListener('change', checkFiles, false);

        saveButton.onclick = () => sentBuyEstatePosting(frontName, id);

    } else if (frontName === "rentAnEstate") {

        postingForm2.innerHTML =
            '<div id="parameters" class="main-container">\n' +
            '        <div class="category-head-container">\n' +
            '            <div class="category-head-text">Параметры</div>\n' +
            '        </div>\n' +
            '        <div>\n' +
            '            <form id="parametersForm">\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postTitle" class="col-sm-2 col-form-label">Название объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <input id="postTitle" name="postTitle" title="Укажите название объявления" maxlength="100" type="text" class="form-control form-control-sm">\n' +
            '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Квартира однушка, Двухкомнатная квартира...»</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postState" class="col-sm-2 col-form-label">Вид жилья</label>\n' +
            '                    <div class="col-sm-10">\n' +
            '                        <div id="postState" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
            '                            <label class="btn btn-sm btn-outline-secondary">\n' +
            '                                <input type="radio" name="estate" id="noNewEstate" value="Вторичка">Вторичка' +
            '                            </label>\n' +
            '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
            '                                <input type="radio" name="estate" id="newEstate" value="Новостройка" checked>Новостройка' +
            '                            </label>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postState2" class="col-sm-2 col-form-label">Право собственности</label>\n' +
            '                    <div class="col-sm-10">\n' +
            '                        <div id="postState2" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
            '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
            '                                <input type="radio" name="ownership" id="owner" value="Собственник" checked>Собственник' +
            '                            </label>\n' +
            '                            <label class="btn btn-sm btn-outline-secondary">\n' +
            '                                <input type="radio" name="ownership" id="intermediary" value="Посредник">Посредник' +
            '                            </label>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postState3" class="col-sm-2 col-form-label">Статус</label>\n' +
            '                    <div class="col-sm-10">\n' +
            '                        <div id="postState3" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
            '                            <label class="btn btn-sm btn-outline-secondary active">\n' +
            '                                <input type="radio" name="statusOf" id="statusOfApartment" value="Квартира" checked>Квартира' +
            '                            </label>\n' +
            '                            <label class="btn btn-sm btn-outline-secondary">\n' +
            '                                <input type="radio" name="statusOf" id="statusOfState" value="Апартаменты">Апартаменты' +
            '                            </label>\n' +
            '                        </div>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +

            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Количество комнат</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="planirovka" name="planirovka" title="Укажите количество комнат" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Студия">Студия</option>\n' +
            '                            <option value="Своб. планировка">Своб. планировка</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="9">9</option>\n' +
            '                            <option value=">9">>9</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postType2" class="col-sm-2 col-form-label">Тип дома</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="tip" name="tip" title="Укажите тип дома" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Кирпичный">Кирпичный</option>\n' +
            '                            <option value="Панельный">Панельный</option>\n' +
            '                            <option value="Блочный">Блочный</option>\n' +
            '                            <option value="Монолитный">Монолитный</option>\n' +
            '                            <option value="Деревянный">Деревянный</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +

            '                <div class="form-group row">\n' +
            '                    <label for="floor" class="col-sm-2 col-form-label">Этаж</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="floor" name="floor" title="Укажите этаж" rows="1" maxlength="4" style="height: 40px; width: 70px" class="form-control" required></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="floorIsHome" class="col-sm-2 col-form-label">Этажей в доме</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="floorIsHome" name="floorIsHome" title="Укажите количество этажей" rows="1" maxlength="4" style="height: 40px; width: 70px" class="form-control" required></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="TotalArea" class="col-sm-2 col-form-label">Общая площадь(м²)</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="TotalArea" name="TotalArea" title="Укажите общую площадь" rows="1" maxlength="6" style="height: 40px; width: 70px" class="form-control" required></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="kitchenArea" class="col-sm-2 col-form-label">Площадь кухни(м²)</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="kitchenArea" name="kitchenArea" rows="1" maxlength="6" style="height: 40px; width: 70px" class="form-control"></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +  //op
            '                <div class="form-group row">\n' +
            '                    <label for="LivingArea" class="col-sm-2 col-form-label">Жилая площадь(м²)</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="LivingArea" name="LivingArea" rows="1" maxlength="6" style="height: 40px; width: 70px" class="form-control"></textarea>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="loggiaF" class="col-sm-2 col-form-label">Балкон или лоджия</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="loggia" name="loggia" title="Укажите, балкон или лоджия" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Балкон">Балкон</option>\n' +
            '                            <option value="Лоджия">Лоджия</option>\n' +
            '                            <option value="Нет">Нет</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="viewF" class="col-sm-2 col-form-label">Вид из окна</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="view" name="view" title="Укажите вид из окна" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="На улицу">На улицу</option>\n' +
            '                            <option value="Во двор">Во двор</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postDescription" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="postDescription" name="postDescription" title="Укажите описание объявления" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
            '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postPrice" class="col-sm-2 col-form-label">Цена</label>\n' +
            '                    <div class="col-sm-2 d-flex">\n' +
            '                        <input id="postPrice" inputmode="numeric" placeholder="₽" type="text" class="form-control form-control-sm" value="">\n' +
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
            //===============================
            '<div id="dopParameters" class="main-container">\n' +

            '    <div class="category-head-container">\n' +
            '       <div class="category-head-text">Дополнительные Параметры</div>\n' +
            '    </div>\n' +

            '    <div>\n' +
            '       <div class="no-gutters">\n' +
            '          <div>\n' +

            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Количество комнат</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="numberOfBeds" name="numberOfBeds" title="Укажите количество комнат" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="8+">8+</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +

            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Кол-во спальных мест</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="sleeper" name="sleeper" title="Укажите количество спальных мест" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="9">9</option>\n' +
            '                            <option value="10+">10+</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                 </div>\n' +

            '                   <span class="multimedia">Мультимедия</span>' +

            '                   <div>\n' +
            '                       <input type="checkbox" class="wifi" id="wifi">' +
            '                       <label class="" id="wifi">WI-FI</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="tv" id="tv">' +
            '                       <label class="" id="tv">Телевизор</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="cable" id="cable">' +
            '                       <label class="" id="cable">Кабельние</label>' +
            '                   </div>\n' +
            '                </div>\n' +

            '                   <span class="appliances">Бытовая техника</span>' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="cooker" id="cooker">' +
            '                       <label class="" id="cooker">Плита</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="microwave" id="microwave">' +
            '                       <label class="" id="microwave">Микроволновка</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="fridge" id="fridge">' +
            '                       <label class="" id="fridge">Холодильник</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="washingMachine" id="washingMachine">' +
            '                       <label class="" id="washingMachine">Стиральная машинка</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="hairdryer" id="hairdryer">' +
            '                       <label class="" id="hairdryer">Фен</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="flatiron" id="flatiron">' +
            '                       <label class="" id="flatiron">Утюг</label>' +
            '                   </div>\n' +

            '           </div>\n' +
            '        </div>\n' +

            '           <div>\n' +
            '                   <span class="appliances">Бытовая техника</span>' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="fridge" id="fridge">' +
            '                       <label class="" id="fridge">Холодильник</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="washingMachine" id="washingMachine">' +
            '                       <label class="" id="washingMachine">Стиральная машинка</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="hairdryer" id="hairdryer">' +
            '                       <label class="" id="hairdryer">Фен</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="flatiron" id="flatiron">' +
            '                       <label class="" id="flatiron">Утюг</label>' +
            '                   </div>\n' +
            '           </div>\n' +
            '           </div>\n' +

            '           <div>\n' +
            '                   <span class="additionally">Дополнительно</span>' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="nurslings" id="nurslings">' +
            '                       <label class="" id="nurslings">Можно с питомцами</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="children" id="children">' +
            '                       <label class="" id="children">Можно с детьми</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="events" id="events">' +
            '                       <label class="" id="events">Можно для мероприятий</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="smoke" id="smoke">' +
            '                       <label class="" id="smoke">Можно курить</label>' +
            '                   </div>\n' +
            '           </div>\n' +
            '           </div>\n' +
            '            </form>\n' +
            '        </div>\n' +
            '    </div>'
        choosenFiles = document.getElementById('postPhotos');
        choosenFiles.addEventListener('change', checkFiles, false);

        saveButton.onclick = () => sentRentAnEstatePosting(frontName, id);

    } else if (frontName === "getAnEstate") {
        postingForm2.innerHTML =
            '<div id="parameters" class="main-container">\n' +
            '        <div class="category-head-container">\n' +
            '            <div class="category-head-text">О квартире</div>\n' +
            '        </div>\n' +
            '        <div>\n' +
            '            <form id="parametersForm">\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postTitle" class="col-sm-2 col-form-label">Название объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <input  id="postTitle" name="postTitle" title="Укажите название объявления" maxlength="100" type="text" class="form-control form-control-sm">\n' +
            '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Квартира однушка, Двухкомнатная квартира...»</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +

            '\n' +


            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Количество комнат</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="planirovka" name="planirovka" title="Укажите количество комнат" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="Студия">Студия</option>\n' +
            '                            <option value="Своб. планировка">Своб. планировка</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="9">9</option>\n' +
            '                            <option value=">9">>9</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postDescription" class="col-sm-2 col-form-label">Описание объявления</label>\n' +
            '                    <div class="col-sm-6">\n' +
            '                        <textarea id="postDescription" name="postDescription" title="Укажите описание объявления" rows="6" maxlength="5000" style="height: 130px;" class="form-control"></textarea>\n' +
            '                        <p class="text-muted">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +
            '                <div class="form-group row">\n' +
            '                    <label for="postPrice" class="col-sm-2 col-form-label">Цена</label>\n' +
            '                    <div class="col-sm-2 d-flex">\n' +
            '                        <input id="postPrice" inputmode="numeric" placeholder="₽" type="text" class="form-control form-control-sm" value="">\n' +
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

            //===============================
            '<div id="dopParameters" class="main-container">\n' +

            '    <div class="category-head-container">\n' +
            '       <div class="category-head-text">Дополнительные Параметры</div>\n' +
            '    </div>\n' +

            '    <div>\n' +
            '       <div class="no-gutters">\n' +
            '          <div>\n' +

            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Количество комнат</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="numberOfBeds" name="numberOfBeds" title="Укажите количество комнат" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="8+">8+</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                </div>\n' +
            '\n' +

            '                <div class="form-group row">\n' +
            '                    <label for="postType" class="col-sm-2 col-form-label">Кол-во спальных мест</label>\n' +
            '                    <div class="col-sm-3">\n' +
            '                        <select id="sleeper" name="sleeper" title="Укажите количество спальных мест" class="custom-select custom-select-sm" required>\n' +
            '                            <option value="">-</option>\n' +
            '                            <option value="1">1</option>\n' +
            '                            <option value="2">2</option>\n' +
            '                            <option value="3">3</option>\n' +
            '                            <option value="4">4</option>\n' +
            '                            <option value="5">5</option>\n' +
            '                            <option value="6">6</option>\n' +
            '                            <option value="7">7</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="8">8</option>\n' +
            '                            <option value="9">9</option>\n' +
            '                            <option value="10+">10+</option>\n' +
            '                        </select>\n' +
            '                    </div>\n' +
            '                 </div>\n' +

            '                   <span class="multimedia">Мультимедия</span>' +

            '                   <div>\n' +
            '                       <input type="checkbox" class="wifi" id="wifi">' +
            '                       <label class="" id="wifi">WI-FI</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="tv" id="tv">' +
            '                       <label class="" id="tv">Телевизор</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="cable" id="cable">' +
            '                       <label class="" id="cable">Кабельние</label>' +
            '                   </div>\n' +
            '                </div>\n' +
            //=====================
            '                   <span class="appliances">Бытовая техника</span>' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="cooker" id="cooker">' +
            '                       <label class="" id="cooker">Плита</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="microwave" id="microwave">' +
            '                       <label class="" id="microwave">Микроволновка</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="fridge" id="fridge">' +
            '                       <label class="" id="fridge">Холодильник</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="washingMachine" id="washingMachine">' +
            '                       <label class="" id="washingMachine">Стиральная машинка</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="hairdryer" id="hairdryer">' +
            '                       <label class="" id="hairdryer">Фен</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="flatiron" id="flatiron">' +
            '                       <label class="" id="flatiron">Утюг</label>' +
            '                   </div>\n' +

            '           </div>\n' +
            '        </div>\n' +

            //==============
            '           <div>\n' +
            '                   <span class="appliances">Бытовая техника</span>' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="fridge" id="fridge">' +
            '                       <label class="" id="fridge">Холодильник</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="washingMachine" id="washingMachine">' +
            '                       <label class="" id="washingMachine">Стиральная машинка</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="hairdryer" id="hairdryer">' +
            '                       <label class="" id="hairdryer">Фен</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="flatiron" id="flatiron">' +
            '                       <label class="" id="flatiron">Утюг</label>' +
            '                   </div>\n' +
            '           </div>\n' +
            '           </div>\n' +


            '           <div>\n' +
            '                   <span class="additionally">Дополнительно</span>' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="nurslings" id="nurslings">' +
            '                       <label class="" id="nurslings">Можно с питомцами</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="children" id="children">' +
            '                       <label class="" id="children">Можно с детьми</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="events" id="events">' +
            '                       <label class="" id="events">Можно для мероприятий</label>' +
            '                   </div>\n' +
            '                   <div>\n' +
            '                       <input type="checkbox" class="smoke" id="smoke">' +
            '                       <label class="" id="smoke">Можно курить</label>' +
            '                   </div>\n' +
            '           </div>\n' +
            '           </div>\n' +
            '            </form>\n' +
            '        </div>\n' +
            '    </div>'

        choosenFiles = document.getElementById('postPhotos');
        choosenFiles.addEventListener('change', checkFiles, false);

        saveButton.onclick = () => sentGetAnEstatePosting(frontName, id);
    }
}

