window.addEventListener("load", function () {
    const commentsArea = document.querySelector("#comments");
    commentsArea.style.display = 'none';
    const commentsButton = document.querySelector("#commentsButton");
    commentsButton.addEventListener("click", function () {
        if (commentsArea.style.display === 'none') {
            commentsArea.style.display = 'inline';
            commentsButton.innerHTML = null;
            commentsButton.innerHTML = 'Hide All Comments';
        } else {
            commentsArea.style.display = 'none';
            commentsButton.innerHTML = null;
            commentsButton.innerHTML = 'Show All Comments';
        }
    });
});