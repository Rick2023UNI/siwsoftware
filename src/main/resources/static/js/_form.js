function setFocus(on) {
	var element = document.activeElement;
	if (on) {
		setTimeout(function() {
			element.parentNode.classList.add("focus");
		});
	} else {
		let box = document.querySelector(".input-div");
		box.classList.remove("focus");
		$("input").each(function() {
			var $input = $(this);
			var $parent = $input.closest(".input-div");
			if ($input.val()) $parent.addClass("focus");
			else $parent.removeClass("focus");
		});
	}
}

$(document).ready(function() {
	$('#mostra').click(mostraPassword);
});

function mostraPassword() {
	document.getElementById("nascondi").style.display = 'block';
	document.getElementById("mostra").style.display = 'none';
	document.getElementById("password").type = "text";

	$('#mostra').off("click", mostraPassword);
	$('#nascondi').click(nascondiPassword);
}

function nascondiPassword() {
	document.getElementById("nascondi").style.display = 'none';
	document.getElementById("mostra").style.display = 'block';
	document.getElementById("password").type = "password";

	$('#mostra').off("click", mostraPassword);
	$('#mostra').click(mostraPassword);
}