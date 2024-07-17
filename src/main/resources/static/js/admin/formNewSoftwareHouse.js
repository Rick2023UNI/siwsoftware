$(document).ready(function() {
	$('#input-immagine').change(aggiorna);
});

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