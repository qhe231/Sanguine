window.addEventListener("load", function () {
    const commentsArea = document.querySelector("#comments");
    commentsArea.style.display = 'none';
    const commentsButton = document.querySelector("#commentsButton");
    /**
     * This function shows and hides comments on the user account page.
     */
    commentsButton.addEventListener("click", function () {
        if (commentsArea.style.display === 'none') {
            commentsArea.style.display = 'inline';
            commentsButton.innerHTML = 'Hide All Comments';
        } else {
            commentsArea.style.display = 'none';
            commentsButton.innerHTML = 'Show All Comments';
        }
    });
});