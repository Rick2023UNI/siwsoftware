function showHideCommentForm() {
  var form = document.getElementById("commentForm");
  var button = document.getElementById("showHideCommentForm");
  if (form.style.display === "none") {
	button.textContent="Non recensisci";
    form.style.display = "block";
  } else {
	  button.textContent="Recensisci";
    form.style.display = "none";
  }
} 