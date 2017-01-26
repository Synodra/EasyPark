/**
 * Created by antoine on 1/21/17.
 */
$("#inscriptionForm").submit(function(event){
    // cancels the form submission
    event.preventDefault();
    submitForm();
});

function submitForm(){
    // Initiate Variables With Form Content
    var firstname = $("#firstname").val();
    var name = $("#name").val();
    var email = $("#email").val();
    var password = $("#password").val();

    $.ajax({
        type: "POST",
        url: "http://synodra.ddns.net/easypark_rest_api/register.php",
        data: "name=" + name + "&firstname=" + firstname +" &email=" + email + "&password=" + password,
        success : function(text){
            if (text == "success"){
                formSuccess();
            }
        }
    });
}
function formSuccess(){
    $( "#msgSubmit" ).removeClass( "hidden" );
}