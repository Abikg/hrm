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

});