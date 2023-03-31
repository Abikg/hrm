let employeeReq = null;
window.addEventListener('DOMContentLoaded', (event) => {
    listData("employee","api/list", "employee-table")
});
function viewEmployee(id, module){
    window.location.href = "/"+module+"/view/" + id;
    getEmployeeSubordinates(id);
}
function editEmployee(id,module) {
    window.location.href = "/"+module+"/edit/" + id;
}
$(document).ready(function(){
    $("#joinDate").text(moment($("#joinDate").text()).format("yyyy/MM/DD"));
    $("#resDate").text(moment($("#resDate").text()).format("yyyy/MM/DD"));
    $("#resExitDate").text(moment($("#resExitDate").text()).format("yyyy/MM/DD"));
});

$(".dateIcon").on("click", function(){
    $(this).parent().next().datepicker().datepicker("show");
});

$('#employeeResignationModal').on('shown.bs.modal', function () {
    $(".rawDate").each(function() {
        if ($(this).val()) {
            $(this).val(moment($(this).val()).format("yyyy/MM/DD"));
        }
    });
});


$("#resignationDate").change(function() {
    let date = moment($("#resignationDate").val(), 'yyyy/mm/dd').toDate();
    $("#resignationDate").val(date);
});
$("#exitDate").change(function() {
    let date = moment($("#exitDate").val(), 'yyyy/mm/dd').toDate();
    $("#exitDate").val(date);
});
function createResignation(id){
    setupForCreateResignationForm(id);
    $('#employeeResignationModal').modal('toggle');
};
function setupForCreateResignationForm(id) {
    $("#employeeResignationForm")[0].reset();
    $("#employeeResId").val(id);
    $("#employeeResignationModalLabel").text("Create New Resignation");
    const formBaseUrl = $("#employeeResignationForm").data("action-base-url");
    $("#employeeResignationForm").attr("action", formBaseUrl + "createResignation");
}
$("#employeeResignationForm").submit(function (event) {
    event.preventDefault();
    const url = event.target.action;
    saveData(url)
})


function saveData(url) {
    employeeReq = $.ajax(url,
        {
            method: "POST",
            data: $("#employeeResignationForm").serialize(),
            beforeSend: function () {
                if (employeeReq !== undefined && employeeReq != null) {
                    employeeReq.abort();
                }
            },
            success: function (data) {
                console.log("saved");
                if (data.status === 200) {
                  $("#employeeResignationModal").modal('toggle');
                  viewEmployee(data.detail.id,"employee");
                  console.log("saved");
                }
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}
function editResignation(id){
    setupForEditResignationForm(id);
    getEmployeeData(id);
    $('#employeeResignationModal').modal('toggle');
    
}
function setupForEditResignationForm(id){
    $("#employeeResignationForm")[0].reset();
    $("#employeeResignationModalLabel").text("Edit Resignation");
    const formBaseUrl = $("#employeeResignationForm").data("action-base-url");
    $("#employeeResignationForm").attr("action", formBaseUrl + "updateResignation");
}
function getEmployeeData(id){
    const url = $("#base-url").val() + "employee/get/" + id;
    employeeReq = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 500,
            beforeSend: function () {
                if (employeeReq !== undefined && employeeReq != null) {
                    employeeReq.abort();
                }
            },
            success: function (data, status, xhr) {
                console.log("saved");
                if (data.status === 200) {
                    populateDataInForm(data.detail);
                    $('#employeeResignationModal').modal('toggle');
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}
function  populateDataInForm(data){
    $("#employeeResignationModal").find("#employeeResId").val(data.id);
    $("#employeeResignationModal").find("#resignationReason").val(data.resignationReason);
    $("#employeeResignationModal").find("#resignationDate").val(data.resignationDate);
    $("#employeeResignationModal").find("#exitDate").val(data.exitDate);
}
function approveResignation(id){
    const url = window.location.origin + "/employee/approveResignation/"+ id;
    employeeReq = $.ajax(url,
        {
            method: "POST",
            beforeSend: function () {
                if (employeeReq !== undefined && employeeReq != null) {
                    employeeReq.abort();
                }
            },
            success: function (data) {
                if (data.status === 200) {
                    viewEmployee(data.detail.id,"employee");
                    console.log("saved");
                }
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}
function exitResignation(id){
    const url = window.location.origin + "/employee/exitResignation/"+ id;
    employeeReq = $.ajax(url,
        {
            method: "POST",
            beforeSend: function () {
                if (employeeReq !== undefined && employeeReq != null) {
                    employeeReq.abort();
                }
            },
            success: function (data) {
                if (data.status === 200) {
                    viewEmployee(data.detail.id,"employee");
                    console.log("saved");
                }
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}
function  getEmployeeSubordinates(id){
    const url = $("#base-url").val() + "employee/" + id + "/subordinates";
    employeeReq = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 500,
            beforeSend: function () {
                if (employeeReq !== undefined && employeeReq != null) {
                    employeeReq.abort();
                }
            },
            success: function (data, status, xhr) {
                console.log("saved");
                if (data.status === 200) {
                    setSubordinates(data.detail);
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}
function setSubordinates(data){

}