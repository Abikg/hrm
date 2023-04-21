let userReq;
let allUserType;

window.addEventListener('DOMContentLoaded', (event) => {
    listData("user","api/list", "user-table")
});
function viewUser(id,module){
    window.location.href = "/"+module+"/view/" + id;
}

function changePassword(userId){
    setupForChangePasswordForm(userId);
    $('#userPasswordModal').modal('toggle');
};
function setupForChangePasswordForm(userId) {
    $("#userPasswordForm")[0].reset();
    $("#userId").val(userId);
    $("#userPasswordModalLabel").text("Reset Password");
    const formBaseUrl = $("#userPasswordForm").data("action-base-url");
    $("#userPasswordForm").attr("action", formBaseUrl + "changePassword");
}
$("#userPasswordForm").submit(function (event) {
    event.preventDefault();
    const data = new FormData(event.target);
    const jsonData = Object.fromEntries(data.entries());
    const url = event.target.action;
    saveData(url,jsonData);
})
function changeUserType(userId,userType){
    getAllUserType().then(result => {
        console.log("User Type List Fetched");
        $('#userType').empty().select2({
            data: populateChangeUserTypeForm(result),
        })
        setTimeout(() => {
            setSelectedUserType(userType);
        }, 300);
    });
    setupForChangeUserType(userId,userType);
    $('#userChangeTypeModal').modal('toggle');
};
function setupForChangeUserType(userId,userType) {
    $("#userChangeTypeForm")[0].reset();
    $("#userId2").val(userId);
    $("#selectedUserType").val(userType);
    $("#userType").val(userType).trigger("change");
    $("#userChangeTypeModalLabel").text("Change User Type");
    const formBaseUrl = $("#userChangeTypeForm").data("action-base-url");
    $("#userChangeTypeForm").attr("action", formBaseUrl + "changeUserType");
}
$("#userChangeTypeForm").submit(function (event) {
    event.preventDefault();
    const data = new FormData(event.target);
    const jsonData = Object.fromEntries(data.entries());
    const url = event.target.action;
    saveData(url,jsonData);
})
function saveData(url,data) {
    userReq = $.ajax(url,
        {
            method: "POST",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),

            beforeSend: function () {
                if (userReq !== undefined && userReq != null) {
                    userReq.abort();
                }
            },
            success: function (data) {
                console.log("saved");
                if (data.status === 200) {
                    alert(data.message)
                    viewUser(data.detail.id, "user");
                }
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}

function disableUser(userId) {
    const url = window.location.origin + "/user/disableUser/" +userId;
    userReq = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 3000,
            beforeSend: function () {
                if (userReq !== undefined && userReq != null) {
                    userReq.abort();
                }
            },
            success: function (data) {
                console.log("saved");
                if (data.status === 200) {
                    alert(data.message);
                    viewUser(data.detail.id, "user")
                }
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}

function getAllUserType(){
    const url = window.location.origin + "/user/getUserType";
    userReq = $.ajax(url,
        {
            method: "GET",
            dataType: 'json',
            timeout: 3000,
            beforeSend: function () {
                if (userReq !== undefined && userReq != null) {
                    userReq.abort();
                }
            },
            success: function (data, status, xhr) {
                if (data.status === 200) {
                    allUserType = data.detail;
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
    return userReq.then(() => allUserType);
}
function populateChangeUserTypeForm(userType){
    let userTypeList=[];
    $.each(userType, function(index, value) {
        let userTypeKey = Object.keys(value)[0];
        let userTypeValue = value[userTypeKey];
        let u = {
            id: userTypeKey,
            text: userTypeValue
        }
        userTypeList.push(u);
    });

    return userTypeList;
}
$("#userType").change(function (){
    if($("#userType").val()){
        $("#selectedUserType").val($("#userType").val());
    }
});
function setSelectedUserType(userType){
    if(userType != null || userType !== undefined) {
        $('#userType').val(userType).trigger('change');
    }
    else{
        $('#userType').val("").trigger('change');
    }
}