let newNotesCount = 0;

$(document).ready(function () {
    return bellIconSet();
});

async function bellIconSet() {
    if ($("#reguserid").val()) {

        await fetch('/api/notification/count-map', {method: 'GET'})
            .then(response => response.json())
            .then(responseData => {
                newNotesCount = responseData.data[1];
            });

        if (newNotesCount !== 0) {
            document.getElementById('bell_icon').removeAttribute('class');
            document.getElementById('bell_icon').setAttribute('class', 'note_bell_active');
        }
    }
}