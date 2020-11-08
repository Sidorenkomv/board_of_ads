let displayLayer = 1;

$(document).ready(function () {
    getParentCategories().then();
})

async function getParentCategories() {

    let categories = await getCategories('/api/category/allParentCategory');

    for (let i = 0; i < categories.length; i++) {
        let category = categories[i];
        document.getElementById('cascadeTableColumn1').innerHTML +=
            `<div class="category-table-button inactive-category-table-button layer1" 
                    id="categoryTableButton${category.id}" onclick="clickOnCategoryButton(this, ${category.id}, 1)" 
                    onMouseOver="this.style.backgroundColor='DeepSkyBlue', this.style.color='white'" 
                    onMouseOut="this.style.backgroundColor='white', this.style.color='#555'">
                ${category.name}
            </div>`
    }
    return true;
}

async function getChildCategory(parentID, selectLayer) {

    let categories = await getCategories('/api/category/allChildCategories/' + parentID);

    if (selectLayer <= displayLayer) {
        for (let i = selectLayer+1; i <= displayLayer; i++) {
            document.getElementById('cascadeTableColumn' + i).remove();
        }
        displayLayer = selectLayer;
    }

    if (categories.length !== 0) {
        let parent = await getCategories('/api/category/' + parentID);

        document.getElementById('cascadeTableContainer').innerHTML +=
            '<div id="cascadeTableColumn' + categories[0].layer + '" class="cascade-table-column">' +
            '   <div class="category-table-title">'+parent.name+'</div>' +
            '</div>';

        for (let i = 0; i < categories.length; i++) {
            let category = categories[i];
            let id = 'cascadeTableElement_' + (category.frontName === null ? category.id : category.frontName);
            document.getElementById('cascadeTableColumn' + category.layer).innerHTML +=
                `<div class="category-table-button inactive-category-table-button layer${category.layer}" 
                        id="${id}" onclick="clickOnCategoryButton(this, ${category.id}, ${category.layer})"
                        onMouseOver="this.style.backgroundColor='#0af', this.style.color='white'" 
                        onMouseOut="this.style.backgroundColor='white', this.style.color='#555'">
                ${category.name}
            </div>`
        }
        displayLayer++;
        return true;
    } else {
        $('#pathCategory').show();
        $('#visibleElement1').hide();
        $('#visibleElement2').show();
        $('#visibleElement3').show();
        return true;
    }
    return false;
}

async function clickOnCategoryButton(o, id, selectLayer) {
    let className = '.layer' + selectLayer;
    $(className)
        .removeClass("active-category-table-button")
        .addClass("inactive-category-table-button").css("background-color", "#fff");
    $(this).removeClass("inactive-category-table-button")
        .addClass("active-category-table-button").css("background-color", "#0af", "color", "#fff");
    /*const items = document.querySelectorAll('.layer' + selectLayer);
    Array.from(items).forEach(item => {
        item.classList.remove('active-category-table-button');
        item.classList.add('inactive-category-table-button');
    })

    $(object).addClass("active-category-table-button").css("background-color", "DeepSkyBlue", "color", "#ffffff");*/

    return await getChildCategory(id, selectLayer);
}

async function getCategories(url) {
    let response = await fetch(url, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        }
    });
    return (await response.json()).data;
}

/*function hoverOnCategoryButton() {
    $(".category-table-button").hover(
        function () {
            if (this.classList.contains("inactive-category-table-button")) {
                $(this).css("background-color", "#a5eaf8");
            }
        });
    $(".category-table-button").hover(
        function () {
            if (this.classList.contains("active-category-table-button")) {
                $(this).css("background-color", "#ffffff");
            }
        });
}*/

$('#pathCategoryButton').on('click', function () {
    $('#pathCategory').hide();
    $('#visibleElement1').show();
    $('#visibleElement3').hide();
});
