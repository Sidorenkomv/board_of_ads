let usersProfilePostings = $('#userPo tbody');
let usersProfileArchivePostings = $('#userPoArch tbody');
let usersProfilePostsBlock = $('#posts_block');
let usersProfilePostsContent = $('#profile-posts-content');
const userId = document.getElementById('profile-user-id').textContent;

$(document).ready(function () {
 getAllUserPosts();
});

function getAllUserPosts() {
    profileService.findPostingByUserId(userId).then((response => {

        let activeCounter = 0;
        let archiveCounter = 0;
        let soldAmountCounter = 0;

        response.data.map(post => {

            for (let o in post) {

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

                let title = document.createTextNode(post.title);
                let price = document.createTextNode(post.price + ' ₽');
                let meeting = document.createTextNode(post.meetingAddress);

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
                tr.setAttribute('id', "userPosting");
                td.setAttribute('rowspan', "3");
                td.setAttribute('class', "first");
                td.setAttribute('width', '210');
                tdTitleHref.setAttribute('class', 'text-primary');
                tdTitleHref.setAttribute('href', '#');
                tdTitleHref.appendChild(title);

                divSlider.appendChild(divInnerSlider);
                divSlider.appendChild(olIndicSlider);
                td.appendChild(divSlider);
                tdTitle.appendChild(tdTitleHref);
                tdPrice.appendChild(price);
                tdMeeting.appendChild(meeting);
                tr.appendChild(td);
                tr.appendChild(tdTitle);
                trPrice.appendChild(tdPrice);
                trMeeting.appendChild(tdMeeting);

                console.log(post.images.length)
                if (post.images.length > 0) {
                    for (let i = 0; i < post.images.length; i++) {

                        let liIndic = document.createElement('li');
                        liIndic.setAttribute('data-target', '#userImageSlider' + activeCounter)
                        liIndic.setAttribute('data-slide-to', '' + i);
                        liIndic.setAttribute('style', 'border-bottom: 5px solid gray;margin:1px;height: 130px;\n' +
                            '    background: transparent;')

                        let img = document.createElement('img');
                        img.setAttribute('class', 'img-fluid');
                        img.setAttribute('src', '' + post.images[i].pathURL);
                        img.setAttribute('style', 'height: 150px; width: 210px;');

                        let divInner = document.createElement('div');
                        divInner.appendChild(img);

                        if (i === 0) {

                            liIndic.setAttribute('class', 'active');
                            olIndicSlider.appendChild(liIndic)

                            divInner.setAttribute('class', 'carousel-item active');
                            divInnerSlider.appendChild(divInner);

                        } else {

                            olIndicSlider.appendChild(liIndic)

                            divInner.setAttribute('class', 'carousel-item');
                            divInnerSlider.appendChild(divInner);

                        }
                    }
                } else {

                    let img = document.createElement('img');
                    img.setAttribute('class', 'img-fluid');
                    img.setAttribute('src', '../images/empty_image.jpg');
                    img.setAttribute('style', 'height: 150px; width: 210px;');

                    let divInner = document.createElement('div');
                    divInner.setAttribute('class', 'carousel-item active');
                    divInner.appendChild(img);
                    divInnerSlider.appendChild(divInner);

                }

                if (post.isActive) {
                    activeCounter = activeCounter + 1;
                    usersProfilePostings.append(tr).append(trPrice).append(trMeeting).append(li);
                } else {
                    archiveCounter = archiveCounter + 1;
                    soldAmountCounter = post.price + soldAmountCounter;
                    console.log(soldAmountCounter);
                    usersProfileArchivePostings.append(tr).append(trPrice).append(trMeeting).append(li);
                }
                break;
            }

        });
        document.getElementById('active-ads-tab').innerText = 'Активные ' + ' - ' + activeCounter;
        document.getElementById('archive-ads-tab').innerText = 'Архивные ' + ' - ' + archiveCounter;
        document.getElementById('amountOfSales').innerHTML = soldAmountCounter + ' ₽';
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
        return await httpHeader.fetch('api/posting/userpost/' + id);
    }
};

