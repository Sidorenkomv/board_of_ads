let allPostings = [];
selectedCategoryOption = "Любая категория";
selectedCity = $("#category-select-city option:selected").val();
textInput = $("#search-main-text").val();
photoOption = $("#image-select option:selected").val();

function getPostingsTable(posts) {
    if(posts === "undefined") {

    } else {
        for (let step = 0; step < posts.length; step++) {

            let postingDTO = posts[step];

            let date = postingDTO.datePosting.toString().substring(8, 10) + "-" +
                postingDTO.datePosting.toString().substring(5, 7) + "-" +
                postingDTO.datePosting.toString().substring(0, 4) + " " +
                postingDTO.datePosting.toString().substring(11, 16);

            document.getElementById('mainPageBody').innerHTML +=
                `<div id="main_page_posting" class="col-md-3">
                        <div id="cardPosting" class="card">
                            <div id="ImageSlider${step}" class="carousel slide" data-interval="false">
                                <ol id="carouselIndicators${step}" class="carousel-indicators">
         
                                </ol>
                                <div id="carouselInner${step}" class="carousel-inner">
                                    <div id="add${postingDTO.id}">
                                        <img data-id="${postingDTO.id}" class="addToWish" src="../images/heart.jpg" th:src="@{images/heart.jpg}">
                                    </div>
                                    <div id="delete${postingDTO.id}">
                                        <img data-id="${postingDTO.id}" class="deleteWish" src="../images/heart2.jpg" th:src="@{images/heart2.jpg}">
                                    </div>                                    
                                </div>
                            </div>
                            <div id="postingCardBody" class="card-body">
                                <a id="postingTitle" class="text-primary" href="/${postingDTO.id}">${postingDTO.title}</a>
                                <strong>
                                    <div id="price">${postingDTO.price} ₽</div>
                                </strong>
                                <div class="card-text text-muted">
                                    <div id="meetingPlace">${postingDTO.city}</div>
                                    <div id="timeOfPosting">${date}</div>
                                </div>
                            </div>
                        </div>
                    </div>`

            $("#add" + postingDTO.id).show()
            $("#delete" + postingDTO.id).hide()

            $(".addToWish").on('click', function (event) {
                event.preventDefault();

                let userid = $("#reguserid").val();

                let postingId = this.dataset.id;

                $("#add" + postingId).hide()
                $("#delete" + postingId).show()

                fetch(`/api/favorite/add/`, {
                    method: 'POST',
                    headers: {
                        'Accept': 'application/json, text/plain, */*',
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify({
                        posting: postingId,
                        userid: userid
                    })
                })
            });


            $('.deleteWish').on('click', function (event) {

                event.preventDefault();

                let postingId = this.dataset.id;

                $("#delete" + postingId).hide()
                $("#add" + postingId).show()

                fetch(`/api/favorite/delete/${postingId}`, {
                    method: 'DELETE',
                })
            })


            if (postingDTO.images.length > 0) {
                for (let i = 0; i < postingDTO.images.length; i++) {
                    let indicator = "indicator" + i;
                    if (i === 0) {
                        document.getElementById("carouselIndicators" + step).innerHTML +=
                            `<li id="${indicator}" data-target="#ImageSlider${step}" data-slide-to="${i}" class="active"></li>`

                        document.getElementById("carouselInner" + step).innerHTML +=
                            `<div class="carousel-item active">
                                    <a href="/${postingDTO.id}">
                                        <img id="postingImageRef" src="${postingDTO.images[i].pathURL}" class="card-img-top" alt="">
                                    </a>
                                </div>`

                    } else {
                        document.getElementById("carouselIndicators" + step).innerHTML +=
                            `<li id="${indicator}" data-target="#ImageSlider${step}" data-slide-to="${i}"></li>`

                        document.getElementById("carouselInner" + step).innerHTML +=
                            `<div class="carousel-item">
                                    <a href="/${postingDTO.id}">
                                        <img id="postingImageRef" src="${postingDTO.images[i].pathURL}" class="card-img-top" alt="">
                                    </a>
                                </div>`
                    }
                }
            } else {
                document.getElementById("carouselInner" + step).innerHTML +=
                    `<div class="carousel-item active">
                                    <a href="/${postingDTO.id}">
                                        <img id="postingImageRef" src="../images/empty_image.jpg" class="card-img-top" alt="">
                                    </a>
                                </div>`
            }
        }


        if ($("#reguserid").val()) {
            fetch(`/api/favorite/addregid/` + $("#reguserid").val())
            fetch(`/api/favorite/addregipafter/` + $("#reguserid").val())
        }


    }
}

function getNewPostings() {

    selectedCategoryOption = $("#category-select option:selected").text();
    selectedCity = $("#category-select-city option:selected").val();
    textInput = $("#search-main-text").val();
    photoOption = $("#image-select option:selected").val();

    reinstallTable(selectedCategoryOption, selectedCity, textInput, photoOption);
}

$(document).ready(function () {
    reinstallTable(selectedCategoryOption, selectedCity, textInput, photoOption);
})

async function reinstallTable(categoryOption, cityOption, searchTextOption, photoOption) {

    document.querySelectorAll('#main_page_posting').forEach(block => block.remove())

    allPostings = await getData(categoryOption, cityOption, searchTextOption, photoOption);

    getPostingsTable(allPostings);
}

async function getData(categoryOption, cityOption, searchTextOption, photoOption) {
    let response = await fetch("/api/posting/search" + "?catSel=" + categoryOption
        + "&citSel=" + cityOption
        + "&searchT=" + searchTextOption
        + "&phOpt=" + photoOption, {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
    });
    return (await response.json()).data;
}