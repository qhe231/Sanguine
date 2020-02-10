window.addEventListener("load", function () {
    const reactionButtons = document.querySelectorAll(".reactionButton");
    for (let i = 0; i < reactionButtons.length; i++)
        reactionButtons[i].addEventListener("click", react);
    const reactionCounters = document.querySelectorAll(".reactionCounter");

    /**
     * articleList is the list of all articles on the page. If on the index page it will retrieve
     * articles from their articleRow elements; if on the article page it will first add the root article
     * followed by all comments.
     */
    const articleList = [];
    if (document.querySelector(".articleRow") != null) {
        const rows = document.querySelectorAll(".articleRow");
        for (let i = 0; i < rows.length; i++)
            articleList.push(rows[i].id);
    } else {
        articleList.push(document.querySelector(".mainArticle").id);
        const comments = document.querySelectorAll(".comment");
        for (let i = 0; i < comments.length; i++)
            articleList.push(comments[i].id);
    }

    /**
     * Current reactions are pulled when the page loads, then refreshed automatically every 5 seconds thereafter.
     */
    checkCurrentReactions();
    let timer = setInterval(checkCurrentReactions, 5000);

    /**
     * This function is called when a like/dislike button is pressed on an article. It forwards the articleId
     * and reaction type to the NewReaction servlet, then refreshes all reaction counts on the page.
     */
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

    /**
     * This function queries the ArticleReaction servlet to obtain a count of all reactions to the articles
     * on this page, then updates the displayed counts.
     */
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