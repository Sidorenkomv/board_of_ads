let newUserUrl = 'http://localhost:5556/api/user/modal-reg';
let elementCreateUser = document.getElementById('createUser');

$(document).ready(function () {

});

elementCreateUser.onclick = function () {
    regNewUser();
};

async function regNewUser() {
    let data = {

        firstName: $('#userNameReg').val(),
        password: $('#userPasswordReg').val(),
        email: $('#userEmailReg').val(),

    };

    let userFirstNameFormData = document.getElementById('userNameReg').value;
    let userEmailFormData = document.getElementById('userEmailReg').value;
    let userPasswordFormData = document.getElementById('userPasswordReg').value;


    if (userEmailFormData !== "" &&
        (userPasswordFormData.length >= 6 && userPasswordFormData.length <= 60
            && userPasswordFormData.match(/\s/) === null)
        && userFirstNameFormData !== "") {
        const response = await fetch(newUserUrl, {
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
            }).then(response => {
                if (response.status === 200) {
                    location.reload();
                }
            });
        return response.json();
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
        document.getElementById("createUserResult").innerText = "Creation failed";
    }


}