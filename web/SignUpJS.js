function checkPassword() {
    if (document.getElementById("password").value == document.getElementById("confirmPassword").value) {
        document.getElementById("message").style.color = 'green';
        document.getElementById("message").innerHTML = "matching";
        document.getElementById("submit").disabled = false;
    } else {
        document.getElementById("message").style.color = "red";
        document.getElementById("message").innerHTML = "not matching";
        document.getElementById("submit").disabled = true;
    }
}

async function checkUserName() {

    console.log('checkusername called')

    const possibleUserName = document.querySelector("#userName").value;
    console.log(possibleUserName);

    const response = await fetch(`./UserNameList?possibleUserName=${possibleUserName}`);

    console.log('$$$$$$$$$$$$$$$$$$$$$$$$');
    console.log(response);

    const json = await response.json();

    console.log('this is json', json);
    console.log('is this good: ' + json.isAvailable);

    if (json.isAvailable) {
        document.getElementById("userNameMessage").style.color = 'green';
        document.getElementById("userNameMessage").innerHTML = "Username available";
        document.getElementById("submit").disabled = false;
    } else {
        document.getElementById("userNameMessage").style.color = "red";
        document.getElementById("userNameMessage").innerHTML = "Username not available";
        document.getElementById("submit").disabled = true;
    }


}
