let postingResume = document.getElementById('visibleElement2');
let saveResume = document.getElementById('saveButton');
let numOfElements = '1';
let dropmenuLanguages = '';

function closeButton() {
    let d = document.createElement('div');
    d.setAttribute('class', 'row');
    let b = document.createElement('button');
    b.setAttribute('onclick', 'removeTheForm(this)');
    b.setAttribute('type', 'button');
    b.setAttribute('class', 'close ml-auto');
    b.setAttribute('aria-label', 'Close');
    b.innerHTML = '&times;';
    d.appendChild(b);
    return d;
}

function removeTheForm(item) {
    let c = item.parentElement.parentElement.parentElement.parentElement;
    item.parentElement.parentElement.parentElement.remove();
    if(c.childElementCount === 1) {
        c.firstElementChild.firstElementChild.firstElementChild.remove();
    }
}

function addCloseButton(container) {
    if (numOfElements > 1) {
        if(numOfElements === 2) {
            container.children[0].firstElementChild.prepend(closeButton());
            container.children[1].firstElementChild.prepend(closeButton());
        } else {
            container.children[numOfElements - 1].firstElementChild.prepend(closeButton());
        }
    }
}


async function sentResumePosting(frontName, selectedCategoryId) {
    let url = '/api/posting/new/' + frontName + '/' + selectedCategoryId;

    const formDataResume = new FormData();
    const fileFieldResume = document.querySelector('input[type="file"]');

    for (let i = 0; i < fileFieldResume.files.length; i++) {
        formDataResume.append('photos', fileFieldResume.files[i]);
    }
    formDataResume.append('resume', new Blob([...getJsonObjectResume()], {
        type: "application/json"
    }));

    await sendResumeFile(formDataResume, url);
    window.location.href = '/';
}

async function sendResumeFile(body, url) {

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

function getJsonObjectResume() {
    let dataResume = {};
    dataResume["title"] = $('#title').val();
    dataResume["description"] = $('#description').val();
    dataResume["price"] = $('#price').val();
    dataResume["contact"] = $('#inputPhone').val();
    dataResume["meetingAddress"] = $('#inputAddress').val();
    dataResume["contactEmail"] = $('#inputEmail').val();
    dataResume["communicationType"] = $('#wayOfCommunication :checked').val();
    dataResume["schedule"] = $('#schedule').val();
    dataResume["experienceValue"] = $('#experienceValue').val();
    dataResume["education"] = $('#education').val();
    dataResume["gender"] = $('#gender :checked').val();
    dataResume["age"] = $('#age').val();
    dataResume["readyForBusinessTrip"] = $('#readyForBusinessTrip :checked').val();
    dataResume["relocation"] = $('#relocation :checked').val();
    dataResume["citizenship"] = $('#citizenship').val();

    let experiences = [];
    $('#experienceContainer > div').each( function () {
        let exp = {};
        [...this.querySelectorAll('input, textarea')].map(element => {
            let { name, value } = element;
            exp[name] = value;
        });
        experiences.push(exp);
    });
    dataResume["experiences"] = experiences;

    let graduatedFroms = [];
    $('#studyContainer > div').each( function () {
        let gr = {};
        [...this.getElementsByTagName('input')].map(element => {
            let { name, value } = element;
            gr[name] = value;
        });
        graduatedFroms.push(gr);
    });
    dataResume["graduatedFroms"] = graduatedFroms;

    let levelLanguages = [];
    $('#languagesContainer > div').each( function () {
        let ll = {};
        [...this.querySelectorAll('input, select')].map(element => {
            let { name, value } = element;
            let lll = {};
            lll[name] = value;
            ll[name] = lll;
        });
        levelLanguages.push(ll);
    });
    dataResume["levelLanguages"] = levelLanguages;

    return JSON.stringify(dataResume);
}

async function getCountriesList() {
    let response = await fetch("/api/posting/getcountries", {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    return (await response.json()).data;
}

async function getLanguagesList() {
    let response = await fetch("/api/posting/getlanguages", {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    return (await response.json()).data;
}

async function getResumeForm(frontName, selectedCategoryId) {
    let dropmenuExp = '<datalist id="expValue">\n';
    dropmenuExp += '<option value="Без опыта">\n';
    for (let i = 1; i < 51; i++) {
        dropmenuExp += '<option value="' + i + '">\n';
    }
    dropmenuExp += '</datalist>\n';

    let dropmenuAge = '<datalist id="ageValue">\n';
    for (let i = 14; i < 100; i++) {
        dropmenuAge += '<option value="' + i + '">\n';
    }
    dropmenuAge += '</datalist>\n';

    let countriesList = await getCountriesList();
    let dropmenuCountries = '<datalist id="countriesValue">\n';
    for (let i = 0; i < countriesList.length; i++) {
        dropmenuCountries += '<option value="' + countriesList[i].country + '">\n';
    }
    dropmenuCountries += '</datalist>\n';

    let languagesList = await getLanguagesList();
    dropmenuLanguages = '<datalist id="languageValue">\n';
    for (let i = 0; i < languagesList.length; i++) {
        dropmenuLanguages+= '<option value="' + languagesList[i].language + '">\n';
    }
    dropmenuLanguages += '</datalist>\n';


    saveResume.onclick = () => sentResumePosting(frontName, selectedCategoryId);

    postingResume.innerHTML = '';
    if (postingFooterVac.contains(document.getElementById('experienceAndPreferences'))) {
        document.getElementById('experienceAndPreferences').remove();
    }

    postingResume.innerHTML = '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="title" class="col-sm-2 col-form-label">Желаемая должность</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="title" name="title" maxlength="100" type="text" class="form-control form-control-sm">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Главный бухгалтер» или «Мастер отделочных работ»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="schedule" class="col-sm-2 col-form-label">График работы</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <select style="width: 220px;" id="schedule" name="schedule" class="form-control form-control-sm">\n' +
        '                           <option value="">—</option>\n' +
        '                           <option value="Вахтовый метод">Вахтовый метод</option>\n' +
        '                           <option value="Неполный день">Неполный день</option>\n' +
        '                           <option value="Полный день">Полный день</option>\n' +
        '                           <option value="Свободный график">Свободный график</option>\n' +
        '                           <option value="Сменный график">Сменный график</option>\n' +
        '                           <option value="Удалённая работа">Удалённая работа</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="experienceValue" class="col-sm-2 col-form-label">Стаж работы</label>\n' +
        '                    <div class="col-sm-1">\n' +
        '                       <input list="expValue"  style="width: 110px;" id="experienceValue" name="experienceValue" class="form-control form-control-sm" type="text">\n' +
        dropmenuExp +
        '                    </div>\n' +
        '                    <div class="col-sm-1" style="text-align: center">\n' +
        '                       <span>лет</span>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="education" class="col-sm-2 col-form-label">Образование</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <select style="width: 220px;" id="education" name="education" class="form-control form-control-sm">\n' +
        '                           <option value="">—</option>\n' +
        '                           <option value="Высшее">Высшее</option>\n' +
        '                           <option value="Незаконченное высшее">Незаконченное высшее</option>\n' +
        '                           <option value="Среднее">Среднее</option>\n' +
        '                           <option value="Среднее специальное">Среднее специальное</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="gender" class="col-sm-2 col-form-label">Пол</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <div id="gender" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                           <label style="border: lightgrey solid 1px;" class="btn btn-light">\n' +
        '                               <input type="radio" name="gender" id="gender1" autocomplete="off" value="Мужской"> Мужской\n' +
        '                           </label>\n' +
        '                           <label style="border: lightgrey solid 1px;" class="btn btn-light">\n' +
        '                               <input type="radio" name="gender" id="gender2" autocomplete="off" value="Женский"> Женский\n' +
        '                           </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="age" class="col-sm-2 col-form-label">Возраст</label>\n' +
        '                    <div class="col-sm-1">\n' +
        '                        <input list="ageValue" style="width: 80px;" id="age" name="age" class="form-control form-control-sm" type="text">\n' +
        dropmenuAge +
        '                    </div>\n' +
        '                    <div class="col-sm-1">\n' +
        '                       <span>лет</span>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="businessTrips" class="col-sm-2 col-form-label">Готовность к командировкам</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <div id="readyForBusinessTrip" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                           <label style="border: lightgrey solid 1px;" class="btn btn-light">\n' +
        '                               <input type="radio" name="readyForBusinessTrip" id="businessTrips1" autocomplete="off" value="Не готов"> Не готов\n' +
        '                           </label>\n' +
        '                           <label style="border: lightgrey solid 1px;" class="btn btn-light">\n' +
        '                               <input type="radio" name="readyForBusinessTrip" id="businessTrips2" autocomplete="off" value="Готов"> Готов\n' +
        '                           </label>\n' +
        '                           <label style="border: lightgrey solid 1px;" class="btn btn-light">\n' +
        '                               <input type="radio" name="readyForBusinessTrip" id="businessTrips3" autocomplete="off" value="Иногда"> Иногда\n' +
        '                           </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="relocation" class="col-sm-2 col-form-label">Переезд</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <div id="relocation" class="btn-group btn-group-toggle" data-toggle="buttons">\n' +
        '                           <label style="border: lightgrey solid 1px;" class="btn btn-light">\n' +
        '                               <input type="radio" name="relocation" id="relocation1" autocomplete="off" value="Невозможен"> Невозможен\n' +
        '                           </label>\n' +
        '                           <label style="border: lightgrey solid 1px;" class="btn btn-light">\n' +
        '                               <input type="radio" name="relocation" id="relocation2" autocomplete="off" value="Возможен"> Возможен\n' +
        '                           </label>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="citizenship" class="col-sm-2 col-form-label">Гражданство</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input list="countriesValue" style="width: 220px;" id="citizenship" name="citizenship" class="form-control form-control-sm" type="text">\n' +
        dropmenuCountries +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="description" class="col-sm-2 col-form-label">О себе</label>\n' +
        '                    <div class="col-sm-5">\n' +
        '                        <textarea id="description" name="description" rows="7" placeholder="Чтобы побудить работодателя пригласить именно вас,' +
        'укажите свои деловые качества и профессиональные навыки. Расскажите про ваши достижения. Напишите, чем вы можете быть полезны компании на этой должности" maxlength="6000" style="height: 150px;" class="form-control"></textarea>\n' +
        '                        <p class="text-muted" data-toggle="tooltip">Не указывайте в описании телефон и e-mail — для этого есть отдельные поля</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="price" class="col-sm-2 col-form-label">Зарплата</label>\n' +
        '                    <div class="col-sm-2">\n' +
        '                        <input  style="width: 120px;" id="price" name="price" maxlength="7" inputmode="numeric" placeholder="₽" type="text" class="form-control form-control-sm">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                   <div class="col-sm-2 ">\n' +
        '                       <label for="postPhotos" class="col-form-label">Фотографии</label>\n' +
        '                       <p class="text-muted" style="font-size: 90%;">Не более 5</p>\n' +
        '                   </div>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <label>\n' +
        '                           <input id="postPhotos" type="file" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input" value="">\n' +
        '                            <div id="uploadPhotos"></div>' +
        '                            <p id="errorFor-postPhotos" class="hidden error-text" data-toggle="tooltip" data-placement="top">Загрузите хотя бы одну фотографию</p>\n' +
        '                           <p class="text-muted" data-toggle="tooltip">Пользователи, добавившие фотографию, получают в 3 раза больше просмотров</p>\n' +
        '                        </label>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </form>\n' +
        '        </div>\n' +
        '    </div>';

    document.getElementById('meetingPlace').innerText = 'Место, рядом с которым вы хотите работать';
    document.getElementById('preciseAddress').innerText = 'Укажите точный адрес, например своего дома, чтобы привлечь заинтересованных работодателей';

    let additional = document.createElement("div");
    additional.id = "additional";
    additional.innerHTML =
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Укажите дополнительные параметры и опции</div>\n' +
        '        </div>\n' +
        '\n' +
        '        <div id="experience" style="margin-top: 20px; margin-bottom: 20px;">\n' +
        '           <span>Опыт работы</span>\n' +
        '           <div id="experienceContainer" style="margin: 10px 0px;"></div>\n' +
        '           <span id="addExperienceForm" style="color: #009cf0;">+ Добавить место работы</span>\n' +
        '        </div>\n' +
        '\n' +
        '        <div id="study" style="margin-top: 20px; margin-bottom: 20px;">\n' +
        '           <span>Учебные заведения</span>\n' +
        '           <div id="studyContainer" style="margin: 10px 0px;"></div>\n' +
        '           <span id="addStudyForm" style="color: #009cf0;">+ Добавить учебное заведение</span>\n' +
        '        </div>\n' +
        '\n' +
        '        <div id="languages" style="margin-top: 20px; margin-bottom: 20px;">\n' +
        '           <span>Знание языков</span>\n' +
        '           <div id="languagesContainer" style="margin: 10px 0px;"></div>\n' +
        '           <span id="addLanguageForm" style="color: #009cf0;">+ Добавить язык</span>\n' +
        '        </div>\n';

    document.getElementById('parametersForm').children[8].after(additional);
    document.getElementById('experienceContainer').appendChild(experienceForm(numOfElements));
    document.getElementById('studyContainer').appendChild(studyForm(numOfElements));
    document.getElementById('languagesContainer').appendChild(languageForm(numOfElements));

    $('#addExperienceForm').click(function () {
        let container = document.getElementById('experienceContainer');
        numOfElements = container.childElementCount;
        numOfElements++;
        container.lastElementChild.after(experienceForm(numOfElements));
        addCloseButton(container)
    });

    $('#addStudyForm').click(function () {
        let container = document.getElementById('studyContainer');
        numOfElements = container.childElementCount;
        numOfElements++;
        container.lastElementChild.after(studyForm(numOfElements));
        addCloseButton(container);
    });

    $('#addLanguageForm').click(function () {
        let container = document.getElementById('languagesContainer');
        numOfElements = container.childElementCount;
        numOfElements++;
        container.lastElementChild.after(languageForm(numOfElements));
        addCloseButton(container);
    });

}

function experienceForm(orderNum) {
    let form = document.createElement("div");
    form.setAttribute('style', 'margin-top: 10px;');
    form.id = "expForm" + orderNum;
    form.innerHTML =
        '           <div style="width: 710px; border: 1px solid lightgrey; padding: 10px 25px;">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="companyName' + orderNum + '" class="col-sm-4 col-form-label">Название компании</label>\n' +
        '                    <div class="col-sm-8">\n' +
        '                        <input  id="companyName' + orderNum + '" name="companyName" maxlength="40" type="text" class="form-control form-control-sm">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="position' + orderNum + '" class="col-sm-4 col-form-label">Должность</label>\n' +
        '                    <div class="col-sm-8">\n' +
        '                        <input  id="position' + orderNum + '" name="position" maxlength="40" type="text" class="form-control form-control-sm">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="startWork' + orderNum + '" class="col-sm-4 col-form-label">Начало работы</label>\n' +
        '                    <div class="col-sm-4">\n' +
        '                        <input  id="startWork' + orderNum + '" name="startWork" type="month" class="form-control form-control-sm" min="1960-01" max="2020-12">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="endWork' + orderNum + '" class="col-sm-4 col-form-label">Окончание работы</label>\n' +
        '                    <div class="col-sm-4">\n' +
        '                        <input  id="endWork' + orderNum + '" name="endWork" type="month" class="form-control form-control-sm" min="1960-01" max="2020-12">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="responsibility' + orderNum + '" class="col-sm-4 col-form-label">Обязанности</label>\n' +
        '                    <div class="col-sm-8">\n' +
        '                        <textarea id="responsibility' + orderNum + '" name="responsibility" rows="4" placeholder="Перечислите ваши должностные функции и ключевые навыки, чтобы ' +
        'получить больше приглашений от работодателей" maxlength="6000" style="height: 100px;" class="form-control"></textarea>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '           </div>\n';
    return form;
}

function studyForm(orderNum) {
    let form = document.createElement("div");
    form.setAttribute('style', 'margin-top: 10px;');
    form.id = "stForm" + orderNum;
    form.innerHTML =
        '           <div style="width: 710px; border: 1px solid lightgrey; padding: 10px 25px;">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="eduInstitution' + orderNum + '" class="col-sm-4 col-form-label">Название заведения</label>\n' +
        '                    <div class="col-sm-8">\n' +
        '                        <input  id="eduInstitution' + orderNum + '" name="eduInstitution" maxlength="40" type="text" class="form-control form-control-sm">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Полное название или аббревиатура учебного заведения</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="degreeIn' + orderNum + '" class="col-sm-4 col-form-label">Специальность</label>\n' +
        '                    <div class="col-sm-8">\n' +
        '                        <input  id="degreeIn' + orderNum + '" name="degreeIn" maxlength="40" type="text" class="form-control form-control-sm">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Финансы, денежное обращение и кредит» или «Прикладная математика»</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="endOfStudy' + orderNum + '" class="col-sm-4 col-form-label">Год окончания</label>\n' +
        '                    <div class="col-sm-8">\n' +
        '                        <input  style="width: 150px" id="endOfStudy' + orderNum + '" name="endOfStudy" type="month" class="form-control form-control-sm" min="1960-01" max="2026-12">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Если ещё учитесь — укажите год предполагаемого окончания</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '           </div>\n';
    return form;
}

function languageForm(orderNum) {
    let form = document.createElement("div");
    form.setAttribute('style', 'margin-top: 10px;');
    form.id = "langForm" + orderNum;
    form.innerHTML =
        '           <div style="width: 710px; border: 1px solid lightgrey; padding: 10px 25px;">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="language' + orderNum + '" class="col-sm-4 col-form-label">Язык</label>\n' +
        '                    <div class="col-sm-4">\n' +
        '                        <input  list="languageValue" id="language' + orderNum + '" name="language" type="text" class="form-control form-control-sm">\n' +
        dropmenuLanguages +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="level' + orderNum + '" class="col-sm-4 col-form-label">Уровень владения</label>\n' +
        '                    <div class="col-sm-4">\n' +
        '                        <select id="level' + orderNum + '" name="level" class="form-control form-control-sm">\n' +
        '                           <option value="">—</option>\n' +
        '                           <option value="Начальный">Начальный</option>\n' +
        '                           <option value="Средний">Средний</option>\n' +
        '                           <option value="Выше среднего">Выше среднего</option>\n' +
        '                           <option value="Свободное владение">Свободное владение</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '           </div>\n';
    return form;
}
