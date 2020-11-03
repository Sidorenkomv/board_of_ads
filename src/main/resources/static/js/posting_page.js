$(document).ready(function () {
    getPostingInfo($("#postingId").text().valueOf());
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
    let postingId = $("#postingId").text().valueOf();

    categoriesLine.append(liCity);
    categoriesLine.append(liDot);

    if (isFavorite()) {
        $("#imgAdd" + postingId).show()
        $("#imgDel" + postingId).hide()
        $("#buttonFav_txt" + postingId).textContent="Добавить в избранное";
    } else {
        $("#imgAdd" + postingId).hide();
        $("#imgDel" + postingId).show();
        $("#buttonFav_txt" + postingId).textContent="В избранном";
    }

    while (true) {
        li = `<li><a href="#">${categoryDto.name}</a></li>`;
        categoriesLine.append(li);
        if (categoryDto.layer !== 1) {
            categoriesLine.append(liDot);
            categoryDto = getCategoryByName(categoryDto.parentName);
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

    $("#timeOfPosting").append(date);
    $("#price").append(`${postingDto.price} ₽`);
    $("#contactNumber").append(`<br>${postingDto.contact}`);
    $("#address").append(`${postingDto.city}`);
    $("#description").append(`${postingDto.description}`);

}

async function onClickFav() {
    if (await isFavorite() === true) {
        deleteFromFavorites();
    } else {
        addToFavorites();
    }
}

async function addToFavorites() {
    // let userid = $("#reguserid").val();
    let postingId = $("#postingId").text().valueOf();
    alert("add "+postingId);

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
    // let userid = $("#reguserid").val();
    let postingId = $("#postingId").text().valueOf();
    //alert(postingId);
    alert("del "+postingId);
     $("#imgAdd" + postingId).show()
     $("#imgDel" + postingId).hide()

    $("#buttonFav_txt" + postingId).text("Добавить в избранное");
    fetch(`/api/favorite/delete/` + postingId, {
        method: 'GET',
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
    let postingId = $("#postingId").text().valueOf();
    for (let i = 0; i < favorites.length; i++) {
        if (favorites[i].id == postingId) {
            return true;
        }
    }
    return false;
}
