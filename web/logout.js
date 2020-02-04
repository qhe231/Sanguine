window.addEventListener("load", function () {

    gapi.load('auth2', function() {
        gapi.auth2.init();
    });

    const logoutButton = document.querySelector("#logout");
    if (logoutButton != null)
        logoutButton.addEventListener("click", logout);

    async function logout() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut();/*.then(async function () {
            await fetch('./logout')
                .then(redirect => {
                    if (response.redirected)
                        window.location.href = response.url;
                });
        });*/
    }
});