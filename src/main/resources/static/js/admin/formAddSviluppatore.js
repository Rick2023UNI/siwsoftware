function mostraNascondiInformazione() {
	var informazione=document.getElementById("informazione");
	if (informazione.classList.contains("nascosto")) {
		informazione.classList.remove("nascosto");
	}
	else {
		informazione.classList.add("nascosto");
	}
}


$(document).ready(function() {
	
	var dragEls = document.getElementsByClassName('scroll-orizzontale');
	var scrolls = document.getElementsByClassName('sviluppatori');
	
	for (var i=0;i<dragEls.length;i++) {
		var dragEl = dragEls[i];
		var scroll = scrolls[i+1];
		inizializzaScroll(dragEl, scroll);
	}
	});