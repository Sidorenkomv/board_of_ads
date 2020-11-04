let usersProfilePostsContent = $('#profile-posts-content');

let allNotesCount = 0;
let newNotesCount = 0;
let readNotesCount = 0;

const usersProfileNewNotifications = $("#tbody_new");
const usersProfileReadNotifications = $("#tbody_read");
const userId = document.getElementById('profile-user-id').textContent;
const requestURL = '/api/notification/user/';

const valueNoteBell = document.getElementsByClassName("note_bell");

$(document).ready(function () {
    getUserAllNotifications();
});

const httpHeader = {
    fetch: async function (url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });
        return response.json();
    }
}

function getUserAllNotifications() {
    userHasAnyNotes();
    profileNotesService.findNotificationsByUserId(userId).then((response => {
        response.data.map(note => {

            for (let o in note) {
                let messageTitle = document.createTextNode(note.messageTitle);
                let messageBody = document.createTextNode(note.messageBody);
                let clickAction = document.createTextNode(note.clickAction);
                let sentTime = document.createTextNode(note.sentTime);

                let acceptNumber = note.acceptNumber;
                let notificationId = note.notificationId;
                let userIdFromRequest = note.userId;
                let status  = note.status;
                let urgentLevel = note.urgentLevel;

                let trNoteBlock = document.createElement('tr');
                trNoteBlock.setAttribute('id', 'notification-block-' + notificationId);

                let li = document.createElement('li');
                li.setAttribute('class', 'dropdown-divider');

                let trHead = document.createElement('tr');
                let trBody= document.createElement('tr');
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
                    let readButtonVsId = $("<button type='button' class='btn-sm btn-outline-info' " +
                        "onclick='onChangeButt(value)' " +
                        "id='readButton' name='id' value='1'></button>").text('Прочел').appendTo(buttonDiv);
                    readButtonVsId.attr('value', notificationId);
                }
                else {
                    let deleteButtonVsId = $("<button type='button' class='btn-sm btn-outline-info' " +
                        "onclick='onDeleteButt(value)'   " +
                        "id='deleteButton' name='id' value='1'></button>").text('Удали').appendTo(buttonDiv);
                    deleteButtonVsId.attr('value', notificationId);
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
                    usersProfileNewNotifications.append(trNoteBlock);
                } else {
                    usersProfileReadNotifications.append(trNoteBlock);
                }
                break;
            }
        });
    }));
}

const profileNotesService = {
    findNotificationsByUserId: async (id) => {
        return await httpHeader.fetch(requestURL + id);
    }
}

function userHasAnyNotes() {
    getResData(userId).then((responseData) => {
        let ncm = responseData.data;
        allNotesCount = ncm[0];
        newNotesCount = ncm[1];
        readNotesCount = ncm[2];
        console.log('Checking count ....... ');
        document.getElementById('new-notes-tab').innerText = 'Новые ' + ' - ' + newNotesCount;
        document.getElementById('read-notes-tab').innerText = 'Прочитанные ' + ' - ' + readNotesCount;
        if (newNotesCount === 0 ) {valueNoteBell.style.backgroundColor = 'black'; }
    })

function getResData() {
        return fetch(requestURL + userId + '/count-map', {method: 'GET'})
            .then(response => response.json())
            .then(data => {
                return data;
            })
    }
}

function onChangeButt(noteId) {
    changeStatusToRead(userId, noteId);
    userHasAnyNotes();
}

function changeStatusToRead(userId, noteId) {
    let url = requestURL + userId + '/' + noteId + '/change-status-to-read';
    sendRequest('PATCH', url)
        .then(() => {
                console.log('User ' + userId + ' note ' + noteId +' is changed')
                let deleteBlock = "#notification-block-" + noteId;
                $(deleteBlock).remove();
            }
        )
        .catch(err => console.log(err))

    async function sendRequest(method, url) {
        fetch(url, {
            method: method
        })
            .then((response) => {
                return response;
            })
    }
}

function onDeleteButt(noteId) {
    deleteNoteFromUserFunction(userId, noteId);
    userHasAnyNotes();
}

function deleteNoteFromUserFunction(userId, noteId) {
    let url = requestURL + userId + '/' + noteId + '/delete';
    sendRequest('DELETE', url)
        .then(() => {
                console.log('User ' + userId + ' note ' + noteId +' is deleted')
                let deleteBlock = "#notification-block-" + noteId;
                $(deleteBlock).remove();

            }
        )
        .catch(err => console.log(err))

    async function sendRequest(method, url) {
        fetch(url, {
            method: method
        })
            .then((response) => {
                return response;
            })
    }
}
