let displayLayer = 0;
let path = [];
$(document).ready(function () {
    if ($("#reguserid").val()) {
        getCategoryColumn(0, 0).then();
    }

    $('#buttonAuth').on('click', function () {
        $('#emailAuth').addClass("redborder");
        authorization().then();
    });
})

async function getCategoryColumn(selectedCategoryId, selectLayer, frontName) {
    let categories = await getCategories(await getURL(selectedCategoryId));
    await cleanCategoryColumns(selectLayer);

    if (categories.length !== 0) {
        displayLayer++;
        let temp = document.getElementById('cascadeTableButton_' + selectedCategoryId);
        let parentName = (temp === null) ? 'Категория' : temp.textContent;

        if (temp !== null) {
            path.push(temp.textContent + ' / ');
        }

        document.getElementById('cascadeTableContainer').innerHTML +=
            '<div id="cascadeTableColumn' + displayLayer + '" class="cascade-table-column">' +
            '   <div class="category-table-title">' + parentName + '</div>' +
            '</div>';

        for (let i = 0; i < categories.length; i++) {
            let category = categories[i];
            let id = 'cascadeTableButton_' + category.id;
            let frontName = "\'" + category.frontName + "\'";
            document.getElementById('cascadeTableColumn' + displayLayer).innerHTML +=
                `<div id="${id}" onMouseOver="hoverOnMouseOver()" 
                        class="category-table-button inactive-category-table-button layer${displayLayer}"  
                        onclick="clickOnCategoryButton(this, ${category.id}, ${displayLayer}, ${frontName})">
                ${category.name}
            </div>`
        }
        return true;
    } else {
        let temp;
        temp = document.getElementById('cascadeTableButton_' + selectedCategoryId);
        path.push(temp.textContent);
        document.getElementById('pathCategoryButton').textContent = await getPath(path);
        return await changeVisible(frontName, selectedCategoryId);
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
        for (let i = selectLayer + 1; i <= displayLayer; i++) {
            document.getElementById('cascadeTableColumn' + i).remove();
        }
        path.splice(selectLayer - 1, path.length - selectLayer + 1);
        displayLayer = selectLayer;
    }
}

async function changeVisible(frontName, id) {
    $('#pathCategory').show();
    $('#visibleElement1').hide();
    $('#visibleElement3').show();

    switch (frontName) {
        case 'used-car':
            await carPostingFunction(frontName, id);
            break;
        case 'new-car':
            await carPostingFunction(frontName, id);
            break;
        case 'householdAppliances':
            await getHouseholdAppliancesForm(frontName, id);
            break;
        case 'clothes':
            showClothesForm(frontName, id);
            break;
        case 'hobbyAndRest':
            await getHobbyAndRestForm(frontName, id);
            break;
        case 'tickets':
            await getTicketsForm(frontName, id);
            break;
        case 'shoes':
            showShoesForm(frontName, id);
            break;
        case 'other-clothes':
            showOtherClothesForm(frontName, id);
            break;
        case 'audiovideo':
            await getAudioVideoForm(frontName, id);
            break;
        case 'dogs':
            await getDogsForm(frontName, id);
            break;
        case 'vacancy':
            await getVacancyForm(frontName, id);
            break;
        case 'ready-business':
            showReadyBusinessForm(frontName, id);
            break;
        case 'equpment-for-business':
            showEquipmentForBusinessForm(frontName, id);
            break;
        case 'sellEstate' : await getEstate(frontName, id)
            break;
        case 'rentAnEstate' : await getEstate(frontName, id)
            break;
        case 'buyEstate' : await getEstate(frontName, id)
            break;
        case 'getAnEstate' : await getEstate(frontName, id)
            break;
        default:
            alert('For frontName="' + frontName + '" not found posting form');
            break;
    }

}

$('#pathCategoryButton').on('click', function () {
    $('#pathCategory').hide();
    $('#visibleElement1').show();
});

async function sendPosting(body, url) {
    await fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    });
}

async function getPath(arr) {
    let result = '';
    arr.forEach(item => {
        result = result + item;
    })
    return result;
}