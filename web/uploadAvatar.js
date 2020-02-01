window.addEventListener("load", function () {
    document.querySelector("#ownAvatarFile").onchange = function (event) {
        const ownAvatarPic = document.querySelector("#ownAvatarPic");
        const picURL = URL.createObjectURL(event.target.files[0]);

        const image = document.createElement("img");
        image.src = picURL;
        image.width = 64;
        ownAvatarPic.innerHTML = null;
        ownAvatarPic.appendChild(image);
    }
});