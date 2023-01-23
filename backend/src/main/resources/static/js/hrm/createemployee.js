$(document).ready(function(){

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
    if($("#joinDate").val()){
        $("#joinDate").val(moment($("#joinDate").val()).format("yyyy/MM/DD"));
    }
});

$("#joinDate").change(function() {
    let date = moment($(this).val(), 'yyyy/mm/dd').toDate();
    $(this).val(date);
});

$(".inputDate").change(function() {
    setAge($(".inputDate").val());
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
    html += " <label for=\"prevCompany\" class=\"col-form-label\">Previous Company</label>\n" +
            " <input type=\"text\" name=\"workExperienceDTO["+dtoCount+"\].previousCompany\" class=\"form-control\" id=\"prevCompany\" required>";
    html += "</div>";
    html += "<div class=\"form-inline\">";
    html += "<div class=\"form-group\">\n" +
            "<label class=\"mr-3\">Job Title</label>\n" +
            " <input type=\"text\" class=\"form-control\" name=\"workExperienceDTO["+dtoCount+"\].jobTitle\"  placeholder=\"Job Title Eg.: Developer\" required>\n" +
            "</div>";
    html +=     "<div class=\"form-group ml-0 ml-sm-auto\">\n" +
                " <label class=\"mr-3\">From</label>\n" +
                " <div class=\"input-group\">\n" +
                " <div class=\"input-group-prepend\">\n" +
                "  <span class=\"input-group-text bg-transparent\" id=\"icon\"><i class=\"fas fa-calendar\"></i></span>\n" +
                "  </div>\n" +
                "  <input type=\"text\" class=\"form-control mydatepicker fromDate\" name=\"workExperienceDTO["+dtoCount+"\].joinDate\" data-date-format=\"yyyy/mm/dd\" required>\n" +
                "   </div>\n" +
                "   </div>\n" +
                "   <div class=\"form-group ml-0 ml-sm-auto\">\n" +
                "   <label class=\"mr-3\">To</label>\n" +
                "   <div class=\"input-group\">\n" +
                "   <div class=\"input-group-prepend\">\n" +
                "   <span class=\"input-group-text bg-transparent\" id=\"icon\"><i class=\"fas fa-calendar\"></i></span>\n" +
                "   </div>\n" +
                "   <input type=\"text\" class=\"form-control mydatepicker toDate\" name=\"workExperienceDTO["+dtoCount+"\].leftDate\" data-date-format=\"yyyy/mm/dd\" required>\n" +
                "   </div>\n" +
                "   </div>";
    html +=     "</div>";

    html +=     "<div class=\"form-group\">\n" +
                "<label for=\"jobDesc\" class=\"col-form-label\">Job Description</label>\n" +
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
