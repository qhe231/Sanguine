window.addEventListener("load", function () {


    function newComment() {
        if (tinymce.activeEditor != null)


        //const

        const textArea = document.createElement("textarea");
        newCommentDiv.appendChild(textArea);
        tinymce.init({ selector: "#textArea" });

        const submitButton = document.createElement("button");
        const cancelButton = document.createElement("button");
    }

    function submitComment() {

    }

    function cancelComment() {

    }
});