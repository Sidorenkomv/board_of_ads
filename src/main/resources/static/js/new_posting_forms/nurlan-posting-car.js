let frontName;
let pco;
let colorsSetGlobal;
let carBrandsGlobal;

async function carPostingFunction(fName) {
    frontName = fName;
    colorsSetGlobal = await getColorsSet();
    carBrandsGlobal = await getCarBrandsSet();
    await getPostingCarMap();
}

const requestURL = '/api/posting/car/';

const httpHeader = {
    fetchData: async function (url) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        return response.json();
    }
}

const postingCarService = {
    getPostingCarMap: async (isCarNew) => {
        return await httpHeader.fetchData(requestURL + isCarNew);
    }
}

async function getPostingCarMap() {
    postingCarService.getPostingCarMap(frontName).then((async pc => {
            console.log(pc);
            await fillNewCarPostingFields(pc.data);
        })
    )
}

async function getColorsSet() {
    let url = requestURL + 'colors';
    return await getResponseData(url).then((responseData) => {
        return responseData.data;
    });
}

async function getCarBrandsSet() {
    let url = requestURL + 'brands';
    return await getResponseData(url).then((responseData) => {
        return responseData.data;
    });
}

function getResponseData(url) {
    return fetch(url, {method: 'GET'})
        .then(response => response.json())
        .then(data => {
            return data;
        })
}

async function fillNewCarPostingFields(pc) {
    pco = pc;

    let mainDiv = document.createElement('div');
    mainDiv.setAttribute('id', 'car-post-fields-main-container');
    document.getElementById('visibleElement2').appendChild(mainDiv);

    changePathCategory();
    changeMeetingPlace();
    addTypeOfUsedCarPosting()

    let div1 = makeMainAndTitleFields('1', "Внешний вид");
    mainDiv.appendChild(div1);
    let div2 = makeMainAndTitleFields('2', "Регистрационные данные");
    mainDiv.appendChild(div2);
    let div3 = makeMainAndTitleFields('3', "Технические характеристики");
    mainDiv.appendChild(div3);
    let div4 = makeMainAndTitleFields('4', "История эксплуатации и состояние");
    mainDiv.appendChild(div4);
    let div5 = makeMainAndTitleFields('5', "Дополнительные опции");
    mainDiv.appendChild(div5);
    let div6 = makeMainAndTitleFields('6', "Описание");
    mainDiv.appendChild(div6);
    let div7 = makeMainAndTitleFields('7', "Цена");
    mainDiv.appendChild(div7);

    createDiv1Photo(div1);
    createDiv1Colors(div1);
    createDiv1YouTube(div1);
    createDiv2VinFields(div2);
    createDiv2PlateFields(div2);
    createDiv3CarBrandFields(div3);
    createDiv4Mileage(div4);
    createDiv4Conditions(div4);
    createDiv4CountOfOwners(div4);
    createDiv4ServicingInfo(div4);
    createDiv5AllInOne(div5);
    createDiv6DescriptionField(div6);
    createDiv7PriceFields(div7);
}

function createDiv1Photo(div1) {
    let div = document.createElement('div');
    div.setAttribute('id', 'photoDiv');
    div.setAttribute('class', 'row');
    div.setAttribute('data-marker', 'images');

    let div1_1 = document.createElement('div');
    div1_1.setAttribute('class', 'col-3');
    div.appendChild(div1_1);

    let label1 = document.createElement('label');
    label1.setAttribute('class', 'fieldset-label');
    label1.setAttribute('data-marker', 'label');
    let span1 = document.createElement('span');
    span1.setAttribute('class', 'description-label');
    span1.innerText = "Фотографии";
    label1.appendChild(span1);
    let div1_1_l_1 = document.createElement('div');
    div1_1_l_1.setAttribute('class', 'fieldset-label-icon');
    div1_1_l_1.setAttribute('data-marker', 'icon');
    div1_1_l_1.innerText = "Не более 40;)";
    label1.appendChild(div1_1_l_1);
    div1_1.appendChild(label1);

    let div1_2 = document.createElement('div');
    div1_2.setAttribute('class', 'col-5');
    div.appendChild(div1_2);
    let div1_2_1 = document.createElement('div');
    div1_2_1.setAttribute('class', 'fieldset-field-21qUq');
    div1_2_1.setAttribute('data-marker', 'field');
    div1_2.appendChild(div1_2_1);

    let div1_2_1_1 = document.createElement('div');
    div1_2_1_1.setAttribute('class', 'uploader-root-2eaHO uploader-root_design-photo-7MGKB uploader-size-s-2Ecqw');
    div1_2_1.appendChild(div1_2_1_1);

    let div1_2_1_1_last = document.createElement('div');
    div1_2_1_1_last.setAttribute('class', 'uploader-list');
    div1_2_1_1.appendChild(div1_2_1_1_last);

    let label2 = document.createElement('label');
    label2.setAttribute('class', 'uploader-item uploader-item_add-_sMoP button-button-2JBTe button-size-s-2RLvz button-default-szBWv');
    label2.setAttribute('data-marker', 'add');
    div1_2_1_1_last.appendChild(label2);
    let input = document.createElement('input');
    input.setAttribute('class', 'uploader-input-file');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/gif,image/png,image/jpeg');
    input.setAttribute('multiple', '');
    input.setAttribute('data-marker', 'add/input');
    input.setAttribute('value', '');
    label2.appendChild(input);
    div1.appendChild(div);
}

function createDiv1Colors(div1) {
    let inCol9Div = document.createElement('div');
    let label = document.createElement('label');
    label.setAttribute('for', 'car-color');
    let row = makeRowCol3Col9('colors-div', 'Цвет', inCol9Div);
    let select = document.createElement('select');
    select.setAttribute('id', 'car-color');
    select.setAttribute('name', 'car-color');
    select.value = pco.carColor;
    makeSelectOptionsElement(select, colorsSetGlobal);

    inCol9Div.appendChild(select);
    inCol9Div.appendChild(label);
    div1.appendChild(row);
}

function createDiv1YouTube(div1) {
    let input = document.createElement('input');
    input.setAttribute('class', 'input-input');
    input.setAttribute('id', 'videoUrl');
    input.setAttribute('name', 'videoUrl');
    input.setAttribute('placeholder', 'Например: https://www.youtube.com/watch?v=qPeVoi6OmRc');
    input.setAttribute('autocomplete', 'disabled');
    input.setAttribute('type', 'text');
    let row = makeRowCol3Col9('you-tube-div', 'Видео c YouTube', input);
    div1.appendChild(row);
}

function createDiv2VinFields(div2) {
    let div = document.createElement('div');
    let span = document.createElement('span');
    span.setAttribute('class', 'info-text-small');
    span.innerText =
        "Технические характеристики автоматически определятся по VIN — мы покажем в объявлении лишь часть цифр. Госномер пользователи не увидят.";
    div.appendChild(span);

    let label = document.createElement('label');
    let row = makeRowCol3Col9('vin-row', 'VIN или номер кузова', label);
    let input = document.createElement('input');
    input.setAttribute('id', 'vinCode');
    input.setAttribute('name', 'vinCode');
    input.setAttribute('type', 'text');
    label.appendChild(input);
    div.appendChild(row);
    div2.appendChild(div);
}

function createDiv2PlateFields(div2) {
    let inCol9Div = document.createElement('div');
    inCol9Div.setAttribute('class', 'input-auto-number');

    let div = makeRowCol3Col9('state-plate-number', 'Государственный номер', inCol9Div);

    let label2 = document.createElement('label');
    label2.setAttribute('class', 'input-auto-number');
    inCol9Div.appendChild(label2);
    let input2 = document.createElement('input');
    input2.setAttribute('class', 'input-auto-number')
    input2.setAttribute('data-marker', 'statePlateNumber');
    input2.setAttribute('id', 'statePlateNumber');
    input2.setAttribute('placeholder', 'о 000 оо');
    label2.appendChild(input2);

    let label3 = document.createElement('label');
    label3.setAttribute('class', 'input-auto-number');
    inCol9Div.appendChild(label3);
    let innerDiv = document.createElement('div');
    innerDiv.innerText = 'RUS';
    label3.appendChild(innerDiv);

    div2.appendChild(div);
}

function makeRowCol3Col9(id, text, innerDiv) {
    let div = document.createElement('div');
    div.setAttribute('id', id);
    let row = document.createElement(id);
    row.setAttribute('class', 'row');
    let col3 = document.createElement('div');
    col3.setAttribute('class', 'col-3');
    col3.appendChild(makeDescriptionText(text));
    row.appendChild(col3);
    let col9 = document.createElement('div');
    col9.setAttribute('class', 'col-9');
    col9.appendChild(innerDiv);
    row.appendChild(col9);
    div.appendChild(row);
    return div;
}

function createDiv3CarBrandFields(div3) {
    let select = document.createElement('select');
    select.setAttribute('id', 'car-brand');
    select.setAttribute('name', 'car-brand');
    select.onchange = (a) => getAutoModel(a.target, div3);
    let div = makeRowCol3Col9('brands-set', 'Марка', select);
    makeSelectOptionsElement(select, carBrandsGlobal);
// Add model etc....
    div3.appendChild(div);
}

async function getAutoModel(target, div3) {
    let brand = target.options[target.selectedIndex].value;
    console.log('after ' + brand);

    async function getCarModelsSet() {
        let url = requestURL + 'models/' + brand;
        return await getResponseData(url).then((responseData) => {
            return responseData.data;
        });
    }

    let models = await getCarModelsSet();
    let oldDiv = $('#models-set');
    if (oldDiv.length > 0) {
        oldDiv.remove();
    }
    let select = document.createElement('select');
    select.setAttribute('id', 'carModel');
    select.setAttribute('name', 'carModel');
    select.onchange = (a) => getAutoYear(a.target, div3);
    let divM = makeRowCol3Col9('models-set', 'Модель', select);
    makeSelectOptionsElement(select, models);
    div3.appendChild(divM);
}

function makeSelectOptionsElement(select, options) {
    for (let i = 0; i < options.length; i++) {
        let optionDiv = document.createElement("option");
        optionDiv.setAttribute('value', options[i]);
        optionDiv.text = options[i];
        optionDiv.value = options[i];
        select.add(optionDiv);
    }
}

async function getAutoYear(target, div3) {
}

function createDiv4Mileage(div4) {
    let label = document.createElement('label');
    label.setAttribute('class', 'input');
    let input = document.createElement('input');
    input.setAttribute('id', 'mileageInput');
    input.setAttribute('name', 'mileageInput');
    input.setAttribute('class', 'input');
    input.setAttribute('type', 'text');
    input.setAttribute('value', '');
    label.appendChild(input);
    let span = document.createElement('span');
    span.innerText = 'км';
    label.appendChild(span);
    let div = makeRowCol3Col9('mileage-row', 'Пробег', label);
    div4.appendChild(div);
}

function createDiv4Conditions(div4) {
    let divInCol9 = document.createElement('fieldset');
    divInCol9.setAttribute('class', 'btn-group mr-2');
    divInCol9.setAttribute('role', 'group');
    divInCol9.setAttribute('aria-label', 'Second group');
    divInCol9.setAttribute('id', 'conditionValue');

    let buttonLeft = document.createElement('button');
    buttonLeft.setAttribute('class', 'btn btn-outline-secondary');
    buttonLeft.setAttribute('type', 'radio');
    buttonLeft.setAttribute('value', 'Битый');
    buttonLeft.innerText = 'Битый';
    divInCol9.appendChild(buttonLeft);
    let buttonRight = document.createElement('button');
    buttonRight.setAttribute('class', 'btn btn-outline-secondary');
    buttonRight.setAttribute('type', 'radio');
    buttonRight.setAttribute('value', 'Не битый');
    buttonRight.innerText = 'Не битый';
    divInCol9.appendChild(buttonRight);
    let div = makeRowCol3Col9('conditions-row', 'Состояние', divInCol9);
    div4.appendChild(div);
}

function createDiv4CountOfOwners(div4) {
    let divInCol9 = document.createElement('div');
    divInCol9.setAttribute('class', 'btn-group mr-2');
    divInCol9.setAttribute('role', 'group');
    divInCol9.setAttribute('aria-label', 'First group');
    divInCol9.setAttribute('id', 'ownerCount');
    divInCol9.setAttribute('name', 'ownerCount');

    divInCol9.appendChild(makeButtonForOwnersCount('1'));
    divInCol9.appendChild(makeButtonForOwnersCount('2'));
    divInCol9.appendChild(makeButtonForOwnersCount('3'));
    divInCol9.appendChild(makeButtonForOwnersCount('4+'));
    let div = makeRowCol3Col9('owners-count-row', 'Владельцев по ПТС', divInCol9);
    div4.appendChild(div);
}

function makeButtonForOwnersCount(val) {
    let button = document.createElement('button');
    button.setAttribute('class', 'btn btn-outline-secondary');
    button.setAttribute('type', 'button');
    button.setAttribute('value', val);
    button.innerText = val;
    return button;
}

function createDiv4ServicingInfo(div4) {
    let cbDiv = document.createElement('div');
    cbDiv.appendChild(makeCheckbox('hasServiceBook', 'Есть Сервисная книжка'));
    cbDiv.appendChild(makeCheckbox('dealerServiced', 'Обслуживался у дилера'));
    cbDiv.appendChild(makeCheckbox('underWarranty', 'На гаранти'));
    let div = makeRowCol3Col9('serviced-info-row', 'Данные о ТО', cbDiv);
    div4.appendChild(div);
}

function createDiv5AllInOne(div5) {
    let div = document.createElement('div');
    div.setAttribute('id', 'car-options');
    div.setAttribute('class', 'container no-gutters');

    let div2 = document.createElement('div');
    div2.setAttribute('class', 'row no-gutters');
    div.appendChild(div2);
    let column_left = document.createElement('div'); // ====== left column
    column_left.setAttribute('class', 'col-4 ');
    div2.appendChild(column_left);
    let option = ["Гидро-",
        "Электро-",
        "Электрогидро-"];
    column_left.appendChild(makeOptionsVsTitle('climateControlType', 'Усилитель руля', option));
    option = ["Кондиционер",
        "Климат-контроль однозонный", "Климат-контроль двузонный",
        "Климат-контроль трехзонный"];
    column_left.appendChild(makeOptionsVsTitle('param1', 'Управление климатом', option));
    column_left.appendChild(makeCheckbox('param2', 'Управление на руле'));
    column_left.appendChild(makeCheckbox('param3', 'Атермальное остекление'));
    option = ["Кожа",
        "Ткань",
        "Велюр",
        "Комбинированный"];
    column_left.appendChild(makeOptionsVsTitle('param4', 'Салон', option));
    column_left.appendChild(makeCheckbox('param5', 'Кожаный руль'));
    column_left.appendChild(makeCheckbox('param6', 'Люк'));

    column_left.appendChild(makeDescriptionText('Обогрев'));
    column_left.appendChild(makeCheckbox('param7', 'Передних сидений'));
    column_left.appendChild(makeCheckbox('param8', 'Задних сидений'));
    column_left.appendChild(makeCheckbox('param15', 'Зеркал'));
    column_left.appendChild(makeCheckbox('param16', 'Заднего стекла'));
    column_left.appendChild(makeCheckbox('param17', 'Руля'));

    option = ["Только передние",
        "Передние и задние"];
    column_left.appendChild(makeOptionsVsTitle('param23', 'Электростеклоподъемники', option));

    column_left.appendChild(makeDescriptionText('Электропривод'));
    column_left.appendChild(makeCheckbox('param22', 'Передних сидений'));
    column_left.appendChild(makeCheckbox('param18', 'Задних сидений'));
    column_left.appendChild(makeCheckbox('param19', 'Зеркал'));
    column_left.appendChild(makeCheckbox('param20', 'Рулевой колонки'));
    column_left.appendChild(makeCheckbox('param21', 'Складывания зеркал'));

    column_left.appendChild(makeDescriptionText('Память настроек'));
    column_left.appendChild(makeCheckbox('param30', 'Передних сидений'));
    column_left.appendChild(makeCheckbox('param31', 'Задних сидений'));
    column_left.appendChild(makeCheckbox('param32', 'Зеркал'));
    column_left.appendChild(makeCheckbox('param33', 'Рулевой колонки'));

    let column_center = document.createElement('div'); // ====== center column
    column_center.setAttribute('class', 'col-4 no-gutters');
    div2.appendChild(column_center);
    column_center.appendChild(makeDescriptionText('Помощь при вождении'));
    column_center.appendChild(makeCheckbox('param40', 'Автоматический парковщик'));
    column_center.appendChild(makeCheckbox('param41', 'Датчик дождя'));
    column_center.appendChild(makeCheckbox('param42', 'Датчик света'));
    column_center.appendChild(makeCheckbox('param43', 'Парктроник задний'));
    column_center.appendChild(makeCheckbox('param44', 'Парктроник передний'));
    column_center.appendChild(makeCheckbox('param45', 'Система контроля слепых зон'));
    column_center.appendChild(makeCheckbox('param46', 'Камера заднего вида'));
    column_center.appendChild(makeCheckbox('param47', 'Круиз-контроль'));
    column_center.appendChild(makeCheckbox('param48', 'Бортовой компьютер'));

    column_center.appendChild(makeDescriptionText('Противоугонная система'));
    column_center.appendChild(makeCheckbox('param50', 'Сигнализация'));
    column_center.appendChild(makeCheckbox('param51', 'Центральный замок'));
    column_center.appendChild(makeCheckbox('param52', 'Иммобилайзер'));
    column_center.appendChild(makeCheckbox('param53', 'Спутник'));

    column_center.appendChild(makeDescriptionText('Подушки безопасности'));
    column_center.appendChild(makeCheckbox('param55', 'Фронтальные'));
    column_center.appendChild(makeCheckbox('param56', 'Коленные'));
    column_center.appendChild(makeCheckbox('param57', 'Шторки'));
    column_center.appendChild(makeCheckbox('param58', 'Боковые передние'));
    column_center.appendChild(makeCheckbox('param59', 'Боковые задние'));

    column_center.appendChild(makeDescriptionText('Активная безопасность'));
    column_center.appendChild(makeCheckbox('param60', 'Антиблокировка тормозов'));
    column_center.appendChild(makeCheckbox('param61', 'Антипробуксовка'));
    column_center.appendChild(makeCheckbox('param62', 'Курсовая устойчивость'));
    column_center.appendChild(makeCheckbox('param63', 'Распред. тормозных усилий'));
    column_center.appendChild(makeCheckbox('param64', 'Экстренное торможение'));
    column_center.appendChild(makeCheckbox('param65', 'Блок. дифференциала'));
    column_center.appendChild(makeCheckbox('param66', 'Обнаружение пешеходов'));

    let column_right = document.createElement('div');  // ====== right column
    column_right.setAttribute('class', 'col-4 no-gutters');
    div2.appendChild(column_right);

    column_right.appendChild(makeDescriptionText('Мультимедиа и навигация'));
    column_right.appendChild(makeCheckbox('param70', 'CD/DVD/Blu-ray'));
    column_right.appendChild(makeCheckbox('param71', 'МР3'));
    column_right.appendChild(makeCheckbox('param72', 'Радио'));
    column_right.appendChild(makeCheckbox('param73', 'ТВ'));
    column_right.appendChild(makeCheckbox('param74', 'Видео'));
    column_right.appendChild(makeCheckbox('param75', 'Управление на руле'));
    column_right.appendChild(makeCheckbox('param76', 'USB'));
    column_right.appendChild(makeCheckbox('param77', 'AUX'));
    column_right.appendChild(makeCheckbox('param78', 'Bluetooth'));
    column_right.appendChild(makeCheckbox('param79', 'GPS-навигатор'));

    option = ["2 колонки",
        "4 колонки",
        "6 колонок",
        "8+ колонок"];
    column_right.appendChild(makeOptionsVsTitle('param80', 'Аудиосистема', option));
    column_right.appendChild(makeCheckbox('param81', 'Сабвуфер'));

    option = ["Галогенные",
        "Ксеноновые",
        "Светодиодные"];
    column_right.appendChild(makeOptionsVsTitle('param83', 'Фары', option));
    column_right.appendChild(makeCheckbox('param84', 'Противотуманные'));
    column_right.appendChild(makeCheckbox('param85', 'Омыватели фар'));
    column_right.appendChild(makeCheckbox('param86', 'Адаптивное освещение'));

    option = ['1\"', '2\"', '3\"', '4\"', '5\"', '6\"', '7\"', '8\"', '9\"',
        '10\"', '11\"', '12\"', '13\"', '14\"', '15\"', '16\"', '17\"', '18\"', '19\"',
        '20\"', '21\"', '22\"', '23\"', '24\"', '25\"', '26\"', '27\"', '28\"', '29\"', '30\"'];
    column_right.appendChild(makeOptionsVsTitle('param87', 'Шины и диски', option));
    column_right.appendChild(makeCheckbox('param88', 'Зимние шины в комплекте'));
    div5.appendChild(div);
}


function createDiv6DescriptionField(div6) {
    let div = document.createElement('div');
    let row = document.createElement('div');
    row.setAttribute('class', 'row');

    let col10 = document.createElement('div');
    col10.setAttribute('class', 'col-10');
    // col10.appendChild(makeDescriptionText('Описание'));
    row.appendChild(col10);

    let divIn = document.createElement('div');
    divIn.setAttribute('class', 'fieldset');
    divIn.setAttribute('data-marker', 'field');
    col10.appendChild(divIn);

    let textArea = document.createElement('textarea');
    textArea.setAttribute('class', 'input-layout');
    textArea.setAttribute('id', 'description');
    textArea.setAttribute('name', 'description');
    textArea.setAttribute('maxlength', '5000');
    textArea.setAttribute('rows', '10');
    textArea.setAttribute('cols', '100');

    divIn.appendChild(textArea);
    div.appendChild(row);
    div6.appendChild(div);
}


function makeOptionsVsTitle(field, text, options) {
    let div = document.createElement('div');
    let div1 = document.createElement('div');
    div1.setAttribute('for', field);
    div1.innerText = text;
    div.appendChild(div1);
    let select = document.createElement('select');
    select.setAttribute('id', field);
    select.setAttribute('name', field);
    for (let i = 0; i < options.length; i++) {
        let optionDiv = document.createElement("option");
        optionDiv.text = options[i];
        select.add(optionDiv);
    }
    div.appendChild(select);
    return div;
}


function makeCheckbox(id, text) {
    let div = document.createElement('div');
    div.setAttribute('class', 'custom-control custom-checkbox');
    let input = document.createElement('input');
    input.setAttribute('type', 'checkbox');
    input.setAttribute('class', 'custom-control-input');
    input.setAttribute('id', id);
    div.appendChild(input);
    let label = document.createElement('label');
    label.setAttribute('class', 'custom-control-label');
    label.setAttribute('for', id);
    label.innerText = text;
    div.appendChild(label);
    return div;
}


function makeDescriptionText(text) {
    let div = document.createElement('span');
    div.setAttribute('class', 'description-label');
    div.innerText = text;
    return div;
}


function createDiv7PriceFields(div7) {
    let row = document.createElement('div');
    row.setAttribute('class', 'row');

    let col4 = document.createElement('div');
    col4.setAttribute('class', 'col-4');
    let col8 = document.createElement('div');
    col8.setAttribute('class', 'col-8');
    row.appendChild(col4);
    row.appendChild(col8);
    let input = document.createElement('input');
    input.setAttribute('id', 'carPrice-');
    input.setAttribute('type', 'text');

    col4.appendChild(input);
    div7.appendChild(row);
}

function makeMainAndTitleFields(id_suffix, title) {
    let div = makeMainContainer(id_suffix);
    div.appendChild(makeTitleFields(id_suffix, title));
    return div;
}

function makeTitleFields(id_suffix, title) {
    let div = document.createElement('div');
    div.setAttribute('id', 'head-title-' + id_suffix);
    div.setAttribute('class', 'category-head-container');
    let cht = document.createElement('div');
    cht.setAttribute('class', 'category-head-text');
    cht.innerText = title;
    div.appendChild(cht);
    return div;
}

function makeMainContainer(id_suffix) {
    let div = document.createElement('div');
    div.setAttribute('id', 'main-container-' + id_suffix);
    div.setAttribute('class', 'main-container');
    return div;
}

function changePathCategory() {
    let a = document.getElementById('thisCategoryText');
    if (frontName === 'used-car') {
        a.innerText = 'Транспорт / Автомобили / С пробегом';
    } else {
        a.innerText = 'Транспорт / Автомобили / Новые';
    }
}

function changeMeetingPlace() {
    document.getElementById('meetingPlace').innerText = 'Место осмотра';
}

function addTypeOfUsedCarPosting() {
    let a = document.getElementById('pathCategory').parentElement.parentElement;
    a.appendChild(addPostingType());


}

function addPostingType() {
    let div = document.createElement('div');
    let row = document.createElement('div');
    row.setAttribute('class', 'row');

    let col3 = document.createElement('div');
    col3.setAttribute('class', 'col-3');
    col3.appendChild(makeDescriptionText('Вид объявления'));
    row.appendChild(col3);

    let col9 = document.createElement('div');
    col9.setAttribute('class', 'col-9');

    let divInCol9 = document.createElement('div');
    col9.appendChild(divInCol9);
    let label = document.createElement('label');
    label.setAttribute('class', 'category-head-text');
    label.setAttribute('for', 'typeOfUsedCarPosting');

    divInCol9.appendChild(label);

    let options = [];
    options[0] = 'Продаю личный автомобиль';
    options[1] = 'Автомобиль приобретен на продажу';

    let select = document.createElement('select');
    select.setAttribute('id', 'typeOfUsedCarPosting');
    select.setAttribute('name', 'typeOfUsedCarPosting');
    select.setAttribute('value', pco.typeOfUsedCarPosting);
    for (let i = 0; i < options.length; i++) {
        let optionDiv = document.createElement("option");
        optionDiv.text = options[i];
        optionDiv.value = options[i];
        select.add(optionDiv);
    }
    divInCol9.appendChild(select);


    row.appendChild(col9);
    div.appendChild(row);
    return div;

}

submitUsedCarForm = function () {
    let formM = document.getElementById("used-car-posting-form");
    formM.submit();
    let posting = formM.attr('object');

    console.log("function is called");
    console.log(posting);

    alert("Saved!");
}


let submitAndSavePostingButton = document.getElementById('saveButton');
submitAndSavePostingButton.onclick = () => saveFunction();


function saveFunction() {
    alert('Save is ok! ))))')
    let e = document.getElementById('car-color');
    let carColor = e.options[e.selectedIndex].value;

    e = document.getElementById('typeOfUsedCarPosting');
    let typeOfUsedCarPosting = e.options[e.selectedIndex].value;

    e = document.getElementById('car-brand');
    let carBrand = e.options[e.selectedIndex].value;

    let posting = {
        typeOfUsedCarPosting: typeOfUsedCarPosting,
        carBrand: carBrand,
        carColor: carColor,
        vinCode: document.getElementById('vinCode').value,
        statePlateNumber: document.getElementById('statePlateNumber').value

    }


    alert('Point 1 / Saving ... ');

    let json_posting = JSON.stringify(posting);
    const requestURL = '/api/posting/car/new-save';

    sendRequest('POST', requestURL, json_posting).then(() => {
        console.log("saved/ may be");
    }).catch(err => console.log(err))

    async function sendRequest(method, url, body) {
        await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        }).then()
    }
}