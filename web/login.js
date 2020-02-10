/**
 * This function retrieves the ID token from Google, then forwards it through to the LoginGoogle servlet.
 * When the servlet responds, if login was successful it sends the user to the new URL.
 */
async function googleSignIn(googleUser) {
    let id_token = googleUser.getAuthResponse().id_token;
    let headers = new Headers();
    headers.append("Content-Type", "text/plain")
    let promise = await fetch(`./LoginGoogle`, {
        method: 'POST',
        header: headers,
        body: id_token,
        redirect: "follow"
    }).then(response => {
        if (response.redirected)
            window.location.href = response.url;
    }).catch(function (err) {
        console.info(err + " url: " + url);
    });
}