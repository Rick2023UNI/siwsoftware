$(document).ready(function() {
	$('#input-immagine').change(aggiorna);
	controllaThumbnail();
});

function controllaThumbnail() {
	var thumbnail = document.getElementById("thumbnail");
	//Se è stato inizializzato l'attributo src
	//allora c'è un'immagine
	if (thumbnail.getAttribute("src") != null && thumbnail.getAttribute("src") != "") {
		thumbnail.style.display = "block";
	}
}

function aggiorna() {
	aggiornaMiniatura(this);
}

function aggiornaMiniatura(fileInput) {
	//Selezione del file
	file = fileInput.files[0];

	reader = new FileReader();

	//Aggiornamento dell'immagine 
	reader.onload = function(e) {
		$('#thumbnail').attr('src', e.target.result);
	};
	document.getElementById("thumbnail").style.display = "block";
	reader.readAsDataURL(file);
}