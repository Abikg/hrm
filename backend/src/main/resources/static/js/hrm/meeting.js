$('#icon1').click(function (){
    $('#date').datepicker().datepicker("show");
});

document.addEventListener("DOMContentLoaded", function () {
    var meetingType=$('#meetingType').val();
    listData("meetingMinutes","allMinuteFetch?meetingType="+meetingType,"meeting-table");
})