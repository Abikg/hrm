let employeeListForManager='';
let departmentListForEmployee='';
let positionListForEmployee;
let departmentRequest;
let employeeRequest;
let positionRequest;
window.addEventListener('DOMContentLoaded', (event) => {
        getDepartmentListForEmployee().then(result => {
            console.log("Department list fetched");
            $("#department").empty().select2({
                placeholder: {
                    id: null,
                    text: 'Select Department',
                },
                data: setupSelectOptionForDepartment(departmentListForEmployee),
                allowClear: true
            })
            setTimeout(() => {
                setSelectedDepartment($("#selectedDepartment").val());
            }, 300);
        });

        getPositionListForEmployee().then(result => {
            console.log("Position list fetched");
            $("#position").empty().select2({
                placeholder: {
                    id: null,
                    text: 'Select Position',
                },
                data: setupSelectOptionForPosition(positionListForEmployee),
                allowClear: true
            });
            setTimeout(() => {
                setSelectedPosition($("#selectedPosition").val());
            }, 300);
        });

        getActiveEmployeeList().then(result => {
            console.log("Active employee  list fetched");
            $('#reportingManager').empty().select2({
                placeholder: {
                    id: null,
                    text: 'Select Reporting Manager'
                },
                data: setupSelectOptionForEmployee(employeeListForManager),
                allowClear: true
            });
            setTimeout(() => {
                setSelectedManager($("#selectedManagerId").val());
            }, 2000);
        });
        if ($(".alertSuccess").length) {
            $('#employeeForm input').attr('readonly', 'readonly');
            $('#position').attr("disabled", true);
            $('#department').attr("disabled", true);
            window.setTimeout(function () {
                window.location.href = "/employee/list";
            }, 3000);
        }

        const imageCard = $(".imageCard");
        if (imageCard.length) {
            $(".imageCard").css({
                "width": "200px",
                "height": "200px",
                "display": "flex",
                "justify-content": "center",
                "align-items": "center",
                "overflow": "hidden"
            });
            $(".imageCard img").css({
                "flex-shrink": "0",
                "min-width": "100%",
                "min-height": "100%"
            });
        }

        if ($("input[name='joinDate']").val()) {
            const format = "ddd MMM D HH:mm:ss z yyyy";
            if (moment($("input[name='joinDate']").val(), format).isValid()) {
                $("input[name='joinDate']").val(moment($("input[name='joinDate']").val(), format).format("YYYY/MM/DD"));

            } else {
                $("input[name='joinDate']").val(moment($("input[name='joinDate']").val()).format("YYYY/MM/DD"));
            }
        }
        if ($("#hiddenJoinDate").val()) {
            $(this).val(moment($(this).val()).format("YYYY/MM/DD"));
        }
})
$("#employeeForm").submit(function(e) {
    if ($("input[name='joinDate']").val() === ""){
        e.preventDefault();
        $("#joinDateError").removeClass("d-none");
        $("#joinDateError p").text("Join date can't be empty.");
    }else{
        $("#joinDateError").addClass("d-none");
    }
});

$(".dateIcon").on("click", function(){
    $(this).parent().next().datepicker().datepicker("show");
});

$("#joinDate").change(function() {
        let date = new Date(Date.parse($(this).val()));
        const formattedDate = moment(date).format("YYYY-MM-DD HH:mm:ss.S");

        $(this).val(formattedDate);
});

$(".inputDobDate").change(function() {
    setAge($(".inputDobDate").val());
});
$(".checkUser").change(function() {
    if(this.checked) {
        $(this).val(true);
    } else {
        $(this).val(false);
    }
});
function setAge(date) {
    let birthDate = new Date(date);

    const today = new Date();

    let age = today.getFullYear() - birthDate.getFullYear();
    const m = today.getMonth() - birthDate.getMonth();
    if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    $(".age").val(age);
}
let count = 1;
let dtoCount = 0;
function  addWorkExperience(workExperienceCount){
        dtoCount = workExperienceCount + 1;
    let html = "<div class=\"form-group mt-2 p-3 custom-card-layout work-experience-content_"+count+ "\"\>";

    html +=  "<button class=\"btn btn-danger float-right mb-2\" type=\"button\" onclick='remove(count-1)'>Remove</button>";
    html += "<div class=\"form-group\">";
    html += " <label for=\"prevCompany\" class=\"col-form-label font-weight-normal\">Previous Company</label>\n" +
            " <input type=\"text\" name=\"workExperienceDTO["+dtoCount+"\].previousCompany\" class=\"form-control\" id=\"prevCompany\" required>";
    html += "</div>";
    html += "<div class=\"form-inline\">";
    html += "<div class=\"form-group\">\n" +
            "<label class=\"mr-3 col-form-label font-weight-normal\">Job Title</label>\n" +
            " <input type=\"text\" class=\"form-control\" name=\"workExperienceDTO["+dtoCount+"\].jobTitle\"  placeholder=\"Job Title Eg.: Developer\" required>\n" +
            "</div>";
    html +=     "<div class=\"form-group ml-0 ml-sm-auto\">\n" +
                " <label class=\"mr-3 col-form-label font-weight-normal\">From</label>\n" +
                " <div class=\"input-group\">\n" +
                " <div class=\"input-group-prepend\">\n" +
                "  <span class=\"input-group-text bg-transparent dateIcon\" id=\"icon\"><i class=\"fas fa-calendar\"></i></span>\n" +
                "  </div>\n" +
                "  <input type=\"text\" class=\"form-control mydatepicker fromDate bg-transparent\" name=\"workExperienceDTO["+dtoCount+"\].joinDate\" data-date-format=\"yyyy/mm/dd\" required readonly>\n" +
                "   </div>\n" +
                "   </div>\n" +
                "   <div class=\"form-group ml-0 ml-sm-auto\">\n" +
                "   <label class=\"mr-3 col-form-label font-weight-normal\">To</label>\n" +
                "   <div class=\"input-group\">\n" +
                "   <div class=\"input-group-prepend\">\n" +
                "   <span class=\"input-group-text bg-transparent dateIcon\" id=\"icon\"><i class=\"fas fa-calendar\"></i></span>\n" +
                "   </div>\n" +
                "   <input type=\"text\" class=\"form-control mydatepicker toDate bg-transparent\" name=\"workExperienceDTO["+dtoCount+"\].leftDate\" data-date-format=\"yyyy/mm/dd\" required readonly>\n" +
                "   </div>\n" +
                "   </div>";
    html +=     "</div>";

    html +=     "<div class=\"form-group\">\n" +
                "<label for=\"jobDesc col-form-label font-weight-normal\" class=\"col-form-label\">Job Description</label>\n" +
                "<textarea class=\"form-control\" name=\"workExperienceDTO["+dtoCount+"\].jobDesc\" id=\"jobDesc\" required></textarea>\n" +
                "</div>";

    html +=     "</div>";

    $("#work-experience-wrapper").append(html);
    $(".mydatepicker").datepicker({
        format: "yyyy/mm/dd",
        autoclose: true,
        todayHighlight: true
    });

    $(".work-experience-content_"+count).css({
        "box-shadow": "0 3px 10px rgb(0 0 0 / 20%)",
        "padding":"12px"
    });
    count++;
    dtoCount++;
}
function remove(countValue){
    $(".work-experience-content_"+countValue).remove();
}

function getDepartmentListForEmployee(){
    const url = window.location.origin + "/department/api/list";
    departmentRequest = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 3000,
            beforeSend: function () {
                if (departmentRequest !== undefined && departmentRequest != null) {
                    departmentRequest.abort();
                }
            },
            success: function (data, status, xhr) {
                if (data.status === 200) {
                    departmentListForEmployee = data.detail;
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
        return departmentRequest.then(() => departmentListForEmployee);
}
function getPositionListForEmployee(){
    const url = window.location.origin + "/position/api/list";
    positionRequest = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 3000,
            beforeSend: function () {
                if (positionRequest !== undefined && positionRequest != null) {
                    positionRequest.abort();
                }
            },
            success: function (data, status, xhr) {
                if (data.status === 200) {
                    positionListForEmployee = data.detail;
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
    return positionRequest.then(() => positionListForEmployee);
}

function getActiveEmployeeList(){
    const url = window.location.origin + "/employee/getActiveEmployees";
    employeeRequest = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 3000,
            beforeSend: function () {
                if (employeeRequest !== undefined && employeeRequest != null) {
                    employeeRequest.abort();
                }
            },
            success: function (data, status, xhr) {
                if (data.status === 200) {
                    employeeListForManager = data.detail;
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });

        return employeeRequest.then(() => employeeListForManager);
}
$("#department").on("change",function(){
    setDepartmentManagerAsReporting($(this).val());
    $("#selectedDepartment").val($(this).val())

});
$("#position").on("change",function(){
    $("#selectedPosition").val($(this).val())
});
function setupSelectOptionForEmployee(data){
    let employees=[];
    let employee;
    if(data.length <= 0  ) {
        $(".reportingManager").addClass("d-none");
    }else {
        for (let i = 0; i < data.length; i++) {
           employee = {
                id: data[i].id,
                text: data[i].fullname
            }
            employees.push(employee);
        }
    }
    return employees;
}
function setupSelectOptionForDepartment(data){
    let dataList=[];
    let val;
    if(data.length <= 0  ) {
        $(".error-department").val("No department found");
    }else {
        for (let i = 0; i < data.length; i++) {
             val = {
                id: data[i].id,
                text: data[i].title
            }
            dataList.push(val);
        }
    }
    return dataList;
}
function setupSelectOptionForPosition(data){
    let dataList=[];
    let val;
    if(data.length <= 0  ) {
        $(".error-position").val("No position found");
    }else {
        for (let i = 0; i < data.length; i++) {
            val = {
                id: data[i].id,
                text: data[i].title
            }
            dataList.push(val);
        }
    }
    return dataList;
}
function setDepartmentManagerAsReporting(selectedDepId){
    let selectedDeptManagerId = getSelectedDepartmentManager(selectedDepId);
    let employeeId = $("#employeeId").val();
    if(employeeListForManager.length <= 0  ) {
        $(".reportingManager").addClass("d-none");
    }else{
        if(selectedDeptManagerId !== null && selectedDeptManagerId != employeeId) {
            $('#reportingManager').val(selectedDeptManagerId).trigger('change');
        }

    }

}
function getSelectedDepartmentManager(departmentId){
    let selectedManagerId = null;
    let selectedManager = departmentListForEmployee.find(e => e.id == departmentId);
    if(selectedManager !=null && selectedManager.managerId != null){
        selectedManagerId = selectedManager.managerId;
    }
    return selectedManagerId;
}
function setSelectedManager(managerId){
    if(employeeListForManager.length > 0  ) {
        if(managerId != null || managerId !== undefined) {
            $('#reportingManager').val(managerId).trigger('change');
        }else{
            $('#reportingManager').val("").trigger('change');
        }

    }
}
function setSelectedDepartment(departmentId){
    $('#department').val(departmentId).trigger('change');
}
function setSelectedPosition(positionId){
  $('#position').val(positionId).trigger('change');
}