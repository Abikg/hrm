$("#add-new-position").click(function (){
    setupForCreateForm();
    $('#positionModal').modal('toggle');
})


function editPosition(id){
    setupForEditForm()
    getPositionData(id);
    $('#positionModal').modal('toggle');
}

function populateDataInForm(data){
    $('#positionModal').find("#id").val(data.id);
    $('#positionModal').find("#title").val(data.title);
    $('#positionModal').find("#details").text(data.detail);
}

let positionReq= null;

function deletePosition(id){
    const url = $("#base-url").val() +"position/delete/"+id;
    positionReq  = $.ajax(url,
        {
            method:"DELETE",
            dataType: 'json',
            timeout: 500,
            beforeSend: function(){
                if (positionReq !== undefined && positionReq != null){
                    positionReq.abort();
                }
            },
            success: function (data,status,xhr) {
                console.log("saved");
                if (data.status === 200){
                    $("#"+id).remove();
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}


function getPositionData(id){
    const url = $("#base-url").val() +"position/get/"+id;
    positionReq  = $.ajax(url,
        {
            method:"GET",
            dataType: 'json',
            timeout: 500,
            beforeSend: function(){
                if (positionReq !== undefined && positionReq != null){
                    positionReq.abort();
                }
            },
            success: function (data,status,xhr) {
                console.log("saved");
                if (data.status === 200){
                    populateDataInForm(data.detail);
                    $('#positionModal').modal('toggle');
                }

            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}



function setupForCreateForm(){
    $("#positionForm")[0].reset();
    $("#positionModalLabel").text("Create New Position");
    const formBaseUrl = $("#positionForm").data("action-base-url");
    $("#positionForm").attr("action", formBaseUrl+"save");
    resetFormError();
}

function setupForEditForm(){
    $("#positionForm")[0].reset();
    $("#positionModalLabel").text("Update Position");
    const formBaseUrl = $("#positionForm").data("action-base-url");
    $("#positionForm").attr("action", formBaseUrl+"update");
    resetFormError();
}

$("#positionForm").submit(function (event){
    event.preventDefault();
    debugger
    const data = new FormData(event.target);
    const jsonData = Object.fromEntries(data.entries());
    const url = event.target.action;
    saveData(jsonData,url)
})


function saveData(data, url){
    positionReq  = $.ajax(url,
        {
            method:"POST",
            dataType : "json",
            contentType: "application/json; charset=utf-8",
            timeout: 500,
            data:JSON.stringify(data),
            beforeSend: function(){
                if (positionReq !== undefined && positionReq != null){
                    positionReq.abort();
                }
            },
            success: function (data,status,xhr) {
                console.log("saved");
                if (data.status === 200){
                    if (url.indexOf("save")>0){
                        appendNewRowInTable(data.detail);
                    }else{
                        updateRowInTable(data.detail)
                    }
                    $('#positionModal').modal('toggle');
                }else if (data.status === 400){
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

function showFormError(error){
    $("#error-title").text(error.title)
    $("#error-detail").text(error.detail)
}

function resetFormError(){
    $("#error-title").text("")
    $("#error-detail").text("")
}

function appendNewRowInTable(data){
    const id = "'"+data.id+"'"
    let row = "<tr id="+data.id+">"
    row += "<td class='title'>"
    row += data.title
    row += "</td>"
    row += "</td>"
    row += "<td class='detail'>"
    row += data.detail
    row += "</td>"
    row += "<td>"
    row += '<button type="button" data-id="'+data.id+'" onclick="editPosition('+id+')" class="position-edit btn btn-info btn-sm">Edit</button>'
    row += '<button type="button" data-id="'+data.id+'" onclick="deletePosition('+id+')" class="btn btn-danger btn-sm">Delete</button>'
    row += "</td>"
    row += "</tr>"
    $("#position-table").append(row)
}

function updateRowInTable(data){
    $("#"+data.id +" .title").text(data.title)
    $("#"+data.id +" .detail").text(data.detail)
}