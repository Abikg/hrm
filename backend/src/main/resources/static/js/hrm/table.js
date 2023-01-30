let dataTable = ''


function listData(module, listApi, tableId) {
    let base_url = window.location.origin + "/" + module + "/" + listApi;
    $.ajax(base_url,
        {
            method: "GET",
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.status === 200) {
                    createTable(data, module, tableId)
                }
            },
            error: function (jqXhr, textStatus, errorMessage) {
                console.log("error")
                console.log(textStatus)
                console.log(errorMessage)
            }
        });
}
function createTable(data, module, tableId) {
    var tableColumns = [],
        actualData = []
    actualData = data.detail
    $.each(actualData, function (k, v) {
        var actionDataElement = '<div class="actionElements">' +
            '<a href="#" onclick="editRecord(' + "'elementId'"+', ' + "'module'"+')" class="department-edit btn btn-info btn-sm"' +
            'style="width: 50px;">' +
            '<i class="fa fa-edit" style="font-size:14px;color:white"></i>' +
            '</a>' ;
            if(module === "employee"){
                actionDataElement += '<a href="#" onclick="viewRecord(' + "'elementId'"+', ' + "'module'"+')" class="btn btn-warning btn-sm ml-2" ' +
                    'style="width: 50px;">' +
                                    '<i class="fa fa-info-circle" style="font-size:14px;color:white"></i>' +
                                    '</a>' +
                                    '</div>';
            }else{
                actionDataElement += '<a href="#" onclick="deleteRecord(' + "'elementId'"+', ' + "'module'"+')" class="btn btn-danger btn-sm ml-2"' +
                    'style="width: 50px;">' +
                    '<i class="fa fa-trash" style="font-size:14px;color:white"></i>' +
                    '</a>' +
                    '</div>';
            }
        v["action"] = actionDataElement.replaceAll("elementId",v.id).replaceAll("module",module);

    })
    var columns = data.column;
    $.each(columns, function (key, val) {
        if (val.width) {
            tableColumns[key] = {
                "data": val.name,
                "title": val.displayName + "<span></span>",
                orderable: val.orderable,
                width: val.width,
            }
        }else if(val.name == "employeeStatus"){
            tableColumns[key] = {
                "data": val.name,
                "title": val.displayName + "<span></span>",
                orderable: val.orderable,
            render: function (data, type, row){
                    if(data == "ACTIVE") {
                        return '<span class="badge badge-success">' + data + '</span>'
                    }else if(data == "RESIGNED"){
                        return '<span class="badge badge-danger">' + data + '</span>'
                    }
            }}
        }
        else {
            tableColumns[key] = {"data": val.name, "title": val.displayName + "<span></span>", orderable: val.orderable}
        }
    });
    dataTable = $('#' + tableId).DataTable({
        pageLength: 10,
        scrollX: true,
        destroy: true,
        data: actualData,
        // width: '100%',
        bAutoWidth: false,
        columns: tableColumns,
        searching: true,
        "fnRowCallback": function (nRow, aData, iDisplayIndex) {
            $(nRow).click(function () {
                // currentSelectedId = aData.id;
                console.log("row selected"+ aData.id)
            })
            return nRow;
        }
    });
    $('#' + tableId).find("tbody").find("tr").find("td").css("white-space", "nowrap");
    $('.dataTables_scrollHeadInner').find(".dataTable ").find("thead").find("th").css("white-space", "nowrap");
    $('.dataTables_scrollHeadInner').css("background-color","#1479c4");
    $('.dataTables_scrollHeadInner table').find("thead").css("color", "white")
    dataTable.columns.adjust().draw();
}

function editRecord(id,module){
    if(module === "department"){
        editDepartment(id,module);
    }
    else if(module === "position"){
        editPosition(id,module);
    }
    else if(module === "employee"){
        editEmployee(id,module);
    }
}


function deleteRecord(id,module){
    if(module === "department"){
        deleteDepartment(id,module);
    }
    else if(module === "position"){
        deletePosition(id,module);
    }
}

function viewRecord(id, module){
    if(module === "employee"){
        viewEmployee(id,module);
    }
}
