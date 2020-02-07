window.addEventListener("load", function () {
    const reactionButtons = document.querySelectorAll(".reactionButton");
    for (let i = 0; i < reactionButtons.length; i++)
        reactionButtons[i].addEventListener("click", react);
    const reactionCounters = document.querySelectorAll(".reactionCounter");


    const articleList = [];
    const comments = document.querySelectorAll(".comment");
    if (comments.length > 0) {
        articleList.push(document.querySelector(".mainArticle").id);
        for (let i = 0; i < comments.length; i++)
            articleList.push(comments[i].id);
    }
    else {
        const rows = document.querySelectorAll(".articleRow");
        for (let i = 0; i < rows.length; i++)
            articleList.push(rows[i].id);
    }

    checkCurrentReactions();
    let timer = setInterval(checkCurrentReactions, 5000);

    async function react() {
        const articleId = this.id.substring(this.id.indexOf("-") + 1);
        let reaction;
        if (!this.checked)
            reaction = 0;
        else if (this.id.substring(0, this.id.indexOf("-")) == "like")
            reaction = 1;
        else
            reaction = 2;

        const promise = await fetch(`./react?articleId=${articleId}&reaction=${reaction}`, {
            method: 'POST'
        });
        await checkCurrentReactions();
    }

    async function checkCurrentReactions() {

        let headers = new Headers();
        headers.append('Content-Type', 'text/html');
        const promise = await fetch("./articleReaction", {
            method: 'POST',
            headers: headers,
            body: articleList
        });

        const currentReactions = await promise.json();

        for (let i = 0; i < reactionCounters.length; i++) {
            const j = Math.floor(i / 2);

            if (reactionButtons.length > 0) {
                if (currentReactions[j].user == -1)
                    reactionButtons[i].disabled = true;
                else if (currentReactions[j].user > 0 && ((currentReactions[j].user - 1) % 2 == i % 2))
                    reactionButtons[i].checked = true;
                else
                    reactionButtons[i].checked = false;
            }

            if (i % 2 == 0)
                reactionCounters[i].innerText = currentReactions[j].likes;
            else
                reactionCounters[i].innerText = currentReactions[j].dislikes;
        }
    }
});