/**
 * This function displays the form in question and hides the Change button.
 */
function showForm(formDivId, btnId) {
    const formDiv = document.querySelector(`#${formDivId}`);
    formDiv.style.display = "initial";

    const btn = document.querySelector(`#${btnId}`);
    btn.style.display = "none";
}

/**
 * This function hides the form in question and displays the Change button.
 */
function hideForm(formDivId, btnId) {
    const formDiv = document.querySelector(`#${formDivId}`);
    formDiv.style.display = "none";

    const btn = document.querySelector(`#${btnId}`);
    btn.style.display = "initial";
}

/**
 * This function displays acknowledgement of whether the user's provided passwords match. If they do not,
 * the Submit button is disabled.
 */
function checkPassword() {
    const messageDiv = document.querySelector("#message");
    const submitBtn = document.querySelector("#submitPassword");

    if (document.getElementById("password").value === document.getElementById("confirmPassword").value) {
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
 * This function sends the user's requested username to UsernameServlet. If the username is available,
 * it displays a success message; otherwise it displays an error message and disables the submit button.
 */
async function checkUserName() {
    const possibleUserName = document.querySelector("#userName").value;

    const response = await fetch(`./UserNameList?possibleUserName=${possibleUserName}`);
    const json = await response.json();

    const messageDiv = document.querySelector("#userNameMessage");
    const submitBtn = document.querySelector("#submitUser");

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
