let usersProfileMessages = $('#userMess tbody');

$(document).ready(function () {
    getAllUserMess();
});

function getAllUserMess() {
    let user_id = document.getElementById('profile-user-id').textContent;
    profileService.findMessageByUserId(user_id).then((response => {

        let activeCounter = 0;
        response.data.map(mess => {

            for (let o in mess) {
                let divSlider = document.createElement('div');
                let divInnerSlider = document.createElement('div')
                let olIndicSlider = document.createElement('ol')
                let li = document.createElement('li');
                let tr = document.createElement('tr');
                let trPrice = document.createElement('tr');
                let trMeeting = document.createElement('tr');
                let td = document.createElement('td');
                let tdTitle = document.createElement('td');
                let tdPrice = document.createElement('td');
                let tdMeeting = document.createElement('td');
                let tdTitleHref = document.createElement('a');
                let title = document.createTextNode(mess.postingAuthorFirstName + ' ' + mess.postingAuthorLastName);
                let price = document.createTextNode(mess.postingTitle + ' - ' +  mess.postingPrice  + ' ₽');
                let meeting = document.createTextNode(mess.messageText);
                let divImg = document.createElement('div');
                let imgName = document.createElement('img');

                divSlider.setAttribute('id', 'userImageSlider' + activeCounter);
                divSlider.setAttribute('class', 'carousel slide');
                divSlider.setAttribute('data-interval', 'false');
                divSlider.setAttribute('style', 'height: 150px; width: 210px; margin-right: 15px;');
                divInnerSlider.setAttribute('id', 'userCarouselInner' + activeCounter);
                divInnerSlider.setAttribute('class', 'carousel-inner');
                olIndicSlider.setAttribute('id', 'userCarousel-indic' + activeCounter);
                olIndicSlider.setAttribute('class', 'carousel-indicators');
                olIndicSlider.setAttribute('style', 'margin:0;');
                li.setAttribute('class', 'dropdown-divider');
                li.setAttribute('width', '500');
                imgName.setAttribute('src', 'http://s2.ucoz.net/sm/3/eyes.gif');

                tr.setAttribute('id', "userPosting");
                td.setAttribute('rowspan', "3");
                td.setAttribute('class', "first");
                td.setAttribute('width', '90');
                tdTitleHref.setAttribute('class', 'text-primary');
                tdTitleHref.setAttribute('href', '#');
                tdTitleHref.appendChild(title);

                divSlider.appendChild(divInnerSlider);
                divSlider.appendChild(olIndicSlider);
                // td.appendChild(divSlider);

                tdTitle.appendChild(tdTitleHref);
                tdPrice.appendChild(price);
                tdMeeting.appendChild(meeting);
                tr.appendChild(td);
                tr.appendChild(tdTitle);
                trPrice.appendChild(tdPrice);
                trMeeting.appendChild(tdMeeting);

                let liIndic = document.createElement('li');
                liIndic.setAttribute('data-slide-to', '' + 1);
                liIndic.setAttribute('style', 'border-bottom: 5px solid gray;margin:1px;height: 130px;\n' +
                        '    background: transparent;')

                let img = document.createElement('img');
                img.setAttribute('class', 'img-fluid');
                img.setAttribute('src', '/images/numbers/0.jpg');
                img.setAttribute('style', 'height: 70px; width: 70;');

                let divInner = document.createElement('div');
                divInner.appendChild(img);
                divImg.appendChild(img);
                td.appendChild(divImg);
                olIndicSlider.appendChild(liIndic)
                divInner.setAttribute('class', 'carousel-item');
                divInnerSlider.appendChild(divInner);

                usersProfileMessages.append(tr).append(trPrice).append(trMeeting).append(li);
                break;
            }
        });
    }));
}

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
};

const profileService = {
    findMessageByUserId: async (id) => {
        return await httpHeader.fetch('api/messages/' + id);
    }
};