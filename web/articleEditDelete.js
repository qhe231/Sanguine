window.addEventListener("load", function() {
    //init
    const editButtons = document.querySelectorAll(".editButton");
    for (let i = 0; i < editButtons.length; i++)
        editButtons[i].addEventListener("click", beginEdit);
    const deleteButtons = document.querySelectorAll(".deleteButton");
    for (let i = 0; i < deleteButtons.length; i++)
        deleteButtons[i].addEventListener("click", deleteArticle);

    let initialText;

   function beginEdit() {
       const articleId = this.id.replace('edit-','');
       initialText = document.querySelector(`#content-${articleId}`).innerHTML;

       const content = document.querySelector(`#content-${articleId}`);
       tinymce.init({
           selector: `#${content.id}`,
           inline: true
       });
       content.classList.add("currentlyEditing");
       content.focus();

       setButtonsForEditing(articleId);
   }

   function submitEdit() {
       const articleId = this.id.replace('edit-','');

       const text = tinymce.activeEditor.getContent({format: 'html'});
       tinymce.activeEditor.remove();

       let xhr = new XMLHttpRequest();
       xhr.open('POST', `./EditArticle?articleId=${articleId}`);
       xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
       xhr.send(encodeURI('content=' + text));

       document.querySelector(`#content-${articleId}`).classList.remove("currentlyEditing");
       resetButtonsFromEditing(articleId);
   }

    function cancelEdit() {
        const articleId = this.id.replace('delete-','');
        tinymce.activeEditor.remove();
        const content = document.querySelector(`#content-${articleId}`);
        content.innerHTML = initialText;
        content.classList.remove("currentlyEditing");
        resetButtonsFromEditing(articleId);
    }

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

   function deleteArticle() {
       const articleId = this.id.replace('delete-','');
       window.location.href = `./DeleteArticle?articleId=${articleId}`;
   }
});