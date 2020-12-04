$(document).ready(function () {
    getPostingInfo(Number.parseInt($("#postingId").text().valueOf()));
});

async function getPostingDto(id) {
    let response = await fetch("/api/posting/" + id, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
    return (await response.json()).data;
}

async function getCategoryByName(name) {
    let response = await fetch("/api/category/" + name, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
    return (await response.json()).data;
}

async function getPostingInfo(id) {
    let categoriesLine = $("#categoriesHrefs");

    let postingDto = await getPostingDto(id);
    let categoryDto = postingDto.category;

    let liCity = `<li><a href="#">${postingDto.city}</a></li>`;
    let liDot = `<li>·</li>`;
    let li;
    let favorite = await isFavorite();

    categoriesLine.append(liCity);
    categoriesLine.append(liDot);

    if (favorite) {
        $("#imgAdd" + id).hide()
        $("#imgDel" + id).show()
        $("#buttonFav_txt" + id).text("В избранном");
    } else {
        $("#imgAdd" + id).show();
        $("#imgDel" + id).hide();
        $("#buttonFav_txt" + id).text("Добавить в избранное");
    }

    while (true) {
        if(categoryDto === undefined) {
            break;
        }
        li = `<li><a href="#">${categoryDto.name}</a></li>`;
        categoriesLine.append(li);
        if (categoryDto.layer !== 1 && categoryDto.name != "undefined") {
            categoriesLine.append(liDot);
            categoryDto = await getCategoryByName(categoryDto.parentName);
        } else {
            break;
        }
    }

    if (postingDto.images.length > 0) {
        document.getElementById('mainRow').style.paddingBottom = '100px';

        for (let i = 0; i < postingDto.images.length; i++) {
            let indicator = "indicator" + i;
            if (i === 0) {
                document.getElementById('carousel-indic').innerHTML +=
                    `<li id="${indicator}" data-target="#ImageSlider" data-slide-to="${i}" class="active">
                        <img class="img-fluid" src="${postingDto.images[i].pathURL}" alt="">
                    </li>`

                document.getElementById('carouselInner').innerHTML +=
                    `<div class="carousel-item active">
                        <img class="d-block w-100" style="height:500px" src="${postingDto.images[i].pathURL}" alt="">
                    </div>`

            } else {

                document.getElementById('carousel-indic').innerHTML +=
                    `<li id="${indicator}" data-target="#ImageSlider" data-slide-to="${i}">
                        <img class="img-fluid" src="${postingDto.images[i].pathURL}" alt="">
                    </li>`

                document.getElementById('carouselInner').innerHTML +=
                    `<div class="carousel-item">
                        <img class="d-block w-100" style="height:500px" src="${postingDto.images[i].pathURL}" alt="">
                    </div>`

            }
        }

        document.getElementById('carouselInner').innerHTML +=
            `<a class="carousel-control-prev" href="#ImageSlider" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#ImageSlider" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>`

    } else {
        document.getElementById('carouselInner').innerHTML +=
            `<div class="carousel-item active">
                <img class="d-block w-100" style="height:500px" src="../images/empty_image.jpg" alt="">
            </div>`
    }

    let date = postingDto.datePosting.toString().substring(8, 10) + "-" +
        postingDto.datePosting.toString().substring(5, 7) + "-" +
        postingDto.datePosting.toString().substring(0, 4) + " " +
        postingDto.datePosting.toString().substring(11, 16);

    $("#title").append(`${postingDto.title}`);

    $("#timeOfPosting").append(date);
    $("#price").append(`${postingDto.price} ₽`);
    $("#contactNumber").append(`<br>${postingDto.contact}`);
    $("#address").append(`${postingDto.city}`);
    $("#description").append(`${postingDto.description}`);
    $("#viewNumber").append(`${postingDto.viewNumber}`);

}


async function onClickFav() {
    if (await isFavorite() === true) {
        deleteFromFavorites();
    } else {
        addToFavorites();
    }
}



async function addToFavorites() {
    let postingId = $("#postingId").text().valueOf();

    $("#imgAdd" + postingId).hide()
    $("#imgDel" + postingId).show()

    $("#buttonFav_txt" + postingId).text("В избранном");
    fetch(`/api/favorite/add/` + postingId, {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-type': 'application/json'
        }
    });
}

async function deleteFromFavorites() {
    let postingId = $("#postingId").text().valueOf();
    $("#imgAdd" + postingId).show()
    $("#imgDel" + postingId).hide()

    $("#buttonFav_txt" + postingId).text("Добавить в избранное");
    fetch(`/api/favorite/delete/` + postingId, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
}

async function isFavorite() {
    let response = await fetch("/api/favorite/get", {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
    let favorites = (await response.json()).data;

    if (favorites === "undefined") {
        return false;
    }
    let postingid = Number.parseInt($("#postingId").text().valueOf());
    for (let i = 0; i < favorites.length; i++) {
        if (favorites[i] === postingid) {
            return true;
        }
    }
    return false;
}





/*
{"success":true,
    "data": {
        "id":1602,
        "title":"Ремонт электроники",
        "description":"Быстро, качественно",
        "price":1000,
        "contact":"+79998887766",
        "datePosting":[2020,12,1,14,3,24,269814000],
        "images":[{"id":1599,"pathURL":"/images/numbers/4.jpg","postings":null},{"id":1598,"pathURL":"/images/numbers/3.jpg","postings":null},{"id":1597,"pathURL":"/images/numbers/2.jpg","postings":null},{"id":1596,"pathURL":"/images/numbers/1.jpg","postings":null},{"id":1595,"pathURL":"/images/numbers/0.jpg","postings":null}],
        "category":{"id":1313,"name":"Недвижимость","parent":false,"layer":1,"frontName":null},"city":"Ростов","meetingAddress":null,"isActive":null,"userEmail":null,"viewNumber":7}}
*/
