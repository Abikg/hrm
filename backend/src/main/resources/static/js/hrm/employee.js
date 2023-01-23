window.addEventListener('DOMContentLoaded', (event) => {
    console.log('DOM fully loaded and parsed');
    listData("employee","api/list", "employee-table")
});
function viewEmployee(id, module){
    window.location.href = "/"+module+"/view/" + id;
}
function editEmployee(id,module) {
    window.location.href = "/"+module+"/edit/" + id;
}
$(document).ready(function(){

    $("#joinDate").text(moment($("#joinDate").text()).format("yyyy/MM/DD"));
})