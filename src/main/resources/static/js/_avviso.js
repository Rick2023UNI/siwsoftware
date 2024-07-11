$(document).ready(function() {

	// Quando l'utente clicca la x, chiude il avviso
	document.getElementsByClassName("chiudi")[0].onclick = function() {
		document.getElementById("avviso").style.display = "none";
	}
	document.getElementById("annulla").onclick = function() {
		document.getElementById("avviso").style.display = "none";
	}

	// Quando l'utente clicca fuori dal avviso, chiudilo
	window.onclick = function(event) {
		if (event.target == document.getElementById("avviso")) {
			document.getElementById("avviso").style.display = "none";
		}
	}
})

// Quando l'utente clicca sull'icona, apri il avviso
function verifica(link) {
	document.getElementById("conferma").setAttribute("onclick", "location.href='" + link + "'");
	document.getElementById("avviso").style.display = "block";
}