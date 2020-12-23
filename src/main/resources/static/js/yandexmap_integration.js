ymaps.ready(init);
let map;
let mapCenter;
let suggestView;
let pointerAdded = false;

function init() {

    $(document).on('click', '#inputAddress', function (e) {
        buildNewSuggestView(5);
    })

    $(document).on('blur', '#inputAddress', function () {
        if (!!suggestView) {
            $('#inputAddress').val(suggestView.state.get('items')[0].value);
            showAddressOnMap();
        }
    })

    //определяем геолокацию и строим карту по ней
    ymaps.geolocation.get({provider: 'yandex', mapStateAutoApply: true}).then(
        (res) => {
            buildMap(res.geoObjects.position)
        },
        (err) => {
            buildMap([55.76, 37.64]);
        })

    function buildMap(startPosition) {
        // создаем карту
        map = new ymaps.Map("default_map", {
            center: startPosition,
            zoom: 10,
            controls: ['zoomControl']
        });

        //добавляем прослушивания события "изменения границ карты"
        map.events.add('boundschange', currentLocationToAddressField);
        // Подключаем поисковые подсказки к полю ввода
        buildNewSuggestView(5);
    }

    function buildNewSuggestView(num) {
        //т.к. поле опций SuggestView нельзя изменить, для отключения/включения подсказок
        //пересоздаем объект с новыми опциями в конструкторе

        if (!!suggestView) {
            suggestView.destroy();
        }
        suggestView = new ymaps.SuggestView('inputAddress', {boundedBy: map.getBounds(), results: num});
        //добавляем прослушивание событие "выбор подсказки" и отображаем указатель
        suggestView.events.add('select', showAddressOnMap);
    }

    function currentLocationToAddressField(e) {
        mapCenter = e.get('newCenter');

        ymaps.geocode(mapCenter).then(function (res) {
            let firstGeoObject = res.geoObjects.get(0).getAddressLine();
            buildNewSuggestView(0);
            if (pointerAdded) {
                $('#inputAddress').val(firstGeoObject);
            }
        })
    }

    function showAddressOnMap() {
        let request = $('#inputAddress').val();

        ymaps.geocode(request).then(function (res) {
            let obj = res.geoObjects.get(0);
            let mapContainer = $('#default_map'),
                bounds = obj.properties.get('boundedBy'),
                mapState = ymaps.util.bounds.getCenterAndZoom(
                    bounds,
                    [mapContainer.width(), mapContainer.height()]
                );

            mapState.controls = [];
            map.setCenter(mapState.center, mapState.zoom);
        })
        addPointer();
    }

    function addPointer() {
        $('.map-pointer').css("display", "block");
        pointerAdded = true;
    }
}