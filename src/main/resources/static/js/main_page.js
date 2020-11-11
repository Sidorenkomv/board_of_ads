let buttonAdd = $('#searchCityDiv');

$("#region, #category-select-city").click(function () {
    $('#searchModel').modal('show');
});

async function addCategories() {

    let categorySelect = $('.categoriesSelect');
    await categorySelect.append('<option id="anyCategory">Любая категория</option>');

    let parentCategories = await getCategories('/api/category/allParentCategory');

    for (let i = 0; i < parentCategories.length; i++) {
        let parentCat = parentCategories[i];

        categorySelect.append(`<option id="categoryId${parentCat.id}" class="category-parent">${parentCat.name.toUpperCase()}</option>`);

        let childCategories = await getCategories('/api/category/allChildCategories/' + parentCat.id);

        for (let i = 0; i < childCategories.length; i++) {
            let childCat = childCategories[i];

            categorySelect.append(`<option id="categoryId${childCat.id}">${childCat.name}</option>`);
        }
    }
}

let changedCityName;
let regionPosts;

function clickCountButton() {
    $('#category-select-city').empty();
    $('#cityInput').empty();
    $('#searchModel').modal('hide');
    let row = `<option>` + changedCityName + `</option>`;
    $('#category-select-city').append(row);
    reinstallTable(selectedCategoryOption, changedCityName, $("#search-main-text").val(), $("#image-select option:selected").val())
}

$('select#cities').on('change', function () {
    $('input[name="cityInput"]').val(this.value);
});

function onOptionHover() {
    $(".opt").mouseover(
        function () {
            $(this).css('background', '#99ccff')
        });
    $(".opt").mouseleave(
        function () {
            $(this).css('background', '#fff')
        });
}

async function onClickOpt(id) {
    changedCityName = id;
    $('.typeahead').val(id);
    $('#citiesSelect').remove();
    let usersResponse;
    if (id.includes('Область')
        || id.includes('Край')
        || id.includes('Республика')
        || id.includes('Автономный округ')
        || id.includes('Город')
    ) {
        usersResponse = await userService.findPostingByRegionName(id);
    } else {
        usersResponse = await userService.findPostingByCityName(id);
    }
    posts = usersResponse.json();
    $('#countPostButton').empty();
    let sizeArray = 0;
    posts.then(posts => {
        posts.data.forEach((posting) => {
            let temp = selectedCategoryOption;
            if(temp === "Любая категория") {
                temp = posting.category;
            }
            if(posting.category === temp) {
                sizeArray++;
            }
        })
    }).then(() => {
            $('#countPostButton').remove();
            let button = `<button
                                type="button"
                                class="btn btn-primary position-fixed"
                                onclick="clickCountButton()"
                                id="countPostButton">Показать ` + sizeArray + ` объявлений
                          </button>`;
            buttonAdd.append(button);
        }
    );
    regionPosts = (await posts).data;
}


$(document).ready(function () {
    viewCities();
    addCategories();
    $('#buttonAuth').on('click', function () {
        $('#emailAuth').addClass("redborder");
        authorization();
    });

    const button = document.getElementById('buttonAuth');

    $('#emailAuth').keyup(function(e) {
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) { //Enter keycode
            button.click();
        }
    });

    $('#passwordAuth').keyup(function(e) {
        var code = (e.keyCode ? e.keyCode : e.which);
        if (code == 13) { //Enter keycode
            button.click();
        }
    });
});

async function authorization() {
    $('#emailAuth').removeClass("redborder");
    $('#passwordAuth').removeClass("redborder");
    let userAuth = {
        email: $("#emailAuth").val(),
        password: $("#passwordAuth").val()
    };
    try {
        const authResponse = await fetch('/api/auth', {
            method: "POST",
            credentials: 'same-origin',
            body: JSON.stringify(userAuth),
            headers: {
                'content-type': 'application/json'
            }
        })
        let authMessage = authResponse.json();
        authMessage.then(authMessage => {
            if (authMessage.success === true) {
                $('#registrationModalCenter').modal('hide');
                $('#emailAuth').val("");
                $('#passwordAuth').val("");
                location.reload();
            } else {
                let errorMessage = authMessage.error;
                if (errorMessage.text === "User not found!") {
                    $('#emailAuth').addClass("redborder");
                }
                if (errorMessage.text === "Incorrect password!") {
                    $('#passwordAuth').addClass("redborder");
                }
            }
        });
    } catch (error) {
        console.log('Возникла проблема с вашим fetch запросом: ', error.message);
    }
}

let cities;
let posts;

async function viewCities() {
    $('#category-select-city').empty();
    const usersResponse = await userService.findAllCity();
    cities = usersResponse.json();
    const postsResponse = await userService.findAllPostings();
    posts = postsResponse.json();
    let sizeArray = 0;
    posts.then(posts => {
        posts.data.forEach((posting) => {
            let temp = selectedCategoryOption;
            if(temp === "Любая категория") {
               temp = posting.category;
            }
            if(posting.category === temp) {
                sizeArray++;
            }
        })
    }).then(() => {
            let button = `<button 
                                type="button" 
                                class="btn btn-primary position-fixed"   
                                id="countPostButton">Показать ` + sizeArray + ` объявлений
                          </button>`;
            buttonAdd.append(button);
        }
    );
}

$('.typeahead').on('keyup', function () {
    addOptions();
    $('#countPostButton').attr("disabled", true);
});

function addOptions() {
    $('#citiesSelect').remove();
    $('#citiesSelect').empty();
    let select = `<select id="citiesSelect" size="7" class="form-control"></select>`;
    $('.citiesOptions').append(select);
    let addForm = $(".typeahead").val().toLowerCase();
    cities.then(cities => {
        cities.data.forEach(city => {
            if (city.name.toLowerCase().includes(addForm)) {
                let userRow = `<option onmouseover="onOptionHover()" 
                                       onclick="onClickOpt(this.id)"
                                       id="${city.name}"
                                       class="opt"                                
                                       text="${city.name}">
                                           <div>${city.name}</div>
                                           <div>${' ' + city.regionFormSubject}</div>
                                </option>`;
                $('#citiesSelect').append(userRow);
            }
        });
    });
}

const httpHeaders = {
    fetch: async function (url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });
        return response;
    }
};

const userService = {
    findAllCity: async () => {
        return await httpHeaders.fetch('/api/city');
    },
    findPostingByCityName: async (name) => {
        return await httpHeaders.fetch('/api/posting/city/' + name);
    },
    findPostingByRegionName: async (name) => {
        return await httpHeaders.fetch('/api/posting/region/' + name);
    },
    findAllPostings: async () => {
        return await httpHeaders.fetch('/api/posting/');
    }
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