window.addEventListener('DOMContentLoaded', (event) => {
    console.log('DOM fully loaded and parsed');
    listData("employee","api/list", "employee-table")
});
function viewEmployee(id, module){
    alert(module+"/"+id);
}
function editEmployee(id,module) {
    window.location.href = "/"+module+"/edit/" + id;
}