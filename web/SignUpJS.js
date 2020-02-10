/**
 * This function checks whether the passwords entered in both boxes match.
 */
function checkPassword() {
    const messageDiv = document.querySelector("#message");
    const submitBtn = document.querySelector("#submit");

    if (document.querySelector("#password").value === document.querySelector("#confirmPassword").value) {
        messageDiv.style.color = 'green';
        messageDiv.innerHTML = "Passwords match";
        submitBtn.disabled = false;
    } else {
        messageDiv.style.color = "red";
        messageDiv.innerHTML = "Passwords do not match";
        submitBtn.disabled = true;
    }
}

/**
 *  This function sends the possible username to UsernameServlet. If the username is available it displays
 *  a success message; otherwise it displays an error message and disables the submit button.
 */
async function checkUserName() {
    const possibleUserName = document.querySelector("#userName").value;

    const response = await fetch(`./UserNameList?possibleUserName=${possibleUserName}`);
    const json = await response.json();

    const messageDiv = document.querySelector("#userNameMessage");
    const submitBtn = document.querySelector("#submit");

    if (json.isAvailable) {
        messageDiv.style.color = 'green';
        messageDiv.innerHTML = "Username available";
        submitBtn.disabled = false;
    } else {
        messageDiv.style.color = "red";
        messageDiv.innerHTML = "Username not available";
        submitBtn.disabled = true;
    }

}
