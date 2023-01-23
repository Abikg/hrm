$("#add-new-department").click(function () {
    setupForCreateForm();
    $('#departmentModal').modal('toggle');
})

window.addEventListener('DOMContentLoaded', (event) => {
    console.log('DOM fully loaded and parsed');
    listData("department","api/list", "department-table")

});
function editDepartment(id, module) {
    setupForEditForm()
    getDepartmentData(id);
    $('#departmentModal').modal('toggle');
}

function populateDataInForm(data) {
    $('#departmentModal').find("#id").val(data.id);
    $('#departmentModal').find("#title").val(data.title);
    $('#departmentModal').find("#code").val(data.departmentCode);
    $('#departmentModal').find("#details").text(data.detail);
}

let departmentReq = null;

function deleteDepartment(id, module) {
    const url = window.location.origin + "/" + module + "/delete/" + id;
    departmentReq = $.ajax(url,
        {
            method: "DELETE",
            dataType: 'json',
            timeout: 500,
            beforeSend: function () {
                if (departmentReq !== undefined && departmentReq != null) {
                    departmentReq.abort();
                }
            },
            success: function (data, status, xhr) {
                console.log("saved");
                if (data.status === 200) {
                    listData("department","api/list", "department-table")
                    // $("#" + id).remove();
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}


function getDepartmentData(id) {
    const url = $("#base-url").val() + "department/get/" + id;
    departmentReq = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 500,
            beforeSend: function () {
                if (departmentReq !== undefined && departmentReq != null) {
                    departmentReq.abort();
                }
            },
            success: function (data, status, xhr) {
                console.log("saved");
                if (data.status === 200) {
                    populateDataInForm(data.detail);
                    $('#departmentModal').modal('toggle');
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}


function setupForCreateForm() {
    $("#departmentForm")[0].reset();
    $("#departmentModalLabel").text("Create New Department");
    const formBaseUrl = $("#departmentForm").data("action-base-url");
    $("#departmentForm").attr("action", formBaseUrl + "save");
    resetFormError();
}

function setupForEditForm() {
    $("#departmentForm")[0].reset();
    $("#departmentModalLabel").text("Update Department");
    const formBaseUrl = $("#departmentForm").data("action-base-url");
    $("#departmentForm").attr("action", formBaseUrl + "update");
    resetFormError();
}

$("#departmentForm").submit(function (event) {
    event.preventDefault();
    const data = new FormData(event.target);
    const jsonData = Object.fromEntries(data.entries());
    const url = event.target.action;
    saveData(jsonData, url)
})


function saveData(data, url) {
    departmentReq = $.ajax(url,
        {
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            beforeSend: function () {
                if (departmentReq !== undefined && departmentReq != null) {
                    departmentReq.abort();
                }
            },
            success: function (data) {
                console.log("saved");
                if (data.status === 200) {
                    listData("department","api/list", "department-table")
                    $('#departmentModal').modal('toggle');
                } else if (data.status === 400) {
                    resetFormError();
                    showFormError(data.detail.error)
                }


            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}

function showFormError(error) {
    $("#error-title").text(error.title)
    $("#error-departmentCode").text(error.departmentCode)
    $("#error-detail").text(error.detail)
}

function resetFormError() {
    $("#error-title").text("")
    $("#error-departmentCode").text("")
    $("#error-detail").text("")
}

function appendNewRowInTable(data) {
    const id = "'" + data.id + "'"
    let row = "<tr id=" + data.id + ">"
    row += "<td class='title'>"
    row += data.title
    row += "</td>"
    row += "<td class='departmentCode'>"
    row += data.departmentCode
    row += "</td>"
    row += "<td class='detail'>"
    row += data.detail
    row += "</td>"
    row += "<td>"
    row += '<button type="button" data-id="' + data.id + '" onclick="editDepartment(' + id + ')" class="department-edit btn btn-info btn-sm">Edit</button>'
    row += '<button type="button" data-id="' + data.id + '" onclick="deleteDepartment(' + id + ')" class="btn btn-danger btn-sm">Delete</button>'
    row += "</td>"
    row += "</tr>"
    $("#department-table").append(row)
}

function updateRowInTable(data) {
    $("#" + data.id + " .title").text(data.title)
    $("#" + data.id + " .departmentCode").text(data.departmentCode)
    $("#" + data.id + " .detail").text(data.detail)
}


