let allNotesCount = 0;
let newNotesCount = 0;
let readNotesCount = 0;
const url = '/api/notification/';

$(document).ready(function () {
    return getUserAllNotifications();
});

async function getUserAllNotifications(qualifiedName) {
    document.getElementById('tbody_new').innerHTML = "";
    document.getElementById('tbody_read').innerHTML = "";

    await fetch(url, {method : "GET"})
        .then(response => response.json())
        .then((response => {
        if (response.data !== undefined) {
            response.data.map(note => {

                let messageTitle = document.createTextNode(note.messageTitle);
                let messageBody = document.createTextNode(note.messageBody);
                let clickAction = document.createTextNode(note.clickAction);
                let sentTime = document.createTextNode(note.sentTime);

                let notificationId = note.notificationId;
                let urgentLevel = note.urgentLevel;

                let trNoteBlock = document.createElement('tr');
                trNoteBlock.setAttribute('id', 'notificationBlock' + notificationId);

                let li = document.createElement('li');
                li.setAttribute('class', 'dropdown-divider');

                let trHead = document.createElement('tr');
                let trBody = document.createElement('tr');
                let trInfo = document.createElement('tr');
                let trFooter = document.createElement('tr');

                let tdTitle = document.createElement('div');
                tdTitle.setAttribute('class', 'font-weight-bold');

                let tdClickAct = document.createElement('td');
                let tdClickActHref = document.createElement('a');

                let tdNewBadge = document.createElement('div');

                trHead.appendChild(tdNewBadge);
                trHead.appendChild(tdTitle);
                trBody.appendChild(messageBody);

                tdClickActHref.setAttribute('class', 'text-primary');
                tdClickActHref.setAttribute('href', note.clickAction);
                tdClickActHref.appendChild(clickAction);

                tdClickAct.appendChild(tdClickActHref);

                let trForButtons = document.createElement('tr');
                let buttonDiv = document.createElement('div');
                trForButtons.appendChild(buttonDiv);

                if (note.status === 'newSent') {
                    let newSpan = document.createElement('span');
                    newSpan.setAttribute('class', 'badge badge-warning');
                    newSpan.innerText = 'Новое';
                    tdNewBadge.appendChild(newSpan);

                    if (urgentLevel === 2) {
                        let levelSpan = document.createElement('span');
                        levelSpan.setAttribute('class', 'badge badge-danger');
                        levelSpan.innerText = 'Важное!';
                        tdNewBadge.appendChild(levelSpan);
                    }
                    $("<button type='button' class='btn-sm btn-success' " +
                        "onclick='onChangeButt("+notificationId+")' id='button"+notificationId+"'></button>")
                        .text('Прочитано').appendTo(buttonDiv);

                } else {
                    $("<button type='button' class='btn-sm btn-danger' " +
                        "onclick='onDeleteButt("+notificationId+")' id='button"+notificationId+"'></button>")
                        .text('Удалить').appendTo(buttonDiv);
                }

                tdTitle.appendChild(messageTitle);
                trInfo.appendChild(tdClickAct);
                trFooter.appendChild(sentTime);

                trNoteBlock.appendChild(trHead);
                trNoteBlock.appendChild(trBody);
                trNoteBlock.appendChild(trInfo);
                trNoteBlock.appendChild(trFooter);
                trNoteBlock.appendChild(trForButtons);
                trNoteBlock.appendChild(li);

                if (note.status === 'newSent') {
                    $("#tbody_new").append(trNoteBlock);
                } else {
                    $("#tbody_read").append(trNoteBlock);
                }
            });
        }}));

    await fetch(url + 'count-map', {method: 'GET'})
        .then(response => response.json())
        .then(responseData => {
            allNotesCount = responseData.data[0];
            newNotesCount = responseData.data[1];
            readNotesCount = responseData.data[2];

            if (newNotesCount !== 0) {
                document.getElementById('bell_icon').removeAttribute('class');
                document.getElementById('bell_icon').setAttribute('class', 'note_bell_active');
            }

            document.getElementById('new-notes-tab').innerText = 'Новые ' + ' - ' + newNotesCount;
            document.getElementById('read-notes-tab').innerText = 'Прочитанные ' + ' - ' + readNotesCount;
            return true;
        });

    if (allNotesCount === 0 ) {
        return document.getElementById('allNotification').innerHTML = '' +
            '<div class="text-center align-middle">' +
            '   <img src="images/ikonka-uvedomlenij.png" class="align-self-center ">' +
            '   <div class="text-muted">' +
            '       <div>Нет уведомлений</div>' +
            '       <div>На этой странице будут собраны</div> ' +
            '       <div>все уведомления</div>' +
            '   </div> ' +
            '</div>';
    } else {
        if (newNotesCount === 0 ) {

             document.getElementById('tbody_new').innerHTML = '' +
                '<div class="text-center align-middle">' +
                '   <img src="images/ikonka-uvedomlenij.png" class="align-self-center ">' +
                '   <div class="text-muted">' +
                '       <div>Нет новых уведомлений</div>' +
                '       <div>На этой странице будут собраны </div>' +
                '       <div>новые уведомления</div>' +
                '   </div> ' +
                '</div>';

             document.getElementById('bell_icon').removeAttribute('class');
             document.getElementById('bell_icon').setAttribute('class', 'note_bell_inactive');
        }

        if (readNotesCount === 0 ) {
            return document.getElementById('tbody_read').innerHTML = '' +
                '<div class="text-center align-middle">' +
                '   <img src="images/ikonka-uvedomlenij.png" class="align-self-center ">' +
                '   <div class="text-muted">' +
                '       <div>Нет прочитанных уведомлений</div>' +
                '       <div>На этой странице будут собраны </div>' +
                '       <div>прочитанные уведомления</div>' +
                '   </div> ' +
                '</div>';

        }
    }
}

async function onChangeButt(noteId) {

    return await fetch(url + noteId, {method: 'PATCH'})
        .then(() => {
            return getUserAllNotifications();
        })
        .catch(err => console.log(err));
}

async function onDeleteButt(noteId) {

    return await fetch(url + noteId, {method: 'DELETE'})
        .then(() => {
            return getUserAllNotifications();
        })
        .catch(err => console.log(err));
}
