/*if ($("#reguserid").val()) {*/
        fetch(`/api/favorite/get`)
        .then(res => res.json())  /*res.json = data*/
        .then(data => renderUserData(data.data))

function check() {
    let cbx = document.getElementById("render").getElementsByClassName("custom-checkbox");
    if (cbx != null) {
        for (let i = 0; i < cbx.length; i++) {
            if (cbx[i].type == "checkbox" && cbx[i].checked) {
                fetch(`/api/favorite/delete/`+cbx[i].value, {
                    method: 'DELETE',
                })
                $("#delete" + cbx[i].value).hide()
                $("#add" + cbx[i].value).show()
                $("#delete" + cbx[i].value +" input").hide()
                $("#add" + cbx[i].value +" input").show()
            }
        }
    }
}
        document.getElementById('render').addEventListener("click", (event) => {
            if (event.target.id === "butt") {
                check();
            }
        })
        function renderUserData(userdata) {


            document.getElementById('render').innerHTML += `<input id="butt"  type="button" value="Удалить">`
            for (let step = 0; step < userdata.length; step++){
                let resdata = userdata[step];
                fetch(`/api/posting/`)
                    .then(res => res.json())
                    .then(data => renderPosting(data.data))
                function renderPosting(posting) {
                    for (let step = 0; step < posting.length; step++) {
                        let postingdata = posting[step];

                         if (postingdata.id === resdata) {

                             document.getElementById('render').innerHTML +=
                            `<div class="favorite-container">
                                <div class="photo">
                                    <div id="flag">
                                        <input class="custom-checkbox" type="checkbox" value=${postingdata.id}>
                                    </div>
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
                                        <strong p class="card-text top">${postingdata.price} ₽</p></strong>
                                        <p class="card-text top text-size">${postingdata.description}</p>
                                        <p class="card-text top text-size">${postingdata.contact}</p>
                                    </div>
                                </div>
                                 <div class="button-responsive">
                                 <div id="delete${postingdata.id}">
                                    <input data-id="${postingdata.id}" class="deleteWish" value="Удалить" type="button"/>
                                 </div>
                                 <div id="add${postingdata.id}">
                                    <input data-id="${postingdata.id}" class="addToWish" value="Добавить" type="button"/>
                                 </div>
                                 </div>
                            </div>`

                             $("#add" + postingdata.id).hide()
                             $("#delete" + postingdata.id).show()
                             $("#add" + postingdata.id +" input").hide()
                             $("#delete" + postingdata.id +" input").show()

                             $(".addToWish").on('click', function (event) {
                                 event.preventDefault();
                                 let postingId = this.dataset.id;
                                 $("#add" + postingId).hide()
                                 $("#delete" + postingId).show()
                                 $("#add" + postingId +" input").hide()
                                 $("#delete" + postingId +" input").show()
                                 fetch(`/api/favorite/add/${postingId}`, {
                                     method: 'POST',
                                 })
                             });

                             $('.deleteWish').on('click', function (event) {
                                 event.preventDefault();
                                 let postingId = this.dataset.id;
                                 $("#delete" + postingId).hide()
                                 $("#add" + postingId).show()
                                 $("#delete" + postingId +" input").hide()
                                 $("#add" + postingId +" input").show()
                                 fetch(`/api/favorite/delete/${postingId}`, {
                                     method: 'DELETE',
                                 })
                             })
                             }
                         }
                    }
                }
            }