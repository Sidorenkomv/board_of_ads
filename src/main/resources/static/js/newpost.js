let displayLayer = 0;
let path = [];

$(document).ready(function () {
    getCategoryColumn(0, 0).then();
})

async function getCategoryColumn(parentID, selectLayer, frontName) {
    let categories = await getCategories(await getURL(parentID));
    await cleanCategoryColumns(selectLayer);

    if (categories.length !== 0) {
        displayLayer++;
        let temp = document.getElementById('cascadeTableButton_' + parentID);
        let parentName = (temp === null) ? 'Категория' : temp.textContent;

        document.getElementById('cascadeTableContainer').innerHTML +=
            '<div id="cascadeTableColumn' + displayLayer + '" class="cascade-table-column">' +
            '   <div class="category-table-title">'+parentName+'</div>' +
            '</div>';

        for (let i = 0; i < categories.length; i++) {
            let category = categories[i];
            let id = 'cascadeTableButton_' + (category.frontName === null ? category.id : category.frontName);
            let frontName = "\'" + category.frontName + "\'";
            document.getElementById('cascadeTableColumn' + displayLayer).innerHTML +=
                `<div id="${id}" onMouseOver="hoverOnMouseOver()" 
                        class="category-table-button inactive-category-table-button layer-${displayLayer}"  
                        onclick="clickOnCategoryButton(this, ${category.id}, ${displayLayer}, ${frontName})">
                ${category.name}
            </div>`
        }
        return true;
    } else {
        return await changeVisible(frontName);
    }
}

async function clickOnCategoryButton(o, id, selectLayer, frontName) {
    let className = '.layer' + selectLayer;
    $(className)
        .removeClass("active-category-table-button")
        .addClass("inactive-category-table-button").css("background-color", "#fff");
    $(o).removeClass("inactive-category-table-button")
        .addClass("active-category-table-button").css("background-color", "#0af", "color", "#fff");

    return await getCategoryColumn(id, selectLayer, frontName);
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

function hoverOnMouseOver() {
    $(".category-table-button").hover(
        function () {
            if (!this.classList.contains("active-category-table-button")) {
                $(this).css("background-color", "#0af");
            }
        }, function () {
            if (!this.classList.contains("active-category-table-button")) {
                $(this).css("background-color", "#fff");
            }
        });
}

async function getURL(parentID) {
    return (displayLayer === 0
        ? '/api/category/allParentCategory'
        : '/api/category/allChildCategories/' + parentID);
}

async function cleanCategoryColumns(selectLayer) {
    if (selectLayer <= displayLayer) {
        for (let i = selectLayer+1; i <= displayLayer; i++) {
            document.getElementById('cascadeTableColumn' + i).remove();
        }
        displayLayer = selectLayer;
    }
}

async function changeVisible(frontName) {
    $('#pathCategory').show();
    $('#visibleElement1').hide();
    $('#visibleElement2').show();
    $('#visibleElement3').show();

    switch(frontName) {
        case 'used-car':  await carPostingFunction(frontName);
            break;
        case 'new-car':  await carPostingFunction(frontName);
            break;

        default: alert("notFOUND")
            break;
    }

}

$('#pathCategoryButton').on('click', function () {
    $('#pathCategory').hide();
    $('#visibleElement1').show();
    $('#visibleElement3').hide();
});

async function sendPosting(body, frontName) {
    await fetch('/api/posting/new/' + frontName, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: body
    });
}
