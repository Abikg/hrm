let employeeListForManager='';
let departmentListForEmployee='';
let departmentRequest;
let employeeRequest;
window.addEventListener('DOMContentLoaded', (event) => {
    getDepartmentListForEmployee().then(result => {
        console.log("Department list fetched");
    });
    getActiveEmployeeList().then(result => {
        console.log("Active employee  list fetched");
        $('#reportingManager').empty().select2({
            data: getEmployeeData(employeeListForManager)
        });
        setTimeout(() => {
            setupSelectManagerList($("#selectedManagerId").val());
        }, 2000);
    });
    if($(".alertSuccess").length){
        $('#employeeForm input').attr('readonly', 'readonly');
        $('#position').attr("disabled",true);
        $('#department').attr("disabled",true);
        window.setTimeout(function() {
            window.location.href = "/employee/list";
        }, 3000);
    }

    const imageCard = $(".imageCard");
    if(imageCard.length){
        $(".imageCard").css({
            "width": "200px",
            "height": "200px",
            "display": "flex",
            "justify-content": "center",
            "align-items": "center",
            "overflow": "hidden"
        });
        $(".imageCard img").css({  "flex-shrink": "0",
            "min-width": "100%",
            "min-height": "100%"});
    }

    if($("input[name='joinDate']").val()){
        const format = "ddd MMM D HH:mm:ss z yyyy";
        if (moment($("input[name='joinDate']").val(), format).isValid()) {
            $("input[name='joinDate']").val(moment($("input[name='joinDate']").val(), format).format("YYYY/MM/DD"));

        } else {
            $("input[name='joinDate']").val(moment($("input[name='joinDate']").val()).format("YYYY/MM/DD"));
        }
    }

    if($("#hiddenJoinDate").val()){
        $(this).val(moment($(this).val()).format("YYYY/MM/DD"));
    }
    $('#position').select2({
        placeholder:'Select Department',
    });
    $('#department').select2({
        placeholder:'Select Position',

});
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
    if(this.value != null) {
        setDepartmentManager(this.value);
    }
});
function getEmployeeData(data){
    let employees=[];
    let manager;
    if(data.length <= 0  ) {
        $(".reportingManager").addClass("d-none");
    }else {
        for (let i = 0; i < data.length; i++) {
            let employee = {
                id: data[i].id,
                text: data[i].fullname
            }
            employees.push(employee);
        }
    }
    return employees;
}
function getSelectedDepartmentManager(departmentId){
    let selectedManagerId = null;
    let selectedManager = departmentListForEmployee.find(e => e.id == departmentId);
    if(selectedManager.managerId != null){
        selectedManagerId = selectedManager.managerId;
    }
    return selectedManagerId;
}
function setDepartmentManager(selectedDepId){
    let selectedDeptManagerId = getSelectedDepartmentManager(selectedDepId);
    if(employeeListForManager.length <= 0  ) {
        $(".reportingManager").addClass("d-none");
    }else{
        if(selectedDeptManagerId != null) {
            $('#reportingManager').val(selectedDeptManagerId).trigger('change');
        }

    }

}
function setupSelectManagerList(managerId){
    if(employeeListForManager.length > 0  ) {
        if(managerId != null || managerId !== undefined) {
            $('#reportingManager').val(managerId).trigger('change');
        }

    }
}