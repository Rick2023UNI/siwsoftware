function showHideCommentForm() {
	var recensione = document.getElementById("recensione-utente-corrente");
	
  var form = document.getElementById("commentForm");
  var showButton = document.getElementById("showCommentForm");
  var hideButton = document.getElementById("hideCommentForm");
  if (form.style.display == "none") {
	//Nasconde recensione se presente
	if (recensione!=null) {
		recensione.style.display = "none";
	}
	
	if (hideButton!=null)
		hideButton.style.display="block";
	if (showButton!=null)
	showButton.style.display="none";
	
    form.style.display = "block";
  } else {
	//Nasconde recensione se presente
	if (recensione!=null) {
		recensione.style.display = "";
	}
	
	if (hideButton!=null)
		hideButton.style.display="none";
	if (showButton!=null)	
		showButton.style.display="block";
    form.style.display = "none";
  }
  stelleSelezionate(document.getElementById("numeroStelle").value);
} 

$(document).ready(function() {
	var stelle=document.getElementsByClassName("selezionate");
	//Impostazione stelle selezionate nelle varie recensioni
	for (var i = 0; i < stelle.length; i++) {
		stelle[i].classList.add("selezionate-"+(stelle[i].getAttribute("stelle")/5*100))
	}
	
	//Impostazione larghezza barre percentuali recensioni per stelle
	var barreProgresso=document.getElementsByClassName("progresso");
	for (var i = 0; i < barreProgresso.length; i++) {
		var barraProgresso=barreProgresso[i];
		barraProgresso.style.width=barraProgresso.getAttribute("progresso")+"%";
	}
});

function stelleSelezionate(numero) {
	//Impostazione valore inputbox
	document.getElementById("numeroStelle").value=numero;
	var stella;
	
	//Cambiamento colore stelle non selezionate a grigio
	for(i=5;i>numero;i--) {
		stella=document.getElementById("stella-"+i);
		stella.classList.remove('selezionata');
		stella.classList.add('non-selezionata');
	}
	
	//Cambiamento colore stelle selezionate ad oro
	for(i=numero;i>0;i--) {
		stella=document.getElementById("stella-"+i);
		stella.classList.remove('non-selezionata');
		stella.classList.add('selezionata');
	}
}

function annulla (){
	//Reimposta titolo
	var titolo = document.getElementById("titolo");
	titolo.value = titolo.defaultValue;
	
	//Reimposta descrizione
	var descrizione = document.getElementById("descrizione");
	descrizione.value = descrizione.defaultValue;
	
	//Reimposta valore input del numero stelle
	var numeroStelle  = document.getElementById("numeroStelle");
	numeroStelle.value = numeroStelle.defaultValue;
	
	//Aggiornamento delle stelle selezionate
	stelleSelezionate(numeroStelle.defaultValue);
	
	showHideCommentForm();
}