async function sendNewVacancyPosting(selectedCategoryId) {
    let url = "/api/posting/new/vacancy"/* + selectedCategoryId*/;

    const fileField = document.querySelector('input[type="file"][name="image"][multiple]');

   /* for (let i = 0; i < fileField.files.length; i++) {
        formData.append('photos', fileField.files[i]);
    }*/
    let body = new FormData();
    body.set("title", document.getElementById("title").value);
    body.set("schedule", document.getElementById("schedule").value);
    body.set("price", document.getElementById("price").value);
    body.set("payoutFrequency", document.getElementById("payout_frequency").value);
    body.set("location", document.getElementById("location").value);
    body.set("duties", document.getElementById("duties").value);
    body.set("description", document.getElementById("description").value);
    body.set("workExperience", document.getElementById("workExperience").value);
    body.set("olderThan45", "" + document.querySelector('input[name="olderThan45"]:checked').value);
    body.set("olderThan14", "" + document.querySelector('input[name="olderThan14"]:checked').value);
    body.set("categoryId", selectedCategoryId);
    body.set("city", document.getElementById("inputAddress").value);
    await sendFile(body, url);
    window.location.href = '/';
}

async function buildVacancyForm(frontName, selectedCategoryId) {
    saveButton.onclick = () => sendNewVacancyPosting(selectedCategoryId)
    postingForm.innerHTML = '<table>\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <label for="title">Название объявления</label>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <div><input type="text" id="title" name="title" maxlength="50" required></div>\n' +
        '                <div>Например, «Продавец-консультант в магазин одежды» или «Водитель такси».</div>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <label for="schedule">График работы</label>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <select name="schedule" id="schedule" required>\n' +
        '                    <option value="" selected disabled> -</option>\n' +
        '                    <option value="Вахтовый метод">Вахтовый метод</option>\n' +
        '                    <option value="Неполный день">Неполный день</option>\n' +
        '                    <option value="Полный день">Полный день</option>\n' +
        '                    <option value="Свободный график">Свободный график</option>\n' +
        '                    <option value="Сменный график">Сменный график</option>\n' +
        '                    <option value="Удалённая работа">Удалённая работа</option>\n' +
        '                </select>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <label for="price">Зарплата</label></td>\n' +
        '            <td>\n' +
        '                <input type="number" id="price" name="price" min="0">\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <label for="payout_frequency">Частота выплат</label>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <select name="payoutFrequency" id="payout_frequency">\n' +
        '                    <option value="">—</option>\n' +
        '                    <option value="Почасовая оплата">Почасовая оплата</option>\n' +
        '                    <option value="Каждый день">Каждый день</option>\n' +
        '                    <option value="Дважды в месяц">Дважды в месяц</option>\n' +
        '                </select>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <label for="duties">Обязанности</label>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <fieldset>\n' +
        '                    <textarea id="duties" name="duties" rows="5"\n' +
        '                              placeholder="Через запятую, например: помощь покупателям, работа с кассой, контроль сроков годности"\n' +
        '                              style="-webkit-box-direction: normal; width: 100%; font-family: Arial, Helvetica, sans-serif; background-color: #ffffff;\n' +
        '                    color: #000000;\n' +
        '                    border-color: #cccccc;\n' +
        '                    box-sizing: border-box;\n' +
        '                    position: relative;\n' +
        '                    display: flex;\n' +
        '                    -webkit-box-align: center;\n' +
        '                    align-items: center;\n' +
        '                    border-style: solid;\n' +
        '                    outline: 0;\n' +
        '                    flex-shrink: 1;\n' +
        '                    resize: none;\n' +
        '                    margin: 0;\n' +
        '                    font-size: 16px;\n' +
        '                    line-height: 24px;\n' +
        '                    min-height: 40px;\n' +
        '                    border-width: 1px;\n' +
        '                    border-radius: 3px;\n' +
        '                    padding: 6px 10px;\n' +
        '                    text-align: left;"></textarea>\n' +
        '                </fieldset>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td><label class="description-label" for="location" data-marker="label"><span\n' +
        '                    class="text-text-3w4WD text-size-m-puHw5">Где предстоит работать</span></label></td>\n' +
        '            <td><select id="location" name="location" class="select-select-3I6EA">\n' +
        '                <option value="">—</option>\n' +
        '                <option value="На дому">На дому</option>\n' +
        '                <option value="С проживанием">С проживанием</option>\n' +
        '                <option value="Разъездная работа">Разъездная работа</option>\n' +
        '                <option value="Автосалон">Автосалон</option>\n' +
        '                <option value="Агентство">Агентство</option>\n' +
        '                <option value="Аптека">Аптека</option>\n' +
        '                <option value="Аэропорт">Аэропорт</option>\n' +
        '                <option value="Банк">Банк</option>\n' +
        '                <option value="Вокзал">Вокзал</option>\n' +
        '                <option value="Гостиница">Гостиница</option>\n' +
        '                <option value="Детский сад">Детский сад</option>\n' +
        '                <option value="Кафе или ресторан">Кафе или ресторан</option>\n' +
        '                <option value="Кинотеатр">Кинотеатр</option>\n' +
        '                <option value="Магазин">Магазин</option>\n' +
        '                <option value="Медицинское учреждение">Медицинское учреждение</option>\n' +
        '                <option value="На производстве">На производстве</option>\n' +
        '                <option value="Ночной клуб">Ночной клуб</option>\n' +
        '                <option value="Офис">Офис</option>\n' +
        '                <option value="Офис продаж">Офис продаж</option>\n' +
        '                <option value="Салон красоты">Салон красоты</option>\n' +
        '                <option value="Склад">Склад</option>\n' +
        '                <option value="Спортклуб">Спортклуб</option>\n' +
        '                <option value="Студия">Студия</option>\n' +
        '                <option value="Суд">Суд</option>\n' +
        '                <option value="Театр">Театр</option>\n' +
        '                <option value="Университет">Университет</option>\n' +
        '                <option value="Ферма" data-marker="option(457711)">Ферма</option>\n' +
        '                <option value="Школа" data-marker="option(457697)">Школа</option>\n' +
        '            </select></td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td><label\n' +
        '                    class="col-form-label" for="description" data-marker="label"><span\n' +
        '                    class="text-text-3w4WD text-size-m-puHw5">Описание вакансии  и&nbsp;компании</span></label></td>\n' +
        '            <td>\n' +
        '                <div class="fieldset-field-21qUq" data-marker="field">\n' +
        '                    <div class="row-root-2zlel row-root_padding_none-1EZaN">\n' +
        '                        <div class="column-root-8BKVe width-width-flex-12-1vUxn column-has_width-2aIQq"><span\n' +
        '                                class="tooltip-tooltip-box-1HOeY"><span class="tooltip-target-wrapper-hnBGl"><textarea\n' +
        '                                id="description" name="description" rows="6" maxlength="5000"\n' +
        '                                placeholder="" style="-webkit-box-direction: normal; width: 100%; font-family: Arial, Helvetica, sans-serif;\n' +
        '                                background-color: #ffffff; color: #000000; border-color: #cccccc; box-sizing: border-box; position: relative;\n' +
        'display: flex;\n' +
        '-webkit-box-align: center;\n' +
        'align-items: center;\n' +
        'border-style: solid;\n' +
        'outline: 0;\n' +
        'flex-shrink: 1;\n' +
        'resize: none;\n' +
        'margin: 0;\n' +
        'font-size: 16px;\n' +
        'line-height: 24px;\n' +
        'padding-left: 10px;\n' +
        'padding-right: 10px;\n' +
        'border-width: 1px;\n' +
        'border-radius: 3px;\n' +
        'padding-top: 6px;\n' +
        'padding-bottom: 6px;\n' +
        'text-align: left;\n' +
        'min-height: 130px!important;\n' +
        'height: 158px;"></textarea></span></span></div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '                <div class="footer" data-marker="field/hint">Расскажите, что вы предлагаете и\n' +
        '                    каких кандидатов ищете. Убедитесь, что в объявлении нет <a\n' +
        '                            href="https://support.avito.ru/articles/200026908#discrimination" target="_blank"\n' +
        '                            rel="noopener noreferrer"\n' +
        '                            class="link-link-1N2uI link-design-inherited-19dhb link-novisited-NTXVG link-underline-solid-1bH_g">признаков\n' +
        '                        дискриминации</a>.\n' +
        '                </div>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td><label\n' +
        '                    class="fieldset-label-uvOzG" data-marker="label" for="image"><span\n' +
        '                    class="text-text-1PdBw">Логотип или фотография</span></label>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <input type="file" id="image" multiple\n' +
        '                       accept="image/gif,image/png,image/jpeg,image/pjpeg"\n' +
        '                       value="">\n' +
        '                <div class="ui-icon-image">\n' +
        '                    Перетащите сюда изображение\n' +
        '                </div>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td colspan="2"><h3>Требования к кандидату</h3></td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <div class="col-form-label">\n' +
        '                    <label for="workExperience" class="paramLabel">Опыт работы</label>\n' +
        '                </div>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <select id="workExperience" name="workExperience" class="user-select-">\n' +
        '                    <option value="" data-marker="option">—</option>\n' +
        '                    <option value="Не имеет значения">Не имеет значения</option>\n' +
        '                    <option value="Более 1 года">Более 1 года</option>\n' +
        '                    <option value="Более 3 лет">Более 3 лет</option>\n' +
        '                    <option value="Более 5 лет">Более 5 лет</option>\n' +
        '                    <option value="Более 10 лет">Более 10 лет</option>\n' +
        '                </select>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '        <tr>\n' +
        '            <td>\n' +
        '                <div class="text-text-1PdBw">Какие кандидаты приветствуются</div>\n' +
        '            </td>\n' +
        '            <td>\n' +
        '                <div class="column-root-8BKVe width-width-flex-6-2HOj- column-has_width-2aIQq">\n' +
        '                    <div class="fieldset-field-21qUq" data-marker="field">\n' +
        '                        <div class="checkbox-group-root-3prFe checkbox-group-root_design-default-3rhVk checkbox-group-root_layout-vertical-3AQ6_">\n' +
        '                            <div class="checkbox-group-item-1tUN_"><label\n' +
        '                                    class="checkbox-checkbox-1xabX checkbox-size-m-1uQjm"\n' +
        '                                    style="vertical-align: middle;"><input name="olderThan45" id="olderThan45"\n' +
        '                                                                           class="checkbox-input-DkFQ1"\n' +
        '                                                                           type="checkbox"\n' +
        '                                                                           value="Соискатели старше 45 лет"><span\n' +
        '                                    class="description-label">\n' +
        '                                    Соискатели старше 45 лет</span></label>\n' +
        '                            </div>\n' +
        '                            <div class="checkbox-group-item-1tUN_"><label\n' +
        '                                    class="checkbox-checkbox-1xabX checkbox-size-m-1uQjm"\n' +
        '                                    style="vertical-align: middle;"><input name="olderThan14" id="olderThan14"\n' +
        '                                                                           class="checkbox-input-DkFQ1"\n' +
        '                                                                           type="checkbox" value="Соискатели от 14 лет"><span\n' +
        '                                    class="checkbox-label-2yoJX text-text-3w4WD text-size-m-puHw5 text-color-default-1nMIH">\n' +
        '                                    Соискатели от 14 лет</span></label>\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                    <div class="fieldset-field-footer-3gU0a text-text-3w4WD text-size-m-puHw5">\n' +
        '                        <div class="fieldset-field-hint-_U7A4" data-marker="field/hint">Подчеркните, что ищете\n' +
        '                            сотрудников разного возраста, чтобы&nbsp;не&nbsp;упустить опытных специалистов и самых\n' +
        '                            молодых&nbsp;кандидатов. Обычно они особенно заинтересованы&nbsp;в&nbsp;подработке.\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </td>\n' +
        '        </tr>\n' +
        '    </table>';
}