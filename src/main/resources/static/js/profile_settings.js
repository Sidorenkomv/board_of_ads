$('#profileSettings, #profile-settings-from-header').on('click', async function() {
    $('#myPosts').addClass("d-none");
    document.getElementById('nav-settings').className = "tab-pane fade active show";
    let userResponse = await userService.getUserInfo();
    let user = userResponse.json();
    user.then(
        principal => {
            userService.getPostingCount(principal.data.principal.id)
                .then( posting => posting.json())
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
            $('#fld_name').val(principal.data.principal.firsName);
            $('#selectedCity').text(principal.data.principal.city != null ? principal.data.principal.city.name : '');
            $('#selectedCity').val(principal.data.principal.city != null ? principal.data.principal.city.id : 0);
        }
    );
})

$('#cancelPhoneButton').on('click', function() {
    $('#phoneEditModal').modal('hide');
})

$('#cancelPhoneDeleteButton').on('click', function() {
    $('#phoneDeleteModal').modal('hide');
})

$('#savePhoneButton').on('click', function() {
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

$('#changePhoneButton').on('click', function() {
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

$("#editPhone").on('click', function() {
    $('#phoneEditModal').modal('show');
    $('#phoneModalTitle').text($('#phone-number').text());
})

$("#deletePhone").on('click', function() {
    $('#phoneDeleteModal').modal('show');
    $('#phoneDeleteModalTitle').text($('#phone-number').text());
})

$('#new_password').on('keyup', function() {
    if ($('#current_password').val() !== "" && $('#new_password').val() !== "") {
        $('#changePasswordButton').removeAttr('disabled');
    }
})

$('#changePasswordButton').on('click', function() {
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
                $('#errorPassMessage').append(userResponse.error.text);
            } else {
                $('#SuccessMessage').removeClass("d-none");
                $('#SuccessMessage').addClass("bg-success text-dark py-2 d-block");
                $('#SuccessMessage').text("");
                $('#SuccessMessage').append('Пароль успешно изменен');
            }
        });
})

$('#selectCity').on('change', function() {
    $('#chooseCityModal').modal('show');
})

$('#tooltip').tooltip();

$('#tooltip').on('click', function() {
    $('[data-toggle="popover"]').popover()
    }
);

$('#editEmail').on('click', function() {
    $('#emailEditModal').modal('show');
    $('#editEmailButton').attr('disabled','disabled');
})

$('#editEmailButton').on('click', function() {
    $('#emailEditModal').modal('hide');
    $('#emailEditConfirmModal').modal('show');
})

$('#emailInput').on('keyup', function() {
    const email = $("#emailInput").val();
    if (validateEmail(email)) {
        $('#editEmailButton').removeAttr('disabled');
    }
})

function validateEmail(email) {
    const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}

$('#editConfirmEmailButton').on('click', function() {
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

$("#saveCityOrNameButton").on('click', function() {
    let data = {
        email: '',
        password: '',
        newPassword: '',
        firstName: $("#fld_name").val(),
        cityId: $("#selectCity").val()
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

const http = {
    fetch: async function(url, options = {}) {
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
    }
 }