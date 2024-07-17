
$(document).ready(function() {
var stelle=document.getElementsByClassName("selezionate");
	//Impostazione stelle selezionate nelle varie recensioni
	for (var i = 0; i < stelle.length; i++) {
		stelle[i].classList.add("selezionate-"+(stelle[i].getAttribute("stelle")/5*100))
	}
});