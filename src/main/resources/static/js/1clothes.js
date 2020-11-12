let frontName;

function showForm(fName) {

    frontName = fName;

    document.getElementById('visibleElement2').innerHTML =
        '<form action="#">\n' +
        '  <label for="size">Size:</label><br>\n' +
        '  <input type="text" id="size" name="size" ><br>\n' +
        '  <label for="condition">Condition:</label><br>\n' +
        '  <input type="text" id="condition" name="condition"><br><br>\n' +
        '  <input type="hidden" name="frontName" value=' + frontName + '>' +
        '</form> '

}


let btn = document.getElementById("saveButton");
btn.addEventListener("click", () => {

    let posting = {
        size: $('#size').val(),
        condition: $('#condition').val(),
        meetingAddress: $('#inputPhone').val(),
        contact: $('#inputPhone').val()

    };

    fetch('/api/posting/clothes/' + frontName, {

        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(posting)
    });

})

