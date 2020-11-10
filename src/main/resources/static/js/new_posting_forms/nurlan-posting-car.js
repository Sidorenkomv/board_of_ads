let frontName;

function carPostingFunction(fn) {
    frontName = fn;
    fillNewCarPostingFields();
}

submitUsedCarForm = function(){
    let formM = document.getElementById("used-car-posting-form");
    formM.submit();
    let posting = formM.attr('object');

    console.log("function is called");
    console.log(posting);

    alert("Saved!");
}

function fillNewCarPostingFields() {

    // Вызвать объект

    let mainDiv = document.createElement('div');
    mainDiv.setAttribute('id','car-post-fields-main-container');
    document.getElementById('visibleElement2').appendChild(mainDiv);

    changePathCategory();
    changeMeetingPlace();

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

    div1.appendChild(createDiv1Photo());
    div1.appendChild(createDiv1Colors());
    div1.appendChild(createDiv1YouTube());

    div2.appendChild(createDiv1Colors());



    // div1.appendChild(make4To8Container1()); Use it later in price field

}

function createDiv1Photo(){
    let div = document.createElement('div');
    div.setAttribute('id','photoDiv' );
    div.setAttribute('class', 'row');
    div.setAttribute('data-marker', 'images');

    let div1_1 = document.createElement('div');
    div1_1.setAttribute('class','col-3');
    div.appendChild(div1_1);

    let label1 = document.createElement('label');
    label1.setAttribute('class', 'fieldset-label');
    label1.setAttribute('data-marker', 'label');
        let span1 = document.createElement('span');
        span1.setAttribute('class', 'description-label');
        span1.innerText = "Фотографии";
        label1.appendChild(span1);
        let div1_1_l_1 = document.createElement('div');
        div1_1_l_1.setAttribute('class','fieldset-label-icon');
        div1_1_l_1.setAttribute('data-marker', 'icon');
        div1_1_l_1.innerText = "Не более 40";
        label1.appendChild(div1_1_l_1);
    div1_1.appendChild(label1);

    let div1_2 = document.createElement('div');
    div1_2.setAttribute('class','col-5');
    div.appendChild(div1_2);
    let div1_2_1 = document.createElement('div');
    div1_2_1.setAttribute('class','fieldset-field-21qUq');
    div1_2_1.setAttribute('data-marker','field');
    div1_2.appendChild(div1_2_1);

    let div1_2_1_1 = document.createElement('div');
    div1_2_1_1.setAttribute('class','uploader-root-2eaHO uploader-root_design-photo-7MGKB uploader-size-s-2Ecqw');
    div1_2_1.appendChild(div1_2_1_1);

    let div1_2_1_1_last = document.createElement('div');
    div1_2_1_1_last.setAttribute('class','uploader-list');
    div1_2_1_1.appendChild(div1_2_1_1_last);

    let label2 = document.createElement('label');
    label2.setAttribute('class','uploader-item uploader-item_add-_sMoP button-button-2JBTe button-size-s-2RLvz button-default-szBWv');
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
 return div;
}

function makeDescriptionText(text){
    let div = document.createElement('span');
    div.setAttribute('class', 'description-label');
    div.innerText = text;
    return div;
}

function createDiv1Colors(){
    let row = document.createElement('div');
    row.setAttribute('class', 'row');

    let col3 = document.createElement('div');
    col3.setAttribute('class', 'col-3');
    col3.appendChild(makeDescriptionText('Цвет'));
    row.appendChild(col3);

    let col9 = document.createElement('div');
    col9.setAttribute('class', 'col-9');
        let label = document.createElement('label');
        label.setAttribute('for', 'car-color');

        let select = document.createElement('select');
        select.setAttribute('id', 'car-color');
        select.setAttribute('name', 'car-color');
            let option1 = document.createElement("option");
            option1.text = "Kiwi";
            let option2 = document.createElement("option");
            option2.text = "Mango";
            select.add(option1);
            select.add(option2);

        col9.appendChild(label);
        col9.appendChild(select);

    row.appendChild(col9);
    return row;
}

function createDiv1YouTube() {
    let row = document.createElement('div');
    row.setAttribute('class', 'row');

    let col3 = document.createElement('div');
    col3.setAttribute('class', 'col-3');
    col3.appendChild(makeDescriptionText('Видео c YouTube'));
    row.appendChild(col3);

    let col9 = document.createElement('div');
    col9.setAttribute('class', 'col-9');
    let input = document.createElement('input');
    input.setAttribute('class', 'input-input');
    input.setAttribute('id', 'videoUrl');
    input.setAttribute('name', 'videoUrl');
    input.setAttribute('placeholder', 'Например: https://www.youtube.com/watch?v=qPeVoi6OmRc');
    input.setAttribute('autocomplete', 'disabled');
    input.setAttribute('type', 'text');
    col9.appendChild(input);

    row.appendChild(col9);
    return row;
}






function make4To8Container1(id_suffix){
    let div = document.createElement('div');
    div.setAttribute('id','container-' + id_suffix);
    div.setAttribute('class', 'container' );
    let row = document.createElement('div');
    row.setAttribute('class', 'row category-head-container');
    div.appendChild(row);
    let col4 = document.createElement('div');
    col4.setAttribute('class', 'col-4');
    let col8 = document.createElement('div');
    col8.setAttribute('class', 'col-8');
    row.appendChild(col4);
    row.appendChild(col8);

    // let lab = document.createElement('label');
    // lab.setAttribute('class', 'col-sm-3 col-form-label')
    // lab.innerText = "Состояние";
   // col4.appendChild(lab);
    col4.appendChild(makeLabelInputFieldsForPrice('555'));

    return div;

}


function makeLabelInputFieldsForPrice(id_suffix){
    let div = document.createElement('label');
    div.setAttribute('id','label-' + id_suffix);
    div.setAttribute('for','carPrice-' + id_suffix);
   // div.setAttribute('class', 'container' );
    let input= document.createElement('input');
    input.setAttribute('class', 'row category-head-container');
    input.setAttribute('id', 'carPrice-' + id_suffix);
    input.setAttribute('type', 'text');
  //  input.setAttribute('value', 'text');
    div.appendChild(input);

    return div;

}


function makeMainAndTitleFields(id_suffix, title){
    let div = makeMainContainer(id_suffix);
    div.appendChild(makeTitleFields(id_suffix, title));
    return div;
}

function makeTitleFields(id_suffix, title) {
    let div = document.createElement('div');
    div.setAttribute('id','head-title-' + id_suffix);
    div.setAttribute('class', 'category-head-container' );
    let cht = document.createElement('div');
    cht.setAttribute('class', 'category-head-text' );
    cht.innerText = title;
    div.appendChild(cht);
    return div;
}

function makeMainContainer(id_suffix){
    let div = document.createElement('div');
    div.setAttribute('id','main-container-' + id_suffix);
    div.setAttribute('class', 'main-container' );
    return div;
}

function changePathCategory(){
    let a = document.getElementById('thisCategoryText');
    if (frontName === 'used-car') {
        a.innerText = 'Транспорт / Автомобили / С пробегом';
    } else {
        a.innerText = 'Транспорт / Автомобили / Новые';
    }
}

function changeMeetingPlace(){
    document.getElementById('meetingPlace').innerText = 'Место осмотра';
}