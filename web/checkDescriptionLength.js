/**
 * This function ensures that a user's profile text is within the maximum character count.
 */
function checkLength() {
    const profileText = document.querySelector("#profileText");
    const profilePrompt = document.querySelector("#profilePrompt");
    const submitButton = document.querySelector("#submit");

    if (profileText.value.length > 300) {
        profilePrompt.style.color = 'red';
        profilePrompt.innerHTML = "<p>Your profile is too long. Please shorten it.</p>";
        submitButton.disabled = true;
    } else {
        profilePrompt.innerHTML = null;
        submitButton.disabled = false;
    }

}