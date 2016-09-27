function getParameterByName(name) {
    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
            results = regex.exec(location.search);
    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
}

function resetPassword() {
    var email = getParameterByName("email");
    var code = getParameterByName("code");
    var password = $('#passwordBox').val();
    var repeatedPassword = $('#repeatPasswordBox').val();

    if (email !== undefined && email.length > 0 &&
        code !== undefined && code.length > 0 &&
        password !== undefined && password.length > 3 &&
        repeatedPassword == password) {

        var button = $('#resetPasswordButton');
        var buttonCaption = button.html();
        button.html('Sending ...');
        button.prop('disabled', true);

        $.ajax({
                   url: "/reset",
                   type: "POST",
                   data: {'email': email, 'code': code, 'password': password},
                   success: function (response) {
                       enableButton(button, buttonCaption);
                       if (response.responseObject == true) {
                           alert("You have successfully changed the password! :)");
                       } else{
                           alert("Sorry, cannot reset the password right now. Probably email or code is not correct :( Please, try again later");
                       }
                   },
                   error: function (e) {
                       enableButton(button, buttonCaption);
                       alert("Sorry, unexpected error on server. Please, try again later :(");
                   }
               });
    } else {
        alert("Passwords must match and should be more than 3 symbols");
    }
}
function sendFeedback() {
    var text = $('#text').val();

    if (text !== undefined && text.length > 8) {
        var button = $('#sendFeedbackButton');
        var buttonCaption = button.html();
        button.html('Sending ...');
        button.prop('disabled', true);

        $.ajax({
                   url: "/feedback",
                   type: "POST",
                   data: {'text': text},
                   success: function (response) {
                       enableButton(button, buttonCaption);
                       if (response.responseObject == true) {
                           alert("Thank you for your feedback! :)");
                       } else{
                           alert("Sorry, cannot send feedback right now :( Please, try again later");
                       }
                   },
                   error: function (e) {
                       enableButton(button, buttonCaption);
                       alert("Sorry, unexpected error on server. Please, try again later :(");
                   }
               });
    } else {
        alert("Cannot send feedback. Text should have at least 8 characters");
    }
}

function enableButton(button, buttonCaption) {
    button.prop('disabled', false);
    button.html(buttonCaption);
}
