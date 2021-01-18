$(document).ready(function () {
    $('#buttonAuth').on('click', async function () {
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
                    document.location.href = "/favorite";
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
    });
});