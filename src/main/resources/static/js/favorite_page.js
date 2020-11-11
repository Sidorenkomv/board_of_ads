    if ($("#reguserid").val()) {

        fetch(`/api/favorite/userid/` + $("#reguserid").val())
        .then(res => res.json())
        .then(data => renderUserData(data.data))

        function renderUserData(userdata) {

            for (let step = 0; step < userdata.length; step++){
                let resdata = userdata[step];

                fetch(`/api/posting/`)
                    .then(res => res.json())
                    .then(data => renderPosting(data.data))

                function renderPosting(posting) {
                    for (let step = 0; step < posting.length; step++) {
                        let postingdata = posting[step];


                         if (postingdata.id === resdata.posting) {

                             document.getElementById('render').innerHTML +=
            `<div class="favorite-container">
                <div class="photo">
                    <div id="add${postingdata.id}">
                        <img data-id="${postingdata.id}" class="addToWish" src="../images/heart.jpg" th:src="@{images/heart.jpg}">
                    </div>
                    <div id="delete${postingdata.id}">
                        <img data-id="${postingdata.id}" class="deleteWish" src="../images/heart2.jpg" th:src="@{images/heart2.jpg}">
                    </div> 
                </div>
                <div class="desc card-body">
                    <div>
                        <h5 class="card-title top"><a href="">${postingdata.title}</a></h5>
                        <p class="card-text top">${postingdata.price} p</p>
                        <p class="card-text top text-size">${postingdata.description}</p>
                        <p class="card-text top text-size">${postingdata.contact}</p>
                    </div>
                </div>
                <div class="buttons"></div>
            </div>`


                             $("#add" + postingdata.id).hide()
                             $("#delete" + postingdata.id).show()

                             $(".addToWish").on('click', function (event) {
                                 event.preventDefault();

                                 let postingId = this.dataset.id;

                                 $("#add" + postingId).hide()
                                 $("#delete" + postingId).show()
                             });


                             $('.deleteWish').on('click', function (event) {

                                 event.preventDefault();

                                 let postingId = this.dataset.id;

                                 $("#delete" + postingId).hide()
                                 $("#add" + postingId).show()

                                 fetch(`/api/favorite/delete/${postingId}`, {
                                     method: 'DELETE',
                                 })
                             })


                         }
                    }
                }
            }
        }

    } else {

        fetch(`/api/favorite/userip/`)
            .then(res => res.json())
            .then(data => renderUserData(data.data))

        function renderUserData(userdata) {

            for (let step = 0; step < userdata.length; step++){
                let resdata = userdata[step];

                fetch(`/api/posting/`)
                    .then(res => res.json())
                    .then(data => renderPosting(data.data))

                function renderPosting(posting) {
                    for (let step = 0; step < posting.length; step++) {
                        let postingdata = posting[step];


                        if (postingdata.id === resdata.posting) {

                            document.getElementById('render').innerHTML +=
                                `<div class="favorite-container">
                <div class="photo">
                    <div id="add${postingdata.id}">
                        <img data-id="${postingdata.id}" class="addToWish" src="../images/heart.jpg" th:src="@{images/heart.jpg}">
                    </div>
                    <div id="delete${postingdata.id}">
                        <img data-id="${postingdata.id}" class="deleteWish" src="../images/heart2.jpg" th:src="@{images/heart2.jpg}">
                    </div> 
                </div>
                <div class="desc card-body">
                    <div>
                        <h5 class="card-title"><a href="">${postingdata.title}</a></h5>
                        <p class="card-text">${postingdata.price} p</p>
                        <p class="card-text top">${postingdata.description}</p>
                        <p class="card-text top">${postingdata.contact}</p>
                    </div>
                </div>
                <div class="buttons"></div>
            </div>`


                            $("#add" + postingdata.id).hide()
                            $("#delete" + postingdata.id).show()

                            $(".addToWish").on('click', function (event) {
                                event.preventDefault();

                                let postingId = this.dataset.id;

                                $("#add" + postingId).hide()
                                $("#delete" + postingId).show()
                            });


                            $('.deleteWish').on('click', function (event) {

                                event.preventDefault();

                                let postingId = this.dataset.id;

                                $("#delete" + postingId).hide()
                                $("#add" + postingId).show()

                                fetch(`/api/favorite/delete/${postingId}`, {
                                    method: 'DELETE',
                                })
                            })

                        }
                    }
                }
            }
        }
    }