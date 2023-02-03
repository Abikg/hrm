$('#icon1').click(function () {
    $('#date').datepicker().datepicker("show");
});

document.addEventListener("DOMContentLoaded", function () {
    listData("meetingMinutes", "allMinutes?meetingType=" + $('#meetingType').val(), "meeting-table");
})
$('#meetingForm').submit(function (e) {
    if ($('#date').val() === "") {
        $('#meetingDate-error p').text("Please Select Date");
        e.preventDefault();
    } else {
        $('#meetingDate-error p').text("");

    }
})
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

                alert("success");
            },
            error: function (jqXHR, textStatus, errorThrown) {

            }
        });
    e.preventDefault(); //STOP default action
    e.unbind();
});

