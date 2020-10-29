let viewAllUsersUrl = '/api/admin/allUsers';
let getUserById = '/api/admin/user';
let deleteUserById = '/api/admin/user';
let createNewUser = '/api/admin/newUser';
let updateUser = '/api/admin/newUserData';
let allRoles = '/api/admin/allRoles';

let adminUsersTable = $('#userTableJs tbody');
let deleteButtonInModalForm = $('#deleteButtonInModal div');
let saveButtonInModalForm = $('#updateButtonInModal div');

let elementCloseDeleteModal1 = document.getElementById('closeDeleteModal');
let elementCloseDeleteModal2 = document.getElementById('closeDeleteModal2');
let elementCreateUserRoles = document.getElementById('roleSelect');
let elementCreateUser = document.getElementById('createUser');
let elementCloseUpdateModal1 = document.getElementById('closeUpdateModal');
let elementCloseUpdateModal2 = document.getElementById('closeUpdateModal2');
let elementCloseCreateNewUserModal = document.getElementById('closeNewUserModal');
let elementCreateNewUserHref = document.getElementById('addUser');

let elementUserTable = document.getElementById('userTableAtAdminPanel');

$(document).ready(function () {
    showAllUsersTable();
    createRoleSelector();
});

$(document).mouseup(function (e) {
    let modalUpd = $('#updateModal');
    let modalDel = $('#deleteModal');
    let modalNew = $('#newUserModal');

    if (modalUpd.is(e.target)) {
        document.getElementById('updButtInModal').remove();
        clearTheValidateUpdate();
    }
    if (modalDel.is(e.target)) {
        document.getElementById('delButtInModal').remove();
    }
    if (modalNew.is(e.target)) {
        clearTheValidateCreate();
    }
});

//ОСНОВНЫЕ ФУНКЦИИ

//Функция формирующая селекты

function createRoleSelector() {
    fetch(allRoles)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            if (data.success) {
                data.data.map(roles => {
                    //селект в update
                    let select = document.getElementById('userUpdRoles');
                    let optionOfSelect = document.createElement('option');
                    let nameOfRole = document.createTextNode(roles.name);
                    optionOfSelect.setAttribute('id', roles.id);
                    optionOfSelect.setAttribute('value', roles.name);
                    optionOfSelect.append(nameOfRole);
                    select.appendChild(optionOfSelect);

                    //селект в new user
                    let newUserSelect = document.getElementById('roleSelect');
                    let optionOfSelect1 = document.createElement('option');
                    let nameOfRole1 = document.createTextNode(roles.name);
                    optionOfSelect1.setAttribute('id', roles.id);
                    optionOfSelect1.setAttribute('value', roles.name);
                    optionOfSelect1.append(nameOfRole1);
                    newUserSelect.appendChild(optionOfSelect1);
                });
            }
        });
}

//Функция заполняющая таблицу пользователей
function showAllUsersTable() {
    $.i18n().load({
        en: {
            'button-edit': 'Edit',
            'button-delete': 'Delete',
            'button-save': 'Save'
        },
        ru: {
            'button-edit': 'Редактировать',
            'button-delete': 'Удалить',
            'button-save': 'Сохранить'
        }
    });
    const urlParams = new URLSearchParams(window.location.search);
    const myParam = urlParams.get('locale');
    if (myParam === 'en') {
        $.i18n().locale = 'en';
    }

    let userIdForDelete = 0;
    let userIdForUpdate = 0;

    fetch(viewAllUsersUrl)
        .then((response) => {
            return response.json();
        })
        .then((data) => {
            if (data.success) {
                data.data.map(user => {

                    let buttonDelete = document.createElement('button');
                    let buttonEdit = document.createElement('button');
                    let tdEdit = document.createElement('td');
                    let tdDelete = document.createElement('td');
                    let tr = document.createElement('tr');

                    tr.setAttribute('id', "userDataTable");

                    const userRoles = user.roles.map(role => {
                        return role.name;
                    }).join(", ");

                    //создаем поля таблицы
                    let tdId = document.createElement('td');
                    let tdEmail = document.createElement('td');
                    let tdUserName = document.createElement('td');
                    let tdLastName = document.createElement('td');
                    let tdPhone = document.createElement('td');
                    let tdDataReg = document.createElement('td');
                    let tdEnabled = document.createElement('td');
                    let tdRoles = document.createElement('td');

                    // если есть null поля - то меняем на No Data
                    for (let o in user) {
                        if (user[o] === null) {
                            user[o] = "No Data";
                        }
                    }

                    if (user.dataRegistration[4].toString().length === 1) {
                        user.dataRegistration[4] = '0' + user.dataRegistration[4];
                    }

                    let usrDataRegistration = user.dataRegistration[2]
                        + '/' + user.dataRegistration[1]
                        + '/' + user.dataRegistration[0]
                        + ' ' + user.dataRegistration[3]
                        + ':' + user.dataRegistration[4];

                    //присоединяем к полям таблицы данными из JSON
                    tdId.appendChild(document.createTextNode(user.id));
                    tdEmail.appendChild(document.createTextNode(user.email));
                    tdUserName.appendChild(document.createTextNode(user.firstName));
                    tdLastName.appendChild(document.createTextNode(user.lastName));
                    tdPhone.appendChild(document.createTextNode(user.phone));
                    tdDataReg.appendChild(document.createTextNode(usrDataRegistration));
                    tdEnabled.appendChild(document.createTextNode(user.enabled));
                    tdRoles.appendChild(document.createTextNode(userRoles));

                    // присоединяем поля к строчке
                    tr.appendChild(tdId);
                    tr.appendChild(tdEmail);
                    tr.appendChild(tdUserName);
                    tr.appendChild(tdLastName);
                    tr.appendChild(tdPhone);
                    tr.appendChild(tdDataReg);
                    tr.appendChild(tdEnabled);
                    tr.appendChild(tdRoles);

                    userIdForDelete = "fillingModalFormDelete" + "(" + user.id + ")";
                    userIdForUpdate = "fillingModalFormUpdate" + "(" + user.id + ")";

                    buttonEdit.setAttribute('id', "updateButton");
                    buttonEdit.setAttribute('class', "btn btn-info");
                    buttonEdit.setAttribute('role', "button");
                    buttonEdit.setAttribute('data-toggle', "modal");
                    buttonEdit.setAttribute('data-target', "#updateModal");
                    buttonEdit.setAttribute('onclick', `${userIdForUpdate}`);
                    buttonEdit.appendChild(document.createTextNode($.i18n('button-edit')));

                    buttonDelete.setAttribute('id', "deleteButton");
                    buttonDelete.setAttribute('class', "btn btn-danger");
                    buttonDelete.setAttribute('role', "button");
                    buttonDelete.setAttribute('data-toggle', "modal");
                    buttonDelete.setAttribute('data-target', "#deleteModal");
                    buttonDelete.setAttribute('onclick', `${userIdForDelete}`);
                    buttonDelete.appendChild(document.createTextNode($.i18n('button-delete')));

                    tdEdit.appendChild(buttonEdit);
                    tdDelete.appendChild(buttonDelete);
                    tr.appendChild(tdEdit);
                    tr.appendChild(tdDelete);
                    adminUsersTable.append(tr);
                });
            }
        })
        .catch(error => {
            console.log(error);
        })
}

/*создание нового пользователя*/
async function newUser() {

    let roleSelectedValues = Array.from(elementCreateUserRoles.selectedOptions).map(el => el.value);
    let roleSelectedId = Array.from(elementCreateUserRoles.selectedOptions).map(el => el.id);
    let roleArray = convertToRoleSet(roleSelectedId, roleSelectedValues);

    let data = {

        email: $('#AdminPanelUserEmail').val(),
        password: $('#AdminPanelUserPassword').val(),
        firstName: $('#AdminPanelUserFirstName').val(),
        lastName: $('#AdminPanelUserLastName').val(),
        phone: $('#AdminPanelUserPhoneCP').val(),

        roles: roleArray

    };

    let userEmailFormData = document.forms["userCreationFormCP"]["userEmailCP"].value;
    let userPasswordFormData = document.forms["userCreationFormCP"]["userPasswordCP"].value;
    let userFirstNameFormData = document.forms["userCreationFormCP"]["userFirstNameCP"].value;
    let userLastNameFormData = document.forms["userCreationFormCP"]["userLastNameCP"].value;

    if (userEmailFormData !== "" &&
        (userPasswordFormData.length >= 6 && userPasswordFormData.length <= 60 && userPasswordFormData.match(/\s/) === null)
        && userFirstNameFormData !== "" && userLastNameFormData !== "" && roleArray.length !== 0) {
        const response = await fetch(createNewUser, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow',
            referrerPolicy: 'no-referrer',
            body: JSON.stringify(data)
        })
            .catch(error => {
                console.log(error);
            });

        clearTheValidateCreate();
        document.getElementById("createUserResult").innerText = "Successful Creation";
        console.log(data);
        clearTable();
        showAllUsersTable();
    } else {
        if (userEmailFormData === "") {
            document.getElementById("emailErrorsNU").innerText = "Empty field";
        } else {
            document.getElementById("emailErrorsNU").innerText = "";
        }
        if (userPasswordFormData.length < 6 || userPasswordFormData.length > 60) {
            document.getElementById("passwordErrorsNU").innerText = "Required between 6 and 60 symbols";
        } else {
            document.getElementById("passwordErrorsNU").innerText = "";
        }
        if (userPasswordFormData.match(/\s/) !== null) {
            document.getElementById("passwordSpaceNotAllow").innerText = "Space not allowed";
        } else {
            document.getElementById("passwordSpaceNotAllow").innerText = "";
        }
        if (userFirstNameFormData === "") {
            document.getElementById("firstnameErrorsNU").innerText = "Empty field";
        } else {
            document.getElementById("firstnameErrorsNU").innerText = "";
        }
        if (userLastNameFormData === "") {
            document.getElementById("lastnameErrorsNU").innerText = "Empty field";
        } else {
            document.getElementById("lastnameErrorsNU").innerText = "";
        }
        if (roleArray.length === 0) {
            document.getElementById("rolesErrorsNU").innerText = "Need to select role";
        } else {
            document.getElementById("rolesErrorsNU").innerText = "";
        }
        document.getElementById("createUserResult").innerText = "Creation failed";
    }
}

async function updateUsers(value) {

    let elementUpdateUserRoles = document.getElementById('userUpdRoles');

    let roleSelectedValues = Array.from(elementUpdateUserRoles.selectedOptions).map(el => el.value);
    let roleSelectedId = Array.from(elementUpdateUserRoles.selectedOptions).map(el => el.id);

    let roleArray = convertToRoleSet(roleSelectedId, roleSelectedValues);

    let data = {

        id: $('#updUserID').val(),
        firstName: $('#updUserName').val(),
        lastName: $('#updUserLastName').val(),
        email: $('#updUserEmail').val(),
        password: $('#updUserPassword').val(),
        phone: $('#updUserPhone').val(),

        roles: roleArray

    };


    let userEmailFormData = document.forms["userUpdateFormCP"]["email"].value;
    let userPasswordFormData = document.forms["userUpdateFormCP"]["password"].value;
    let userFirstNameFormData = document.forms["userUpdateFormCP"]["name"].value;
    let userLastNameFormData = document.forms["userUpdateFormCP"]["surname"].value;

    if (userEmailFormData !== "" &&
        (userPasswordFormData.length >= 6 && userPasswordFormData.length <= 60 && userPasswordFormData.match(/\s/) === null)
        && userFirstNameFormData !== "" && userLastNameFormData !== "" && roleArray.length !== 0) {

        const response = await fetch(updateUser, {
            method: 'PUT',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/json'
            },
            redirect: 'follow',
            referrerPolicy: 'no-referrer',
            body: JSON.stringify(data)
        });

        clearTheValidateUpdate();
        document.getElementById("updateUserResult").innerText = "User updated";

        clearTable();
        showAllUsersTable();

    } else {
        if (userEmailFormData === "") {
            document.getElementById("emailErrorsUS").innerText = "Empty field";
        } else {
            document.getElementById("emailErrorsUS").innerText = "";
        }
        if (userPasswordFormData.length < 6 || userPasswordFormData.length > 60) {
            document.getElementById("passwordErrorsUS").innerText = "Required between 6 and 60 symbols";
        } else {
            document.getElementById("passwordErrorsUS").innerText = "";
        }
        if (userPasswordFormData.match(/\s/) !== null) {
            document.getElementById("passwordSpaceNotAllowUS").innerText = "Space not allowed";
        } else {
            document.getElementById("passwordSpaceNotAllowUS").innerText = "";
        }
        if (userFirstNameFormData === "") {
            document.getElementById("firstnameErrorsUS").innerText = "Empty field";
        } else {
            document.getElementById("firstnameErrorsUS").innerText = "";
        }
        if (userLastNameFormData === "") {
            document.getElementById("lastnameErrorsUS").innerText = "Empty field";
        } else {
            document.getElementById("lastnameErrorsUS").innerText = "";
        }
        if (roleArray.length === 0) {
            document.getElementById("rolesErrorsUS").innerText = "Need to select role";
        } else {
            document.getElementById("rolesErrorsUS").innerText = "";
        }
        document.getElementById("updateUserResult").innerText = "Update failed";
    }

}

/*заполнение модальной формы на удаление*/
function fillingModalFormDelete(id) {

    let deleteButtonInModal = document.createElement('button');
    let userIdForDeleteButton = "deleteData" + "(" + id + ")";

    deleteButtonInModal.setAttribute('type', "button");
    deleteButtonInModal.setAttribute('id', "delButtInModal");
    deleteButtonInModal.setAttribute('class', "btn btn-danger");
    deleteButtonInModal.setAttribute('data-dismiss', "modal");
    deleteButtonInModal.setAttribute('onclick', `${userIdForDeleteButton}`);
    deleteButtonInModal.appendChild(document.createTextNode($.i18n('button-delete')));

    deleteButtonInModalForm.append(deleteButtonInModal);

    fetch(getUserById + "/" + id).then(function (response) {
        response.json().then(function (data) {

            const userRoles = data.data.roles.map(role => {
                return role.name;
            }).join(", ");

            if (data.data.dataRegistration[4].toString().length === 1) {
                data.data.dataRegistration[4] = '0' + data.data.dataRegistration[4];
            }

            let usrDataRegistration = data.data.dataRegistration[2]
                + '/' + data.data.dataRegistration[1]
                + '/' + data.data.dataRegistration[0]
                + ' ' + data.data.dataRegistration[3]
                + ':' + data.data.dataRegistration[4];

            $('#delUserID').val(id);
            $('#delUserName').val(data.data.firstName);
            $('#delUserDataReg').val(usrDataRegistration);
            $('#delUserEmail').val(data.data.email);
            $('#delUserRoles').val(userRoles);
        });
    });

}

//Удаление пользователя

async function deleteData(value) {

    await fetch(deleteUserById + "/" + value, {
        method: 'DELETE',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
            'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
    });

    document.getElementById('delButtInModal').remove();
    clearTable();
    showAllUsersTable()
}

/*заполнение модальной формы на редактирование*/
function fillingModalFormUpdate(id) {

    let updateButtonInModal = document.createElement('button');
    let userIdForUpdateButton = "updateUsers" + "(" + id + ")";

    updateButtonInModal.setAttribute('type', "button");
    updateButtonInModal.setAttribute('id', "updButtInModal");
    updateButtonInModal.setAttribute('class', "btn btn-success");
    updateButtonInModal.setAttribute('onclick', `${userIdForUpdateButton}`);
    updateButtonInModal.appendChild(document.createTextNode($.i18n('button-save')));

    saveButtonInModalForm.append(updateButtonInModal);

    fetch(getUserById + "/" + id).then(function (response) {
        response.json().then(function (data) {

            if (data.data.dataRegistration[4].toString().length === 1) {
                data.data.dataRegistration[4] = '0' + data.data.dataRegistration[4];
            }

            let usrDataRegistration = data.data.dataRegistration[2]
                + '/' + data.data.dataRegistration[1]
                + '/' + data.data.dataRegistration[0]
                + ' ' + data.data.dataRegistration[3]
                + ':' + data.data.dataRegistration[4];

            $('#updUserID').val(id);
            $('#updUserName').val(data.data.firstName);
            $('#updUserLastName').val(data.data.lastName);
            $('#updUserEmail').val(data.data.email);
            $('#updUserPhone').val(data.data.phone);
            $('#updUserDataReg').val(usrDataRegistration);

        });
    });
}

// ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ НА ON.CLICK

elementCreateUser.onclick = function () {
    newUser();
};

elementCreateNewUserHref.onclick = function () {
    $('#newUserModal').modal('show');
    document.getElementById('AdminPanelUserEmail').value = '';
    document.getElementById('AdminPanelUserPassword').value = '';
    document.getElementById('AdminPanelUserFirstName').value = '';
    document.getElementById('AdminPanelUserLastName').value = '';
    document.getElementById('adminPanelUserPhone').value = '';
};

//Сокрытие информации о создании нового пользователя
elementUserTable.onclick = function () {
    clearTable();
    showAllUsersTable();
};

/*создаем массив из значений полученных с селектора при создании нового пользователя*/
function convertToRoleSet(roleId, roleName) {
let roleArray = [];

    for(let index = 0; index < roleId.length; ++index) {
        roleArray.unshift({id: roleId[index], name: roleName[index]})
    }

    return roleArray;
}


//Очистка таблиц при закрытии модального окна
function clearTable() {
    while (document.getElementById("userDataTable") != null) {
        document.getElementById("userDataTable").remove();
    }
}

/*необходимы для избежания дублирования кнопок при закрытии модального окна*/
elementCloseDeleteModal1.onclick = function () {
    document.getElementById('delButtInModal').remove();
};

elementCloseDeleteModal2.onclick = function () {
    document.getElementById('delButtInModal').remove();
};

elementCloseUpdateModal1.onclick = function () {
    document.getElementById('updButtInModal').remove();
    clearTheValidateUpdate();
};

elementCloseUpdateModal2.onclick = function () {
    document.getElementById('updButtInModal').remove();
    clearTheValidateUpdate();
};

elementCloseCreateNewUserModal.onclick = function () {
    clearTheValidateCreate();
};

function clearTheValidateUpdate() {

    document.getElementById("emailErrorsUS").innerText = "";
    document.getElementById("passwordErrorsUS").innerText = "";
    document.getElementById("passwordSpaceNotAllowUS").innerText = "";
    document.getElementById("firstnameErrorsUS").innerText = "";
    document.getElementById("rolesErrorsUS").innerText = "";
    document.getElementById("updateUserResult").innerText = "";
    document.getElementById("lastnameErrorsUS").innerText = "";

}

function clearTheValidateCreate() {

    document.getElementById("createUserResult").innerText = "";
    document.getElementById("emailErrorsNU").innerText = "";
    document.getElementById("passwordErrorsNU").innerText = "";
    document.getElementById("passwordSpaceNotAllow").innerText = "";
    document.getElementById("firstnameErrorsNU").innerText = "";
    document.getElementById("lastnameErrorsNU").innerText = "";
    document.getElementById("rolesErrorsNU").innerText = "";
}

$('#categoryPanel span').on('click', function () {
    document.getElementById('nav-userlist').style.display = "none";
    document.getElementById('nav-analytics').style.display = "none";
    document.getElementById('nav-category').style.display = "block";
    document.getElementById('nav-userlist').className = "tab-pane fade";
    document.getElementById('nav-analytics').className = "tab-pane fade";
    document.getElementById('nav-category').className = "tab-pane fade active show";
});

$('#userTableAtAdminPanel span').on('click', function () {
    document.getElementById('nav-userlist').style.display = "block";
    document.getElementById('nav-category').style.display = "none";
    document.getElementById('nav-analytics').style.display = "none";
    document.getElementById('nav-category').className = "tab-pane fade";
    document.getElementById('nav-analytics').className = "tab-pane fade";
    document.getElementById('nav-userlist').className = "tab-pane fade active show";
});

$('#statisticPanel span').on('click', function () {
    document.getElementById('nav-userlist').style.display = "none";
    document.getElementById('nav-category').style.display = "none";
    document.getElementById('nav-analytics').style.display = "block";
    document.getElementById('nav-category').className = "tab-pane fade";
    document.getElementById('nav-analytics').className = "tab-pane fade active show";
    document.getElementById('nav-userlist').className = "tab-pane fade";
});