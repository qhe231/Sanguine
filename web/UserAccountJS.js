// Make form visible, hide button
function showForm(formDivId, btnId) {
    const formDiv = document.getElementById(formDivId);
    formDiv.style.display = "initial";

    const btn = document.getElementById(btnId);
    btn.style.display = "none";
}

// Make form invisible, display button
function hideForm(formDivId, btnId) {
    const formDiv = document.getElementById(formDivId);
    formDiv.style.display = "none";

    const btn = document.getElementById(btnId);
    btn.style.display = "initial";
}

// If the passwords match, display a success message, else display error message and disable submit button
function checkPassword() {
    if (document.getElementById("password").value === document.getElementById("confirmPassword").value) {
        document.getElementById("message").style.color = 'green';
        document.getElementById("message").innerHTML = "Passwords match";
        document.getElementById("submitPassword").disabled = false;
    } else {
        document.getElementById("message").style.color = "red";
        document.getElementById("message").innerHTML = "Passwords do not match";
        document.getElementById("submitPassword").disabled = true;

    }
}

/** Send the possible username to UsernameServlet
 If the username is available, display a success message, else display error message and disable submit button **/
async function checkUserName() {
    const possibleUserName = document.querySelector("#userName").value;

    const response = await fetch(`./UserNameList?possibleUserName=${possibleUserName}`);

    const json = await response.json();

    if (json.isAvailable) {
        document.getElementById("userNameMessage").style.color = 'green';
        document.getElementById("userNameMessage").innerHTML = "Username available";
        document.getElementById("submitUser").disabled = false;
    } else {
        document.getElementById("userNameMessage").style.color = "red";
        document.getElementById("userNameMessage").innerHTML = "Username not available";
        document.getElementById("submitUser").disabled = true;
    }

}
