window.addEventListener("load", function () {
    const sortingSelector = document.querySelector("#sortingSelector");
    console.log(getSortingMethod());
    sortingSelector.value = getSortingMethod();

    sortingSelector.addEventListener("change", changeSorting);

    /**
     * This function is called when the user selects a value in the sorting dropdown box. Sorting
     * method is stored in the sortingMethod cookie.
     */
    function changeSorting() {
        document.cookie = `sortingMethod=${this.value}`;
        window.location.href = `./index`
    }

    /**
     * This function returns the current sorting method, as defined by the sortingMethod cookie. Defaults to
     * sorting by date if no cookie found.
     */
    function getSortingMethod() {
        const cookies = document.cookie.split("; ");
        for (let i = 0; i < cookies.length; i++) {
            const c = cookies[i].split("=");
            if (c[0] == "sortingMethod")
                return c[1];
        }
        return "sort_date";
    }
})