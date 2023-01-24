$('.mydatepicker').on('click', function (e) {
    e.preventDefault();
    $(this).attr("autocomplete", "off");
});

document.addEventListener("DOMContentLoaded", function () {

    if ($("#adminFlag").val() == "true") {
        var url = "/attendance/allUserAttendance?page=0";

    } else if ($("#adminFlag").val() == "false") {
        var url = "/attendance/attendancelist?page=0";
    }
    getAttendanceData(url);
})

function pagination(page) {
    var url;
    if ($('#adminFlag').val() == "true") {

        if ($('#noDateFilterFlagForAllUsers').val() == "true") {
            url = "/attendance/allUserAttendance?page=" + page;

        } else if ($('#noDateFilterFlagForAllUsers').val() == "false") {
            url = "/attendance/allUserAttendanceByDate?page=" + page + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        }
    }

    getAttendanceData(url);
}

function paginationsecond(page) {

    if ($('#adminFlag').val() == "true") {
        var userId = $("#select-user").find(":selected").val();
        if ($('#noDateFilterFlag').val() == "true") {
            var url = "/attendance/attendancelist?page=" + page + "&id=" + userId;

        } else if ($('#noDateFilterFlag').val() == "false") {
            var url = "/attendance/dateFilterForUser?page=" + page + "&id=" + userId + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        }
    } else if ($('#adminFlag').val() == "false") {
        if ($('#noDateFilterFlag').val() == "true") {
            var url = "/attendance/attendancelist?page=" + page;

        } else if ($('#noDateFilterFlag').val() == "false") {
            var url = "/attendance/dateFilterForUser?page=" + page + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
        }
    }

    getAttendanceData(url);
}

function selectFunction() {
    if ($("#select-user").find(":selected").val() == "ALL") {
        var url = "/attendance/allUserAttendance?page=0";
    } else {
        var userId = $("#select-user").find(":selected").val();
        $('#fromDate').val("");
        $('#toDate').val("");
        var url = "/attendance/attendancelist?page=0&id=" + userId;
    }

    getAttendanceData(url);
}

function filterByDate() {
    let date1 = new Date($('#toDate').val());
    let date2 = new Date($('#fromDate').val());
    let days = (date1.getTime() - date2.getTime())/(1000 * 60 * 60 * 24);

    if (days <= 365) {

        if ($("#adminFlag").val() == "true") {

            if ($("#select-user").val() == "ALL") {
                var url = "/attendance/allUserAttendanceByDate?page=0&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
            } else {
                var url = "/attendance/dateFilterForUser?page=0&id=" + $("#select-user").find(":selected").val() + "&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();
            }
        } else if ($("#adminFlag").val() == "false") {
            var url = "/attendance/dateFilterForUser?page=0&toDate=" + $('#toDate').val() + "&fromDate=" + $('#fromDate').val();

        }
        getAttendanceData(url);
    } else {
        alert("Please Select Filter Date less than year");
    }

}

function getAttendanceData(url) {

    var positionReq = $.ajax(window.location.origin + url, {
        method: "GET", timeout: 500,

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