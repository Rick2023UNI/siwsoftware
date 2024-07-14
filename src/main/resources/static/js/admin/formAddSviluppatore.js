function mostraNascondiInformazione() {
	var informazione=document.getElementById("informazione");
	if (informazione.classList.contains("nascosto")) {
		informazione.classList.remove("nascosto");
	}
	else {
		informazione.classList.add("nascosto");
	}
}