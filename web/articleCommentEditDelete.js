window.addEventListener("load", function() {

    const editButtons = document.querySelectorAll(".editButton");
    for (let i = 0; i < editButtons.length; i++)
        editButtons[i].addEventListener("click", beginEdit);
    const deleteButtons = document.querySelectorAll(".deleteButton");
    for (let i = 0; i < deleteButtons.length; i++)
        deleteButtons[i].addEventListener("click", deleteArticle);
    const addCommentButtons = document.querySelectorAll(".addComment");
    for (let i = 0; i < editButtons.length; i++)
        addCommentButtons[i].addEventListener("click", newComment);

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

   async function submitEdit() {
       const articleId = this.id.replace('edit-','');

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

   let hiddenCommentBtn;
   let newCommentDiv;

   function newComment() {
       //hide this button
       hiddenCommentBtn = this;
       this.style.display = "none";

       //create the content structure
       newCommentDiv = document.createElement("div");
       newCommentDiv.classList.add('comment');
       newCommentDiv.id = '-1';
       this.parentNode.appendChild(newCommentDiv);

       const title = document.createElement("div");
       title.classList.add("articleTitle");
       newCommentDiv.appendChild(title);
       const author = document.createElement("div");
       author.classList.add("articleAuthor");
       newCommentDiv.appendChild(author);
       author.innerText = "You";
       const postTime = document.createElement("div");
       postTime.classList.add("articlePostTime");
       newCommentDiv.appendChild(postTime);
       postTime.innerText = "(not posted)";
       const content = document.createElement("div");
       content.classList.add("articleContent");
       content.id = "newComment";
       newCommentDiv.appendChild(content);

       const titleTextbox = document.createElement("input");
       titleTextbox.id = "titleBox";
       title.appendChild(titleTextbox);

       const submitButton = document.createElement("button");
       submitButton.classList.add("editButton");
       submitButton.id = this.id.replace("comment", "submit");
       submitButton.innerText = "Submit";
       newCommentDiv.appendChild(submitButton);
       submitButton.addEventListener("click", submitNewComment);
       const cancelButton = document.createElement("button");
       cancelButton.innerText = "Cancel";
       cancelButton.classList.add("deleteButton");
       newCommentDiv.appendChild(cancelButton);
       cancelButton.addEventListener("click", cancelNewComment);

       //text editor
       tinymce.init({
           selector: '#newComment',
           inline: true
       });
   }

   async function submitNewComment() {
       const parentId = this.id.replace("submit-", "");
       const title = newCommentDiv.querySelector("#titleBox").innerText;
       const content = newCommentDiv.querySelector(".articleContent").innerHTML;
       if (title.length == 0 || content.length == 0)
           return;
       tinymce.activeEditor.remove();

       let headers = new Headers();
       headers.append('Content-Type', 'text/html; charset=UTF-8');
       const promise = await fetch (`./PostArticleServlet?parentArticle=${parentId};title=${title}`, {
           method: 'POST',
           headers: headers,
           body: content
       })
       const articleData = await promise.json();

       newCommentDiv.removeChild(newCommentDiv.querySelector("#titleBox"));
       newCommentDiv.querySelector(".articleTitle").innerText = title;
       newCommentDiv.querySelector(".articleAuthor").innerText = articleData.author;
       newCommentDiv.querySelector(".articlePostTime").innerText = articleData.time;
       newCommentDiv.id = articleData.articleId;
       newCommentDiv.querySelector(".articleContent").innerText = `content-${articleData.articleId}`;
       newCommentDiv.querySelector(".editButton").innerText = `edit-${articleData.articleId}`;
       newCommentDiv.querySelector(".deleteButton").innerText = `delete-${articleData.articleId}`;


       newCommentDiv
   }

   function cancelNewComment() {
       tinymce.activeEditor.remove();
       newCommentDiv.parentNode.removeChild(newCommentDiv);
       newCommentDiv = null;

       hiddenCommentBtn.style.display = "block";
       hiddenCommentBtn = null;
   }
});