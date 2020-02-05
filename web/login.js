async function googleSignIn(googleUser) {
    let profile = googleUser.getBasicProfile();

    let id_token = googleUser.getAuthResponse().id_token;
    let headers = new Headers();
    headers.append("Content-Type", "text/plain")
    let promise = await fetch(`./LoginGoogle`, {
        method: 'POST',
        header: headers,
        body: id_token,
        redirect: "follow"
    }).then(response=> {
        if (response.redirected)
            window.location.href = response.url;
    }).catch(function(err) {
        console.info(err + " url: " + url);
    });
}