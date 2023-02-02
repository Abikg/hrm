document.addEventListener("DOMContentLoaded", function () {
    listData("attendance", "filter", "attendance-table");
})
function selectFunction() {
    $('#fromDate').val("");
    $('#toDate').val("");
    if ($("#select-user").find(":selected").val() == "ALL") {
        listData("attendance", "filter", "attendance-table");
    } else {
        var userId = $("#select-user").find(":selected").val();
        listData("attendance", "filter?id=" + userId, "attendance-table");
    }
}
function filterByDateForAdmin() {
    if (validateDate()) {
        if ($("#select-user").val() == "ALL") {
            listData("attendance", "filter?toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val(), "attendance-table");
        } else {
            listData("attendance", "filter?toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val() + "&id=" + $("#select-user").find(":selected").val(), "attendance-table");
        }
    } else {
        if ($('#toDate').val() === "" || $('#fromDate').val() === "") {

        } else {
            alert("Please Select Filter Date less than year");
        }
    }
}

function filterByDateForNormal() {
    if (validateDate()) {
        listData("attendance", "filter?toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val(), "attendance-table")

    } else {
        if ($('#toDate').val() === "" || $('#fromDate').val() === "") {

        } else {
            alert("Please Select Filter Date less than year");
        }

    }
}
function validateDate() {

    let date1 = new Date($('#toDate').val());
    let date2 = new Date($('#fromDate').val());
    if ($('#toDate').val() === "") {
        $('#fromDate-error p').text("Please Select fromDate");

    } else {
        $('#fromDate-error p').text("");
    }
    if ($('#toDate').val() === "") {
        $('#toDate-error p').text("Please Select toDate");

    } else {
        $('#toDate-error p').text("");
    }

    let days = (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);
    if (days <= 365) {
        return true;
    } else {
        return false;
    }
}
$("#iconFromDate").click(function () {
    $('#fromDate').datepicker().datepicker("show");
});
$("#iconToDate").click(function () {
    $('#toDate').datepicker().datepicker("show");
});
$('#punchout').click(function () {
    punchout('/attendance/punchOut');
});
$("#attendanceTimeForm").submit(function (e) {

    var postData = $(this).serializeArray();
    var formURL = $(this).attr("action");

    $.ajax(
        {
            url: formURL,
            type: "POST",
            crossDomain: true,
            data: postData,
            dataType: "json",
            success: function (data, textStatus, jqXHR) {
                console.log("next day punhout called");

                if (data.detail.notOfficeHours === true) {

                    $('#error-attendancetTime').text("Please Enter officeHours");
                } else {

                    $('#attendanceModal').modal('toggle');

                }
            },
            error: function (jqXHR, textStatus, errorThrown) {

            }
        });
    e.preventDefault(); //STOP default action
    e.unbind();
});
function punchout(url) {

    var attendanceReq = $.ajax(window.location.origin + url, {
        method: "GET",
        dataType: 'json',
        timeout: 10000,

        beforeSend: function () {
            if (attendanceReq !== undefined && attendanceReq != null) {
                attendanceReq.abort();
            }
        },
        success: function (data, status, xhr) {
            console.log("get called");
            if (data.detail.dayPassed === true) {
                showHourInput();
            } else {
                showSuccessAlert();

            }

        }, error: function (jqXhr, textStatus, errorMessage) {
            console.log("error")
            console.log(textStatus)
            console.log(errorMessage)
        }
    });
}
function showHourInput() {
    $('#attendanceModal').modal('toggle');
}function showSuccessAlert() {
    $.notify("Successfully punchout", "success");
}

