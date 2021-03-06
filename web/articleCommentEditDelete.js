window.addEventListener("load", function () {

    const editButtons = document.querySelectorAll(".editButton");
    for (let i = 0; i < editButtons.length; i++)
        editButtons[i].addEventListener("click", beginEdit);
    const deleteButtons = document.querySelectorAll(".deleteButton");
    for (let i = 0; i < deleteButtons.length; i++)
        deleteButtons[i].addEventListener("click", deleteArticle);
    const addCommentButtons = document.querySelectorAll(".addComment");
    for (let i = 0; i < addCommentButtons.length; i++)
        addCommentButtons[i].addEventListener("click", newComment);

    let initialText;

    /**
     * This function is called when the Edit button is pressed. It sets the div containing the article in question
     * to be a TinyMCE editor and caches its current content.
     */
    async function beginEdit() {
        const articleId = this.id.replace('edit-', '');
        initialText = document.querySelector(`#content-${articleId}`).innerHTML;

        for (let i = 0; i < addCommentButtons.length; i++)
            addCommentButtons[i].style.display = "none";

        const content = document.querySelector(`#content-${articleId}`);
        await tinymce.init({
            selector: `#${content.id}`,
            inline: true,
            plugins: 'image autoresize',
            image_description: false,
            image_dimensions: false,
            image_uploadtab: true,
            images_upload_url: './articleImage'
        });

        setButtonsForEditing(articleId);
        tinymce.activeEditor.focus();
    }

    /**
     * This function is called when the Submit button is pressed on an article which is being edited. It removes
     * the TinyMCE editor from the div and forwards the updated article content to the EditArticle Servlet.
     */
    async function submitEdit() {
        const articleId = this.id.replace('edit-', '');

        for (let i = 0; i < addCommentButtons.length; i++)
            addCommentButtons[i].style.display = "initial";

        const text = tinymce.activeEditor.getContent({format: 'html'});
        tinymce.activeEditor.remove();

        let headers = new Headers();
        headers.append('Content-Type', 'text/html');
        await fetch(`./EditArticle?articleId=${articleId}`, {
            method: 'POST',
            headers: headers,
            body: text
        });

        document.querySelector(`#content-${articleId}`).classList.remove("currentlyEditing");
        resetButtonsFromEditing(articleId);
    }

    /**
     * This function is called when the Cancel button is pressed on an article which is being edited. It removes
     * the TinyMCE editor from the div and sets the div's content back to the cached copy of its original content.
     */
    function cancelEdit() {

        for (let i = 0; i < addCommentButtons.length; i++)
            addCommentButtons[i].style.display = "initial";

        const articleId = this.id.replace('delete-', '');
        tinymce.activeEditor.remove();
        const content = document.querySelector(`#content-${articleId}`);
        content.innerHTML = initialText;
        resetButtonsFromEditing(articleId);
    }

    /**
     * These two functions reconfigure the Edit/Delete buttons into Submit/Cancel and vice versa.
     */
    function setButtonsForEditing(articleId) {
        const editSubmit = document.querySelector(`#edit-${articleId}`);
        editSubmit.innerText = "Submit Changes";
        editSubmit.removeEventListener("click", beginEdit);
        editSubmit.addEventListener("click", submitEdit);

        const deleteCancel = document.querySelector(`#delete-${articleId}`);
        deleteCancel.innerText = "Cancel";
        deleteCancel.removeEventListener("click", deleteArticle);
        deleteCancel.addEventListener("click", cancelEdit);
    }

    function resetButtonsFromEditing(articleId) {
        const editSubmit = document.querySelector(`#edit-${articleId}`);
        editSubmit.innerText = "Edit";
        editSubmit.removeEventListener("click", submitEdit);
        editSubmit.addEventListener("click", beginEdit);

        const deleteCancel = document.querySelector(`#delete-${articleId}`);
        deleteCancel.innerText = "Delete";
        deleteCancel.removeEventListener("click", cancelEdit);
        deleteCancel.addEventListener("click", deleteArticle);
    }

    /**
     * This function is called when the Delete button is pressed on an article. It prompts the user to confirm
     * deletion, then forwards the articleId to the DeleteArticle servlet once confirmation is received.
     */
    function deleteArticle() {
        if (confirm("Are you sure you want to delete?")) {
            const articleId = this.id.replace('delete-', '');
            window.location.href = `./DeleteArticle?articleId=${articleId}`;
        }
    }

    let hiddenCommentBtn;

    /**
     * This function is called when the Add Comment button is pressed. Only one comment writing form can be open
     * on the page at a time, so it first closes any existing comments-in-progress then creates a new editor.
     */
    async function newComment() {
        //if there's another comment form open, close it before continuing
        if (document.querySelector("#newCommentForm") != null)
            cancelNewComment();

        //hide this button
        hiddenCommentBtn = this;
        this.style.display = "none";

        //create the content structure
        const newCommentForm = document.createElement("form");
        newCommentForm.action = "./postArticle";
        newCommentForm.method = "post";
        newCommentForm.id = "newCommentForm";
        this.parentNode.appendChild(newCommentForm);

        const parentId = document.createElement("input");
        parentId.type = "hidden";
        parentId.name = "parentId";
        parentId.value = this.id.replace("comment-", "");
        parentId.value = parentId.value.substring(0, parentId.value.indexOf("-"));
        console.log("parent: " + parentId.value);
        newCommentForm.appendChild(parentId);
        const rootArticle = document.createElement("input");
        rootArticle.type = "hidden";
        rootArticle.name = "rootArticle";
        rootArticle.value = this.id.substring(this.id.indexOf("-") + 1);
        rootArticle.value = rootArticle.value.substring(rootArticle.value.indexOf("-") + 1);
        newCommentForm.appendChild(rootArticle);
        console.log("root: " + rootArticle.value);

        const title = document.createElement("input");
        title.name = "title";
        parentTitle = document.querySelector(`#title-${parentId.value}`).innerText;
        title.value = `re: ${parentTitle}`;
        newCommentForm.appendChild(title);
        title.required = true;
        const content = document.createElement("textarea");
        content.id = "newComment";
        content.name = "content";
        newCommentForm.appendChild(content);

        const submitButton = document.createElement("input");
        submitButton.type = "submit";
        submitButton.name = "submit";
        submitButton.id = "submitButton";
        submitButton.value = "Submit";
        newCommentForm.appendChild(submitButton);
        const cancelButton = document.createElement("button");
        cancelButton.innerText = "Cancel";
        cancelButton.name = "cancel";
        cancelButton.id = "cancelButton";
        newCommentForm.appendChild(cancelButton);
        cancelButton.addEventListener("click", cancelNewComment);

        //text editor
        await tinymce.init({
            selector: '#newComment',
            plugins: 'image autoresize',
            image_description: false,
            image_dimensions: false,
            image_uploadtab: true,
            images_upload_url: './articleImage'
        });
        tinymce.activeEditor.focus();
    }

    /**
     * This function is called when the Cancel button is pressed on a new comment.
     */
    function cancelNewComment() {
        tinymce.activeEditor.remove();
        const newCommentForm = document.querySelector("#newCommentForm");
        newCommentForm.parentNode.removeChild(newCommentForm);

        hiddenCommentBtn.style.display = "block";
        hiddenCommentBtn = null;
    }
});