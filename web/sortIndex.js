window.addEventListener("load", function() {
    const sortingSelector = document.querySelector("#sortingSelector");
    console.log(getSortingMethod());
    sortingSelector.value = getSortingMethod();

    sortingSelector.addEventListener("change", changeSorting);

    function changeSorting() {
        document.cookie = `sortingMethod=${this.value}`;
        window.location.href = `./index`
    }

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