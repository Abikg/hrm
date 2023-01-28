document.addEventListener("DOMContentLoaded", function () {
    var url = "/attendance/filter?page=0";
    getAttendanceData(url);
})

function paginationNormal(page) {
    var url;
    if ($('#noDateFilterFlag').val() == "true") {
        url = "/attendance/filter?page=" + page;
    } else if ($('#noDateFilterFlag').val() == "false") {
        url = "/attendance/filter?page=" + page + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
    }
    getAttendanceData(url);
}

function paginationAdmin(page) {

    let userId = $("#select-user").find(":selected").val();
    if (userId == "ALL") {
        if ($('#noDateFilterFlag').val() == "true") {

            var url = "/attendance/filter?page=" + page;

        } else if ($('#noDateFilterFlag').val() == "false") {

            var url = "/attendance/filter?page=" + page + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        }

    } else {
        if ($('#noDateFilterFlag').val() == "true") {

            var url = "/attendance/filter?page=" + page + "&id=" + userId;

        } else if ($('#noDateFilterFlag').val() == "false") {
            var url = "/attendance/filter?page=" + page + "&id=" + userId + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        }
    }

    getAttendanceData(url);
}

function selectFunction() {
    $('#fromDate').val("");
    $('#toDate').val("");
    if ($("#select-user").find(":selected").val() == "ALL") {
        var url = "/attendance/filter?page=0";
    } else {
        var userId = $("#select-user").find(":selected").val();
        var url = "/attendance/filter?page=0&id=" + userId;
    }
    getAttendanceData(url);
}

function filterByDateForAdmin() {
    if (validateDate()) {
        if ($("#select-user").val() == "ALL") {
            var url = "/attendance/filter?page=0&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        } else {
            var url = "/attendance/filter?page=0&id=" + $("#select-user").find(":selected").val() + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        }
        getAttendanceData(url);
    } else {
        alert("Please Select Filter Date less than year");

    }
}


function filterByDateForNormal() {
    if (validateDate()) {
        var url = "/attendance/filter?page=0&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        getAttendanceData(url);
    } else {
        alert("Please Select Filter Date less than year");

    }
}

function validateDate() {
    let date1 = new Date($('#toDate').val());
    let date2 = new Date($('#fromDate').val());

    let days = (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);
    if (days <= 365) {
        return true;
    } else {
        return false;
    }
}

function getAttendanceData(url) {

    var positionReq = $.ajax(window.location.origin + url, {
        method: "GET", timeout: 10000,

        beforeSend: function () {
            if (positionReq !== undefined && positionReq != null) {
                positionReq.abort();
            }
        }, success: function (data, status, xhr) {
            console.log("get called");
            $("#punchin-data-container").html(data);
        }, error: function (jqXhr, textStatus, errorMessage) {
            console.log("error")
            console.log(textStatus)
            console.log(errorMessage)
        }
    });
}

$("#iconFromDate").click(function () {
    $('#fromDate').datepicker().datepicker("show");
});

$("#iconToDate").click(function () {
    $('#toDate').datepicker().datepicker("show");
});


$('#punchout').click(function (){
    punchout('/attendance/punchOut');
});

function punchout(url) {

    var positionReq = $.ajax(window.location.origin + url, {
        method: "GET",
        dataType: 'json',
        timeout: 10000,

        beforeSend: function () {
            if (positionReq !== undefined && positionReq != null) {
                positionReq.abort();
            }
        }, success: function (data, status, xhr) {
            console.log("get called");
            if(data.detail.dayPassed===true){
                showHourInput();
            }
            else{


            }

            }, error: function (jqXhr, textStatus, errorMessage) {
            console.log("error")
            console.log(textStatus)
            console.log(errorMessage)
        }
    });
}


function  showHourInput(){
$('#attendanceModal').modal('toggle');
}

