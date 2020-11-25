const meta = {
    body: $('#analyticsContent'),
    modalTitle: $('.modal-title'),
    report: $('#reportrange > span'),
    reportrange: $('#reportrange'),
    getCalendar: getCalendar,
    initCalendar: initCalendar,
    drawBody: drawBody,
    btn: $('#createAnalytics'),
};

const analyticsElements = {
    region: {
        url: '/api/region/date',
        element: $('#region-analytics'),
        title: "Отчет по регионам",
        columns: [
            {title: "Регион", name: "region"},
            {title: "Количество постов", name: "postsCount"},
            {title: "Активные посты", name: "activePostsCount"},
            {title: "Архивные посты", name: "archivePostsCount"},
        ],
    },

    user: {
        url: '/api/posting/date',
        element: $('#user-analytics'),
        title: "Отчет по пользователям",
        columns: [
            {title: "Почта", name: "userEmail"},
            {title: "Количество постов", name: "allUserPosts"},
            {title: "Активные посты", name: "activeUserPosts"},
            {title: "Архивные посты", name: "archiveUserPosts"}
        ],
    },

    city: {
        url: '/api/city/date',
        element: $('#city-analytics'),
        title: "Отчет по городам",
        columns: [
            {title: "Город", name: "city"},
            {title: "Количество постов", name: "postsCount"},
            {title: "Активные посты", name: "activePostsCount"},
            {title: "Архивные посты", name: "archivePostsCount"}
        ],
    },

    category: {
        url: '/api/category/date',
        element: $('#category-analytics'),
        title: "Отчет по категориям",
        columns: [
            {title: "Категория", name: "category"},
            {title: "Количество постов", name: "postsCount"},
            {title: "Активные посты", name: "activePostsCount"},
            {title: "Архивные посты", name: "archivePostsCount"}
        ],
    },
};

for (let key in analyticsElements) {
    analyticsElements[key].element.click(function () {
        meta.body.empty();
        meta.initCalendar();
        meta.modalTitle.text(analyticsElements[key].title);
        meta.btn.off();
        meta.btn.click(() => meta.drawBody(analyticsElements[key]));
    });
}

async function drawBody(analyticElem) {
    let date = meta.report.text();
    const request = await fetch(analyticElem.url, {
        method: 'POST',
        body: JSON.stringify(date)
    });
    const response = await request.json();
    if (response.success && Object.keys(response.data).length !== 0) {
        let counter = 0;

        // CREATE HEADER
        let tr = document.createElement("thead");
        let num = document.createElement("th");
        num.appendChild(document.createTextNode("№"));
        tr.appendChild(num);
        analyticElem.columns.forEach((x) => {
            let th = document.createElement("th");
            th.appendChild(document.createTextNode(x.title));
            tr.appendChild(th);
        });
        meta.body.append(tr);

        // FILL TABLE
        for (let el in response.data) {
            counter++;
            let tr = document.createElement("tr");
            let num = document.createElement("td");
            num.appendChild(document.createTextNode(counter.toString()));
            tr.appendChild(num);
            analyticElem.columns.forEach(x => {
                let td = document.createElement("td");
                let text = document.createTextNode(response.data[el][x.name]);
                td.appendChild(text);
                tr.appendChild(td);
            });
            meta.body.append(tr);
        }
    } else {
        let span = document.createElement('span');
        let empty = document.createTextNode("За указанные даты посты отсутствуют");
        span.appendChild(empty);
        meta.body.append(span);
    }
}

function initCalendar() {
    const start = moment().subtract(29, 'days');
    const end = moment();

    function cb(start, end) {
        meta.report.html(start.format('DD/MM/YYYY') + ' - ' + end.format('DD/MM/YYYY'));
    }

    meta.reportrange.daterangepicker(meta.getCalendar(), cb);
    cb(start, end);
}

function getCalendar() {
    return {
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }
}
