let postingFormVac = document.getElementById('visibleElement2');
let postingFooterVac = document.getElementById('visibleElement3');
let saveButtonVac = document.getElementById('saveButton');

async function sentVacancyPosting(frontName, selectedCategoryId) {
    let url = '/api/posting/new/' + frontName + '/' + selectedCategoryId;
    const formData = new FormData();
    const fileField = document.querySelector('input[type="file"]');

    for (let i = 0; i < fileField.files.length; i++) {
        formData.append('photos', fileField.files[i]);
    }

    formData.append('title', window.postTitle.value);
    formData.append('description', window.postDescription.value);
    formData.append('price', window.postPrice.value);
    formData.append('schedule', window.postSchedule.value);
    formData.append('frequency', window.postFrequency.value);
    formData.append('experienceValue', window.postExperienceValue.value);
    formData.append('isFor45', window.isFor45.checked);
    formData.append('isFor14', window.isFor14.checked);
    formData.append('isForHandicapped', window.isForHandicapped.checked);
    formData.append('duties', window.postDuties.value);
    formData.append('placeOfWork', window.postPlace.value);
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

async function getVacancyForm(frontName, selectedCategoryId) {
    saveButtonVac.onclick = () => sentVacancyPosting(frontName, selectedCategoryId);

    postingFormVac.innerHTML = '<div id="parameters" class="main-container">\n' +
        '        <div class="category-head-container">\n' +
        '            <div class="category-head-text">Параметры</div>\n' +
        '        </div>\n' +
        '        <div>\n' +
        '            <form id="parametersForm">\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postTitle" class="col-sm-2 col-form-label">Название объявления</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <input  id="postTitle" name="postTitle" maxlength="100" type="text" class="form-control form-control-sm">\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Например, «Продавец-консультант в магазин одежды» или «Водитель такси».</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postSchedule" class="col-sm-2 col-form-label">График работы</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <select style="width: 220px;" id="postSchedule" name="postSchedule" class="form-control form-control-sm">\n' +
        '                           <option value="">—</option>\n' +
        '                           <option value="Вахтовый метод">Вахтовый метод</option>\n' +
        '                           <option value="Неполный день">Неполный день</option>\n' +
        '                           <option value="Полный день">Полный день</option>\n' +
        '                           <option value="Свободный график">Свободный график</option>\n' +
        '                           <option value="Сменный график">Сменный график</option>\n' +
        '                           <option value="Удалённая работа">Удалённая работа</option>\n' +
        '                        </select>\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Треть соискателей ищут подработку: их привлечёт график, который позволит совмещать её с основной работой.</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPrice" class="col-sm-2 col-form-label">Зарплата</label>\n' +
        '                    <div class="col-sm-2">\n' +
        '                        <input  style="width: 120px;" id="postPrice" name="postPrice" maxlength="7" inputmode="numeric" placeholder="₽" type="text" class="form-control form-control-sm">\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postFrequency" class="col-sm-2 col-form-label">Частота выплат</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <select style="width: 220px;" id="postFrequency" name="postFrequency" class="form-control form-control-sm">\n' +
        '                           <option value="">—</option>\n' +
        '                           <option value="почасовая оплата">почасовая оплата</option>\n' +
        '                           <option value="каждый день">каждый день</option>\n' +
        '                           <option value="дважды в месяц">дважды в месяц</option>\n' +
        '                        </select>\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Четверть кандидатов хотят получать оплату каждый день или за час работы.</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postDuties" class="col-sm-2 col-form-label">Обязанности</label>\n' +
        '                    <div class="col-sm-5">\n' +
        '                        <textarea id="postDuties" name="postDuties" rows="5" maxlength="5000" style="height: 130px;" class="form-control" placeholder="Через запятую, например: помощь покупателям, работа с кассой, контроль сроков годности"></textarea>\n' +
        '                        <p class="text-muted">Соискатели увидят это на карточке вакансии в поиске — и смогут решить, откликаться или нет.</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPlace" class="col-sm-2 col-form-label">Где предстоит работать</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <select style="width: 220px;" id="postPlace" name="postPlace" class="form-control form-control-sm">\n' +
        '                           <option value="">—</option>\n' +
        '                           <option value="На дому">На дому</option>\n' +
        '                           <option value="С проживанием">С проживанием</option>\n' +
        '                           <option value="Разъездная работа">Разъездная работа</option>\n' +
        '                           <option value="Автосалон">Автосалон</option>\n' +
        '                           <option value="Агентство">Агентство</option>\n' +
        '                           <option value="Аптека">Аптека</option>\n' +
        '                           <option value="Аэропорт">Аэропорт</option>\n' +
        '                           <option value="Банк">Банк</option>\n' +
        '                           <option value="Вокзал">Вокзал</option>\n' +
        '                           <option value="Гостиница">Гостиница</option>\n' +
        '                           <option value="Детский сад">Детский сад</option>\n' +
        '                           <option value="Кафе или ресторан">Кафе или ресторан</option>\n' +
        '                           <option value="Кинотеатр">Кинотеатр</option>\n' +
        '                           <option value="Магазин">Магазин</option>\n' +
        '                           <option value="Медицинское учреждение">Медицинское учреждение</option>\n' +
        '                           <option value="На производстве">На производстве</option>\n' +
        '                           <option value="Ночной клуб">Ночной клуб</option>\n' +
        '                           <option value="Офис">Офис</option>\n' +
        '                           <option value="Офис продаж">Офис продаж</option>\n' +
        '                           <option value="Салон красоты">Салон красоты</option>\n' +
        '                           <option value="Склад">Склад</option>\n' +
        '                           <option value="Спортклуб">Спортклуб</option>\n' +
        '                           <option value="Студия">Студия</option>\n' +
        '                           <option value="Суд">Суд</option>\n' +
        '                           <option value="Театр">Театр</option>\n' +
        '                           <option value="Университет">Университет</option>\n' +
        '                           <option value="Ферма">Ферма</option>\n' +
        '                           <option value="Школа">Школа</option>\n'+
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postDescription" class="col-sm-2 col-form-label">Описание вакансии и компании</label>\n' +
        '                    <div class="col-sm-5">\n' +
        '                        <textarea id="postDescription" name="postDescription" rows="7" maxlength="6000" style="height: 150px;" class="form-control"></textarea>\n' +
        '                        <p class="text-muted">Расскажите, что вы предлагаете и каких кандидатов ищете. Убедитесь, что в объявлении нет признаков дискриминации.</p>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postPhoto" class="col-sm-2 col-form-label">Логотип или фотография</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                    <label>\n' +
        '                       <input id="postPhoto" type="file" accept="image/gif,image/png,image/jpeg,image/pjpeg" data-marker="add/input" value="">\n' +
        '                    </label>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </form>\n' +
        '        </div>\n' +
        '    </div>';

    document.getElementById('meetingPlace').innerText = 'Место работы';
    document.getElementById('preciseAddress').innerText = 'Точный адрес поможет привлечь заинтересованных cоискателей';

    let r = document.createElement("div");
    r.id = "requirements";
    r.className = "main-container";
    r.innerHTML = '<div class="category-head-container">\n' +
        '            <div id="requirementsToCandidate" class="category-head-text">Требования к кандидату</div>\n' +
        '        </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="postExperienceValue" class="col-sm-2 col-form-label">Опыт работы</label>\n' +
        '                    <div class="col-sm-6">\n' +
        '                        <select style="width: 220px;" id="postExperienceValue" name="postExperienceValue" class="form-control form-control-sm">\n' +
        '                           <option value="">—</option>\n' +
        '                           <option value="Не имеет значения">Не имеет значения</option>\n' +
        '                           <option value="Более 1 года">Более 1 года</option>\n' +
        '                           <option value="Более 3 лет">Более 3 лет</option>\n' +
        '                           <option value="Более 5 лет">Более 5 лет</option>\n' +
        '                           <option value="Более 10 лет">Более 10 лет</option>\n' +
        '                        </select>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <div class="form-group row">\n' +
        '                    <label for="preferences" class="col-sm-2 col-form-label">Подходит соискателям</label>\n' +
        '                    <div id="preferences" class="col-sm-6">\n' +
        '                       <div class="form-check">\n' +
        '                           <input id="isFor45" class="form-check-input" type="checkbox" name="isFor45">\n' +
        '                           <label for="isFor45" class="form-check-label"> Старше 45 лет</label>\n' +
        '                        </div>\n' +
        '                       <div class="form-check">\n' +
        '                           <input id="isFor14" class="form-check-input" type="checkbox" name="isFor14">\n' +
        '                           <label for="isFor14" class="form-check-label"> От 14 лет</label>\n' +
        '                        </div>\n' +
        '                       <div class="form-check">\n' +
        '                           <input id="isForHandicapped" class="form-check-input" type="checkbox" name="isForHandicapped">\n' +
        '                           <label for="isForHandicapped" class="form-check-label"> С нарушениями здоровья</label>\n' +
        '                        </div>\n' +
        '                        <p class="text-muted" data-toggle="tooltip" data-placement="top">Подчеркните, что рассматриваете разных соискателей. Так вы\n' +
        'привлечёте дополнительное внимание кандидатов к вакансии.</p>\n' +
        '                    </div>\n' +
        '                </div>\n';

    postingFooterVac.firstElementChild.after(r);
}