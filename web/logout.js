window.addEventListener("load", function () {

    gapi.load('auth2', function () {
        gapi.auth2.init();
    });

    const logoutButton = document.querySelector("#logout");
    if (logoutButton != null)
        logoutButton.addEventListener("click", logout);

    /**
     * This function informs Google that the user has signed out, preventing the Google Login widget from
     * automatically signing them back in when they return to the Login page.
     */
    async function logout() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut();
    }
});