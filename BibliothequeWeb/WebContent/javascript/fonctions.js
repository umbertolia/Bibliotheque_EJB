function validerForm(elmtAction) {
	var refElmt = document.getElementById("reference");
	
	var intituleElmt = document.getElementById("intitule");

	
	if (elmtAction.value == "consulter") {
		if (intituleElmt.value == "") {
			refElmt.required = true;
			intituleElmt.required = false;
		} 
		else if (refElmt.value == "") {
			intituleElmt.required = true;
		} 
	}
	else if (elmtAction.value == "ajouter") {
		refElmt.required = true;
		intituleElmt.required = true;
	}
	
	if (refElmt.required && !refElmt.checkValidity()) {
		refElmt.setCustomValidity("Reference invalide");
	}

	var form = document.getElementById("articleForm");
	//
	if (form.checkValidity()) {
		form.submit();
	}
}
