$(document).ready(function() {

	// Quando l'utente clicca la x, chiude il avviso
	document.getElementsByClassName("chiudi")[0].onclick = function() {
		nascondiMessaggi();
		document.getElementById("avviso").style.display = "none";
	}
	document.getElementById("annulla").onclick = function() {
		document.getElementById("avviso").style.display = "none";
		nascondiMessaggi();
	}

	// Quando l'utente clicca fuori dal avviso, chiudilo
	window.onclick = function(event) {
		if (event.target == document.getElementById("avviso")) {
			document.getElementById("avviso").style.display = "none";
			nascondiMessaggi();
		}
	}
})

function nascondiMessaggi() {
	var avviso = document.getElementsByClassName("body-avviso")[0];
	var Elementi_p = avviso.getElementsByTagName("p");
	for (let i = 0; i < Elementi_p.length; i++) {
		Elementi_p[i].classList.add("nascosto");
	}

	var Elementi_span = avviso.getElementsByTagName("span");
	for (let i = 0; i < Elementi_span.length; i++) {
		console.log(Elementi_span[i]);
		if (Elementi_span[i].getElementsByTagName("button").length == 0) {
			Elementi_span[i].classList.add("nascosto");
		}
	}
}

// Quando l'utente clicca sull'icona, apri l'avviso
// se sono necessari più avvisi per la pagina è sufficiente
// creare più paragrafi (<p>) con id ad es.
// messaggio_1, messaggio_2 etc.
// Stessa cosa per gli span con id ad es.
// span_1, span_2 etc.
// Aggiungere anche la classe "nascosto" al tag
// se non viene passato un id agirà sugli elementi di default
function verifica(link, id = "") {
	if (id != "") {
		console.log("messaggio_" + id);
		var p = document.getElementById("messaggio_" + id);
		p.classList.remove("nascosto");

		console.log("span_" + id);
		var span = document.getElementById("span_" + id);
		span.classList.remove("nascosto");
	} else {
		var avviso = document.getElementsByClassName("body-avviso")[0];
		var Elemento_p = avviso.getElementsByTagName("p")[0];
		var Elemento_span = avviso.getElementsByTagName("span")[0];
		Elemento_p.classList.remove("nascosto");
		Elemento_span.classList.remove("nascosto");
	}
	document.getElementById("conferma").setAttribute("onclick", "location.href='" + link + "'");
	document.getElementById("avviso").style.display = "block";
}