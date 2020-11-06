
submitUsedCarForm = function(){
    let formM = document.getElementById("used-car-posting-form");
    formM.submit();
    let posting = formM.attr('object');

    console.log("function is called");
    console.log(posting);

    alert("Saved!");
}

 window.onload = function() {
     $('#pathCategory').show();
     $('#visibleElement1').hide();
     $('#visibleElement2').show();
     $('#visibleElement3').show();
     initModelGetDataToModelSendData();
 }

initModelGetDataToModelSendData = function() {



}