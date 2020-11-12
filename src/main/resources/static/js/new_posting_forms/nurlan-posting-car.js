let frontName;
let pco;
let colorsSetGlobal;
let carBrandsGlobal;
let modelChosen;

async function carPostingFunction(fName, id) {
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
    label.setAttribute('for', 'carColor');
    let row = makeRowCol3Col9('colors-div', 'Цвет', inCol9Div);
    let select = document.createElement('select');
    select.setAttribute('id', 'carColor');
    select.setAttribute('name', 'carColor');
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
    select.setAttribute('id', 'carBrand');
    select.setAttribute('name', 'carBrand');
    select.onchange = (a) => getAutoModel(a.target, div3);
    let div = makeRowCol3Col9('brands-set', 'Марка', select);
    makeSelectOptionsElement(select, carBrandsGlobal);
// Add model etc....
    div3.appendChild(div);
}

async function getAutoModel(target, div3) {
    let brand = target.options[target.selectedIndex].value;

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
    select.onchange = (a) => getAutoYear(brand, a.target, div3);
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

async function getAutoYear(brand, target, div3) {
    let model = target.options[target.selectedIndex].value;
    async function getBrandAndModelsYears() {
        let url = requestURL + 'models/' + brand + '/' + model;
        return await getResponseData(url).then((responseData) => {
            return responseData.data;
        });
    }

    let years = await getBrandAndModelsYears();
    let oldDiv = $('#years-set');
    if (oldDiv.length > 0) {
        oldDiv.remove();
    }
    let select = document.createElement('select');
    select.setAttribute('id', 'carYear');
    select.setAttribute('name', 'carYear');
    let divM = makeRowCol3Col9('years-set', 'Год выпуска', select);
    makeSelectOptionsElement(select, years);
    div3.appendChild(divM);
}

function createDiv4Mileage(div4) {
    let label = document.createElement('label');
    label.setAttribute('class', 'input');
    let input = document.createElement('input');
    input.setAttribute('id', 'mileageInput');
    input.setAttribute('name', 'mileageInput');
    input.setAttribute('class', 'input');
    input.setAttribute('type', 'number');
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
    divInCol9.setAttribute('id', 'wasInAccident');

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
    cbDiv.appendChild(makeCheckbox('underWarranty', 'На гарантии'));
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
    column_left.appendChild(makeOptionsVsTitle('powerSteeringType', 'Усилитель руля', option));
    option = ["Кондиционер",
        "Климат-контроль однозонный", "Климат-контроль двузонный",
        "Климат-контроль трехзонный"];
    column_left.appendChild(makeOptionsVsTitle('climateControlType', 'Управление климатом', option));
    column_left.appendChild(makeCheckbox('onWheelControl', 'Управление на руле'));
    column_left.appendChild(makeCheckbox('thermalGlass', 'Атермальное остекление'));
    option = ["Кожа",
        "Ткань",
        "Велюр",
        "Комбинированный"];
    column_left.appendChild(makeOptionsVsTitle('interiorType', 'Салон', option));
    column_left.appendChild(makeCheckbox('leatherWheel', 'Кожаный руль'));
    column_left.appendChild(makeCheckbox('sunroof', 'Люк'));

    column_left.appendChild(makeDescriptionText('Обогрев'));
    column_left.appendChild(makeCheckbox('heatedFrontSeats', 'Передних сидений'));
    column_left.appendChild(makeCheckbox('heatedRearSeats', 'Задних сидений'));
    column_left.appendChild(makeCheckbox('heatedMirrors', 'Зеркал'));
    column_left.appendChild(makeCheckbox('heatedRearWindow', 'Заднего стекла'));
    column_left.appendChild(makeCheckbox('heatedWheel', 'Руля'));

    option = ["Только передние",
        "Передние и задние"];
    column_left.appendChild(makeOptionsVsTitle('powerWindowsType', 'Электростеклоподъемники', option));

    column_left.appendChild(makeDescriptionText('Электропривод'));
    column_left.appendChild(makeCheckbox('powerFrontSeats', 'Передних сидений'));
    column_left.appendChild(makeCheckbox('powerRearSeats', 'Задних сидений'));
    column_left.appendChild(makeCheckbox('powerMirrorRegulation', 'Зеркал'));
    column_left.appendChild(makeCheckbox('powerSteeringWheelRegulation', 'Рулевой колонки'));
    column_left.appendChild(makeCheckbox('powerMirrorClose', 'Складывания зеркал'));

    column_left.appendChild(makeDescriptionText('Память настроек'));
    column_left.appendChild(makeCheckbox('frontSeatsMemory', 'Передних сидений'));
    column_left.appendChild(makeCheckbox('rearSeatsMemory', 'Задних сидений'));
    column_left.appendChild(makeCheckbox('mirrorRegulationMemory', 'Зеркал'));
    column_left.appendChild(makeCheckbox('steeringWheelRegulationMemory', 'Рулевой колонки'));

    let column_center = document.createElement('div'); // ====== center column
    column_center.setAttribute('class', 'col-4 no-gutters');
    div2.appendChild(column_center);
    column_center.appendChild(makeDescriptionText('Помощь при вождении'));
    column_center.appendChild(makeCheckbox('parkingAssist', 'Автоматический парковщик'));
    column_center.appendChild(makeCheckbox('rainSensor', 'Датчик дождя'));
    column_center.appendChild(makeCheckbox('lightSensor', 'Датчик света'));
    column_center.appendChild(makeCheckbox('rearParkingSensor', 'Парктроник задний'));
    column_center.appendChild(makeCheckbox('frontParkingSensor', 'Парктроник передний'));
    column_center.appendChild(makeCheckbox('blindSpotZoneControl', 'Система контроля слепых зон'));
    column_center.appendChild(makeCheckbox('rearCamera', 'Камера заднего вида'));
    column_center.appendChild(makeCheckbox('cruiseControl', 'Круиз-контроль'));
    column_center.appendChild(makeCheckbox('onBoardComp', 'Бортовой компьютер'));

    column_center.appendChild(makeDescriptionText('Противоугонная система'));
    column_center.appendChild(makeCheckbox('alarmSystem', 'Сигнализация'));
    column_center.appendChild(makeCheckbox('powerDoorBlocking', 'Центральный замок'));
    column_center.appendChild(makeCheckbox('immobilizer', 'Иммобилайзер'));
    column_center.appendChild(makeCheckbox('satelliteAlarmControl', 'Спутник'));

    column_center.appendChild(makeDescriptionText('Подушки безопасности'));
    column_center.appendChild(makeCheckbox('frontalAirbags', 'Фронтальные'));
    column_center.appendChild(makeCheckbox('kneeAirbags', 'Коленные'));
    column_center.appendChild(makeCheckbox('sideWindowAirbags', 'Шторки'));
    column_center.appendChild(makeCheckbox('frontSideAirbags', 'Боковые передние'));
    column_center.appendChild(makeCheckbox('rearSideAirbags', 'Боковые задние'));

    column_center.appendChild(makeDescriptionText('Активная безопасность'));
    column_center.appendChild(makeCheckbox('param60', 'Антиблокировка тормозов'));
    column_center.appendChild(makeCheckbox('dtcSystem', 'Антипробуксовка'));
    column_center.appendChild(makeCheckbox('trackingControl', 'Курсовая устойчивость'));
    column_center.appendChild(makeCheckbox('breakAssistSystem', 'Распред. тормозных усилий'));
    column_center.appendChild(makeCheckbox('emergencyBreakSystem', 'Экстренное торможение'));
    column_center.appendChild(makeCheckbox('diffLockSystem', 'Блок. дифференциала'));
    column_center.appendChild(makeCheckbox('pedestrianDetectSystem', 'Обнаружение пешеходов'));

    let column_right = document.createElement('div');  // ====== right column
    column_right.setAttribute('class', 'col-4 no-gutters');
    div2.appendChild(column_right);

    column_right.appendChild(makeDescriptionText('Мультимедиа и навигация'));
    column_right.appendChild(makeCheckbox('cdDvdBluRay', 'CD/DVD/Blu-ray'));
    column_right.appendChild(makeCheckbox('mp3', 'МР3'));
    column_right.appendChild(makeCheckbox('radio', 'Радио'));
    column_right.appendChild(makeCheckbox('tvSystem', 'ТВ'));
    column_right.appendChild(makeCheckbox('videoSystem', 'Видео'));
    column_right.appendChild(makeCheckbox('mediaOnWheelControl', 'Управление на руле'));
    column_right.appendChild(makeCheckbox('usb', 'USB'));
    column_right.appendChild(makeCheckbox('aux', 'AUX'));
    column_right.appendChild(makeCheckbox('bluetooth', 'Bluetooth'));
    column_right.appendChild(makeCheckbox('gpsNavigation', 'GPS-навигатор'));

    option = ["2 колонки",
        "4 колонки",
        "6 колонок",
        "8+ колонок"];
    column_right.appendChild(makeOptionsVsTitle('audioSystemType', 'Аудиосистема', option));
    column_right.appendChild(makeCheckbox('subwoofer', 'Сабвуфер'));

    option = ["Галогенные",
        "Ксеноновые",
        "Светодиодные"];
    column_right.appendChild(makeOptionsVsTitle('frontLightType', 'Фары', option));
    column_right.appendChild(makeCheckbox('antifogLights', 'Противотуманные'));
    column_right.appendChild(makeCheckbox('frontLightCleaning', 'Омыватели фар'));
    column_right.appendChild(makeCheckbox('adaptiveLights', 'Адаптивное освещение'));

    option = ['1\"', '2\"', '3\"', '4\"', '5\"', '6\"', '7\"', '8\"', '9\"',
        '10\"', '11\"', '12\"', '13\"', '14\"', '15\"', '16\"', '17\"', '18\"', '19\"',
        '20\"', '21\"', '22\"', '23\"', '24\"', '25\"', '26\"', '27\"', '28\"', '29\"', '30\"'];
    column_right.appendChild(makeOptionsVsTitle('tyreSize', 'Шины и диски', option));
    column_right.appendChild(makeCheckbox('winterTyreSetIncluded', 'Зимние шины в комплекте'));
    div5.appendChild(div);
}


function createDiv6DescriptionField(div6) {
    let div = document.createElement('div');
    let row = document.createElement('div');
    row.setAttribute('class', 'row');

    let col10 = document.createElement('div');
    col10.setAttribute('class', 'col-10');
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
    input.setAttribute('id', 'carPrice');
    input.setAttribute('type', 'number');

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
    let divInCol9 = document.createElement('div');
    let div = makeRowCol3Col9('type-of-usp', 'Вид объявления', divInCol9);

    let options = [ 'Продаю личный автомобиль',
                    'Автомобиль приобретен на продажу'    ];
    let select = document.createElement('select');
    select.setAttribute('id', 'typeOfUsedCarPosting');
    select.setAttribute('name', 'typeOfUsedCarPosting');
    select.setAttribute('value', pco.typeOfUsedCarPosting);
    makeSelectOptionsElement(select, options);
    divInCol9.appendChild(select);
    return div;
}

savedSuccess = function () {
    alert("Saved!");
}


let submitAndSavePostingButton = document.getElementById('saveButton');
submitAndSavePostingButton.onclick = () => saveFunction();

function collectAllFields(){
    function getValueFromSelectElement(divId){
        let e = document.getElementById(divId);
        return e.options[e.selectedIndex].value;
    }
    function getValueFromInputElement(divId){
        return  document.getElementById(divId).value;
    }
    function checkCarIsNew(){
        return (frontName ==='new-car');
    }


    return {
        vinCode: getValueFromInputElement('vinCode'),
        carIsNew: checkCarIsNew(),
        sellerId: pco.sellerId,
        typeOfUsedCarPosting: getValueFromSelectElement('typeOfUsedCarPosting'),
        statePlateNumber: getValueFromInputElement('statePlateNumber'),
        mileage: getValueFromInputElement('mileageInput'),
        numberOfOwners: getValueFromInputElement('ownerCount'),
        modelIdInAutoCatalogue: 0,
        carColor: getValueFromSelectElement('carColor'),
        carBrand: getValueFromSelectElement('carBrand'),
        carModel: getValueFromSelectElement('carModel'),
        carYear: getValueFromInputElement('carYear'),
        carBodyType: 'Sedan',
        numberOfDoors: 4,
        wasInAccident: getValueFromInputElement('wasInAccident'),
        dealerServiced: getValueFromInputElement('dealerServiced'),
        underWarranty: getValueFromInputElement('underWarranty'),
        hasServiceBook: getValueFromInputElement('hasServiceBook'),
        powerSteeringType: getValueFromSelectElement('powerSteeringType'),
        climateControlType: getValueFromSelectElement('climateControlType'),
        onWheelControl: getValueFromInputElement('onWheelControl'),
        thermalGlass: getValueFromInputElement('thermalGlass'),
        interiorType:  getValueFromSelectElement('interiorType'),
        leatherWheel: getValueFromInputElement('leatherWheel'),
        sunroof: getValueFromInputElement('sunroof'),
        heatedFrontSeats: getValueFromInputElement('heatedFrontSeats'),
        heatedRearSeats: getValueFromInputElement('heatedRearSeats'),
        heatedMirrors: getValueFromInputElement('heatedMirrors'),
        heatedRearWindow : getValueFromInputElement('heatedRearWindow'),
        heatedWheel: getValueFromInputElement('heatedWheel'),
        powerWindowsType: getValueFromSelectElement('powerWindowsType'),
        powerFrontSeats: getValueFromInputElement('powerFrontSeats'),
        powerRearSeats: getValueFromInputElement('powerRearSeats'),
        powerMirrorRegulation: getValueFromInputElement('powerMirrorRegulation'),
        powerSteeringWheelRegulation: getValueFromInputElement('powerSteeringWheelRegulation'),
        powerMirrorClose: getValueFromInputElement('powerMirrorClose'),
        frontSeatsMemory : getValueFromInputElement('frontSeatsMemory'),
        rearSeatsMemory: getValueFromInputElement('rearSeatsMemory'),
        mirrorRegulationMemory : getValueFromInputElement('mirrorRegulationMemory'),
        steeringWheelRegulationMemory: getValueFromInputElement('steeringWheelRegulationMemory'),
        parkingAssist: getValueFromInputElement('parkingAssist'),
        rainSensor: getValueFromInputElement('rainSensor'),
        lightSensor: getValueFromInputElement('lightSensor'),
        rearParkingSensor : getValueFromInputElement('rearParkingSensor'),
        frontParkingSensor: getValueFromInputElement('frontParkingSensor'),
        blindSpotZoneControl: getValueFromInputElement('blindSpotZoneControl'),
        rearCamera: getValueFromInputElement('rearCamera'),
        cruiseControl: getValueFromInputElement('cruiseControl'),
        onBoardComp: getValueFromInputElement('onBoardComp'),
        alarmSystem: getValueFromInputElement('alarmSystem'),
        powerDoorBlocking: getValueFromInputElement('powerDoorBlocking'),
        immobilizer: getValueFromInputElement('immobilizer'),
        satelliteAlarmControl: getValueFromInputElement('satelliteAlarmControl'),
        frontalAirbags: getValueFromInputElement('frontalAirbags'),
        kneeAirbags: getValueFromInputElement('kneeAirbags'),
        sideWindowAirbags: getValueFromInputElement('sideWindowAirbags'),
        frontSideAirbags: getValueFromInputElement('frontSideAirbags'),
        rearSideAirbags: getValueFromInputElement('rearSideAirbags'),
        absSystem: getValueFromInputElement('absSystem'),
        dtcSystem: getValueFromInputElement('dtcSystem'),
        trackingControl: getValueFromInputElement('trackingControl'),
        breakAssistSystem: getValueFromInputElement('breakAssistSystem'),
        emergencyBreakSystem: getValueFromInputElement('emergencyBreakSystem'),
        diffLockSystem: getValueFromInputElement('diffLockSystem'),
        pedestrianDetectSystem: getValueFromInputElement('pedestrianDetectSystem'),
        cdDvdBluRay: getValueFromInputElement('cdDvdBluRay'),
        mp3: getValueFromInputElement('mp3'),
        radio: getValueFromInputElement('radio'),
        tvSystem: getValueFromInputElement('tvSystem'),
        videoSystem: getValueFromInputElement('videoSystem'),
        mediaOnWheelControl: getValueFromInputElement('mediaOnWheelControl'),
        usb: getValueFromInputElement('usb'),
        aux: getValueFromInputElement('aux'),
        bluetooth: getValueFromInputElement('bluetooth'),
        gpsNavigation: getValueFromInputElement('gpsNavigation'),
        audioSystemType: getValueFromSelectElement('audioSystemType'),
        subwoofer: getValueFromInputElement('subwoofer'),
        frontLightType: getValueFromSelectElement('frontLightType'),
        antifogLights: getValueFromInputElement('antifogLights'),
        frontLightCleaning: getValueFromInputElement('frontLightCleaning'),
        adaptiveLights: getValueFromInputElement('adaptiveLights'),
        howToContactVsSeller: '',
        tyreSize: getValueFromSelectElement('tyreSize'),
        winterTyreSetIncluded: getValueFromInputElement('winterTyreSetIncluded'),
        typeOfEngine: '',
        wheelDrive: '',
        transmission: '',
        modification: '',
        configuration: '',
        title: 'Тачки титул',
        description: getValueFromInputElement('description'),
        contact: getValueFromInputElement('inputPhone'),
        meetingAddress: '',
        datePosting: '',
        condition: '',
        videoURL: 'https://youtube.com/',
        contactEmail: '',
        message: '',
        price: getValueFromInputElement('carPrice'),
        isActive: true,
        viewNumber: 0,
        city: 'Moscow'
    }

}

function saveFunction() {
    alert('Save is ok! ))))')

    let posting = collectAllFields();

    alert('Point 1 / Saving ... ');

    let json_posting = JSON.stringify(posting);
    const requestURL = '/api/posting/car/new-save';

    sendRequest('POST', requestURL, json_posting).then(() => {
        console.log("saved/ may be");
          savedSuccess();
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