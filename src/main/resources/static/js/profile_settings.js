let idOfSelectedCity;
let idOfSelectedRegion;
$(document).ready(function () {
    let selcity = document.getElementById("selectedCity");

    sendRequest('GET', '/api/city/userCity').then(data => data.data.name).then(city => {
        selcity.textContent = city
        document.getElementById("selectCity").selectedIndex = 0;

    }).catch(err => selcity.textContent = "Выберите город");

})


function sendMillionRequest(method, url, body = null) {
    return fetch(url).then(response => {
        return response.json()
    })
}


function sendRequest(method, url, body = null) {
    return fetch(url).then(response => {
        return response.json()
    })
}


$('#profileSettings, #profile-settings-from-header').on('click', async function () {
    $('#myPosts').addClass("d-none");
    document.getElementById('nav-settings').className = "tab-pane fade active show";
    let userResponse = await userService.getUserInfo();
    let user = userResponse.json();
    user.then(
        principal => {
            userService.getPostingCount(principal.data.principal.id)
                .then(posting => posting.json())
                .then(post => {
                    $('#postCount').text(post.data != null ? post.data.length : 0);
                    if (principal.data.principal.phone != null) {
                        $('#phone-number').text(principal.data.principal.phone);
                        $('#phoneCountPost').text(post.data != null ? post.data.length + ' объявлений' : '0 объявлений');
                    } else {
                        $('#phoneTd').addClass("d-none");
                    }
                })
            $('#userEmail').text(principal.data.principal.email);
            $('#userId').text(principal.data.principal.id);
            $('#fld_name').val(principal.data.principal.firstName);
            $('#selectedCity').text(principal.data.principal.city != null ? principal.data.principal.city.name : '');
            $('#selectedCity').val(principal.data.principal.city != null ? principal.data.principal.city.id : 0);
        }
    );
})

$('#cancelPhoneButton').on('click', function () {
    $('#phoneEditModal').modal('hide');
})

$('#cancelPhoneDeleteButton').on('click', function () {
    $('#phoneDeleteModal').modal('hide');
})

$('#savePhoneButton').on('click', function () {
    let data = {
        email: '',
        password: '',
        newPassword: '',
        phone: $('#phoneDeleteInput').val(),
        firstName: '',
        cityId: ''
    };
    userService.changeUserData(data)
        .then(userResponse => userResponse.json())
        .then(userResponse => {
            if (userResponse.success === true) {
                $('#phoneDeleteModal').modal('hide');
            }
        });
})

$('#changePhoneButton').on('click', function () {
    let data = {
        email: '',
        password: '',
        newPassword: '',
        phone: $('#phoneInput').val(),
        firstName: '',
        cityId: ''
    };
    userService.changeUserData(data)
        .then(userResponse => userResponse.json())
        .then(userResponse => {
            if (userResponse.success === true) {
                $('#phoneEditModal').modal('hide');
            }
        });
})

$("#editPhone").on('click', function () {
    $('#phoneEditModal').modal('show');
    $('#phoneModalTitle').text($('#phone-number').text());
})

$("#deletePhone").on('click', function () {
    $('#phoneDeleteModal').modal('show');
    $('#phoneDeleteModalTitle').text($('#phone-number').text());
})

$('#new_password').on('keyup', function () {
    if ($('#current_password').val() !== "" && $('#new_password').val() !== "") {
        $('#changePasswordButton').removeAttr('disabled');
    }
})

$('#changePasswordButton').on('click', function () {
    let data = {
        email: '',
        password: $('#current_password').val(),
        newPassword: $('#new_password').val(),
        phone: '',
        firstName: '',
        cityId: ''
    };
    userService.changeUserData(data)
        .then(userResponse => userResponse.json())
        .then(userResponse => {
            if (userResponse.success !== true) {
                $('#errorPassMessage').text(userResponse.error.text);
            } else {
                $('#SuccessMessage').removeClass("d-none");
                $('#SuccessMessage').addClass("bg-success text-dark py-2 d-block");
                $('#SuccessMessage').text("");
                $('#SuccessMessage').append('Пароль успешно изменен');
            }
        });
})

$('#selectCity').on('change', function () {
    $('#chooseCityModal').modal('show');
})


$("#cityListM a").on('click', function () {
    let value = $(this).html();
    let option = document.getElementById("selectedCity");
    option.textContent = value;
    document.getElementById("selectCity").selectedIndex = 0;
    idOfSelectedCity = $(this).attr('id')

});

$("#cityList a").on('click', function () {
    let value = $(this).html();
    let option = document.getElementById("selectedCity");
    option.textContent = value;
    document.getElementById("selectCity").selectedIndex = 0;
    idOfSelectedCity = $(this).attr('id')

});

$("#cityList1 a").on('click', function () {
    let value = $(this).html();
    let option = document.getElementById("selectedCity");
    option.textContent = value;
    document.getElementById("selectCity").selectedIndex = 0;
    idOfSelectedCity = $(this).attr('id')

});

$("#cityList2 a").on('click', function () {
    let value = $(this).html();
    let option = document.getElementById("selectedCity");
    option.textContent = value;
    document.getElementById("selectCity").selectedIndex = 0;
    idOfSelectedCity = $(this).attr('id')

});


$("#saveCityOrNameButton").on('click', function () {
    let data = {
        email: '',
        password: '',
        newPassword: '',
        firstName: $("#fld_name").val(),
        cityId: idOfSelectedCity
    };
    userService.changeUserData(data)
        .then(userResponse => userResponse.json())
        .then(userResponse => {
            if (userResponse.success === true) {
                $('#SuccessMessage').removeClass("d-none");
                $('#SuccessMessage').addClass("bg-success text-dark py-2 d-block");
                $('#SuccessMessage').text("");
                $('#SuccessMessage').append('Контактная информация успешно сохранена');
            }
        });
})


$('.js-regions-link').on('click', function () {
    $('#chooseCityModal').modal('hide');
})

otherCities.onclick = function () {
    showChooseCityModal();
}

$('#selectCity').on('change', function () {
    showChooseCityModal();
})


async function showChooseCityModal() {
    fillCityInModal();
    fillSelectRegionInModal();

    $('#chooseCityModal').modal('show');
}

function fillCityInModal() {
    sendMillionRequest('GET', '/api/city/millionCities').then(data => {
        let cid = 5001;
        for (let i = 0; i < 30; i++) {
            let millionCity = data.data[i];
            if (millionCity.name == 'Москва') {
                document.getElementById("msk5000").style.fontWeight = "bold"
                document.getElementById("msk5000").innerHTML = millionCity.name;
                document.getElementById("msk5000").id = millionCity.id;
                continue;
            } else if (millionCity.name == 'Санкт-Петербург') {
                document.getElementById("spb5000").style.fontWeight = "bold"
                document.getElementById("spb5000").innerHTML = millionCity.name;
                document.getElementById("spb5000").id = millionCity.id;
                continue;
            }
            ;
            document.getElementById(cid.toString()).style.fontSize = '12px';
            document.getElementById(cid.toString()).innerHTML = millionCity.name;
            document.getElementById(cid.toString()).id = millionCity.id;
            cid++;
        }
    }).catch(() => console.log(" "));

}

function fillSelectRegionInModal() {
    let selectRegion = document.getElementById("selectRegionInModal");
    sendRequest('GET', '/api/region/').then(data => data.data).then(regions => {
        for (let reg in regions) {
            let option = document.createElement("option");
            option.setAttribute("value", regions[reg].id);
            option.setAttribute("text", regions[reg].name + ' ' + regions[reg].formSubject);
            option.innerHTML = regions[reg].name + ' ' + regions[reg].formSubject;
            selectRegion.appendChild(option);

        }

    }).catch(err => console.log(err));
}

function fillSelectCityInModal(regionId) {
    let selectCity = document.getElementById("selectCityInModal");
         sendRequest('GET', '/api/city/region/'+regionId).then(data => data.data).then(cities => {

        for (let num in cities) {
            let option = document.createElement("option");
            option.setAttribute("value", cities[num].id);
            option.setAttribute("text", cities[num].name);
            option.innerHTML = cities[num].name;
            selectCity.appendChild(option);
        }
             $('#selectCityInModal')[0].disabled = false;
    }).catch(err => console.log(err));


}

$('#cityFromRegionButton').on('click', function () {
    alert('click');
})
$('#selectRegionInModal').on('change', function () {

    if ($('#selectRegionInModal').val() === "выбрать") {
        $('#selectCityInModal')[0].disabled = true;
        return
    }
    idOfSelectedRegion = $('#selectRegionInModal').val();
    if (idOfSelectedRegion != "выбрать") {
        $('#selectCityInModal')[0].disabled = true;
        $('#selectCityInModal').empty();
        fillSelectCityInModal(idOfSelectedRegion);
    }


})


$('#tooltip').tooltip();

$('#tooltip').on('click', function () {
        $('[data-toggle="popover"]').popover()
    }
);

$('#editEmail').on('click', function () {
    $('#emailEditModal').modal('show');
    $('#editEmailButton').attr('disabled', 'disabled');
})

$('#editEmailButton').on('click', function () {
    $('#emailEditModal').modal('hide');
    $('#emailEditConfirmModal').modal('show');
})

$('#emailInput').on('keyup', function () {
    const email = $("#emailInput").val();
    if (validateEmail(email)) {
        $('#editEmailButton').removeAttr('disabled');
    }
})

function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

$('#editConfirmEmailButton').on('click', function () {
    let data = {
        email: $("#emailInput").val(),
        password: $("#passwordInput").val(),
        phone: '',
        newPassword: '',
        firstName: '',
        cityId: ''
    };
    userService.changeUserData(data)
        .then(userResponse => userResponse.json())
        .then(userResponse => {
            if (userResponse.success !== true) {
                $('#errorMessage').append(userResponse.error.text);
            } else {
                $('#emailEditConfirmModal').modal('hide');
            }
        });
})

const http = {
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
    getUserInfo: async () => {
        return await http.fetch('/api/user/', {
            method: 'GET'
        });
    },
    changeUserData: async (data) => {
        return await http.fetch('/api/user/', {
            body: JSON.stringify(data),
            method: 'PUT'
        });
    },
    getPostingCount: async (id) => {
        return await http.fetch('/api/posting/userpost/' + id, {
            method: 'GET'
        });
    },

    findAllMillionCities: async () => {
        return await http.fetch('/api/city', {
            method: 'GET'
        });
    },

    getUserCity: async () => {
        return await http.fetch('/api/city', {
            method: 'GET'
        });
    }
}