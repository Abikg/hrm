$('#add-new-employee').click(function (){
    setupForCreateForm();
    $('#employeeModal').modal('toggle');
})

let employeeReq = null;

function setupForCreateForm(){
    $("#employeeForm")[0].reset();
    $("#employeeModalLabel").text("Create New Employee");
    getSelectOption();
    const formBaseUrl = $("#employeeForm").data("action-base-url");
    $("#employeeForm").attr("action",formBaseUrl+"save");
    resetFormError();
}
$("#employeeForm").submit(function (event){
    event.preventDefault();
    const form = $('#employeeForm')[0];
    const data = new FormData(form);
    const jsonData = Object.fromEntries(data.entries());
    const url = event.target.action;
    saveData(jsonData,url)
})

function saveData(data,url){
    employeeReq = $.ajax(url,{
        method: "POST",
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        timeout: 500,
        data:{
            data: $("#employeeForm").serialize(),
            file: $("#empImg")[0].files
        },

        beforeSend: function (){
            if(employeeReq !== undefined && employeeReq != null){
                employeeReq.abort();
            }
        },
        success: function (data,status,xhr){
            if(data.status === 200){
                console.log("saved");
            }else if(data.status === 400){
                showFormError(data.detail.error)
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log("error")
            console.log(textStatus)
            console.log(errorMessage)
        }
    })
}
function resetFormError(){
    $("#error-fullname").text("")
    $("#error-address").text("")
    $("#error-email").text("")
    $("#error-phone").text("")
    $("#error-position").text("")
    $("#error-department").text("")
    $("#error-empImage").text("")

}
function showFormError(error){
    $("#error-fullname").text(error.fullname)
    $("#error-address").text(error.address)
    $("#error-email").text(error.email)
    $("#error-phone").text(error.phone)
    $("#error-position").text(error.position)
    $("#error-department").text(error.department)
    $("#error-empImage").text(error.empImage)
}
function getSelectOption(){
    const url= $("#base-url").val()+"employee/getSelectList"
    employeeReq = $.ajax(url,{
        method:"GET",
        dataType:"json",
        timeout: 500,

        beforeSend: function (){
            if(employeeReq !== undefined && employeeReq != null){
                employeeReq.abort();
            }
        },
        success :function (data){
            if(data.status === 200){
                populateSelectForm(data.detail)
            }
        },
        error: function (jqXhr, textStatus, errorMessage) {
            console.log("error")
            console.log(textStatus)
            console.log(errorMessage)
        }
    });
}

function populateSelectForm(data){
    console.log(data)
    for (var index = 0; index < data.positionList.length; index++) {
        $('#position').append('<option value="' + data.positionList[index].id + '">' + data.positionList[index].title + '</option>');
    }
    for (var index = 0; index < data.departmentList.length; index++) {
        $('#department').append('<option value="' + data.departmentList[index].id + '">' + data.departmentList[index].title + '</option>');
    }
}