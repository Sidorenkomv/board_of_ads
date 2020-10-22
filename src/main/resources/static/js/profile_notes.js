
let usersProfileNotifications = $('#userPo tbody');
let usersProfileArchivePostings = $('#userPoArch tbody');

$(document).ready(function () {
    getUserAllNotifications();
});

function getUserAllNotifications() {

    let user_id = document.getElementById('userpostid').textContent;
    console.log("userpostid = " + user_id);
    profileService.findPostingByUserId(user_id).then((response => {

        let activeCounter = 0;
        let archiveCounter = 0;
        let soldAmountCounter = 0;

        response.data.map(note => {

            for (let o in note) {

                let divSlider = document.createElement('div');
                let divInnerSlider = document.createElement('div')
                let olIndicSlider = document.createElement('ol')
                let li = document.createElement('li');
                let trHead = document.createElement('tr');
                let trBody= document.createElement('tr');
                let trInfo = document.createElement('tr');
                let td = document.createElement('td');
                let tdTitle = document.createElement('td');
                let tdBody = document.createElement('td');
                let tdClickAct = document.createElement('td');
                let tdClickActHref = document.createElement('a');

                let title = document.createTextNode(note.messageTitle);
                let body = document.createTextNode(note.messageBody);
                let clickAct = document.createTextNode(note.clickAction);

                li.setAttribute('class', 'dropdown-divider');
                li.setAttribute('width', '100%');

                trHead.setAttribute('id', "userPosting");

                td.setAttribute('rowspan', "3");
                td.setAttribute('class', "first");
                td.setAttribute('width', '210');

                // divSlider.appendChild(divInnerSlider);
                // divSlider.appendChild(olIndicSlider);
                // td.appendChild(divSlider);
                tdTitle.setAttribute('class', 'font-weight-bold');
                tdTitle.appendChild(title);
                tdBody.appendChild(body);
                trHead.appendChild(tdTitle);

                tdClickActHref.setAttribute('class', 'text-primary');
                tdClickActHref.setAttribute('href', '#');
                tdClickActHref.appendChild(clickAct);

                tdClickAct.appendChild(tdClickActHref);

                // trInfo.appendChild(td);
                // trInfo.appendChild(td);

                trBody.appendChild(tdBody);
                trInfo.appendChild(tdClickAct);
                usersProfileNotifications.append(trHead).append(trBody).append(trInfo).append(li);

                if (note.status === 'newSent') {
                    console.log('newSent count = ' + activeCounter);
                    activeCounter++;
                    usersProfileNotifications.append(trHead).append(trBody).append(trInfo).append(li);
                } else {
                    console.log('if not newSent....')
                    archiveCounter = archiveCounter + 1;
                 //   soldAmountCounter = post.price + soldAmountCounter;
                    console.log(soldAmountCounter);
                    usersProfileArchivePostings.append(trHead).append(trBody).append(trInfo).append(li);
                }




                break;
            }

        });

         document.getElementById('active-ads-tab').innerText = 'Новые ' + ' - ' + activeCounter;
         document.getElementById('archive-ads-tab').innerText = 'Прочитанные ' + ' - ' + archiveCounter;
        // document.getElementById('amountOfSales').innerHTML = soldAmountCounter + ' ₽';
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
    findPostingByUserId: async (id) => {
        return await httpHeader.fetch('api/notification/user/' + id);
    }
};

