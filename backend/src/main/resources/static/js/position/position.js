$('.deletePositionBtn').click(function (e){
   e.preventDefault();
   thispointer = $(this);
    if(confirm("Are you sure to delete position : "+thispointer.attr('positionid')) == true) {
        $.ajax({
            url: thispointer.attr('url'),
            method: "GET",
            dataType: 'json',
            beforeSend:function(xhr){
                thispointer.prop("disable",true);
            },

            success : function (result){
                if(result.status == 200){
                    alert("Position Deleted")
                    location.reload();
                }
            },
            error : function(jqXHR) {
                alert("Internal server error");
            },
            complete : function(data) {
                thispointer.prop("disabled", false);
            }


        });
    }
});