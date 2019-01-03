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
	
	if (intituleElmt.required && !intituleElmt.checkValidity()) {
		intituleElmt.setCustomValidity("L'intitule doit être renseigné");
	}

	var form = document.getElementById("articleForm");
	//
	if (form.checkValidity()) {
		form.submit();
	}
}

function validerFormPersonne(elmtAction) {
	var idElmt = document.getElementById("id");
	var nomElmt = document.getElementById("nom");
	var prenomElmt = document.getElementById("prenom");
	if (elmtAction.value == "se_loguer") {
		idElmt.required = true;
	}
	else if (elmtAction.value == "ajouter") {
		idElmt.required = true;
		nomElmt.required = true;
		prenomElmt.required = true;
	}
	
	if (idElmt.required && !idElmt.checkValidity()) {
		idElmt.setCustomValidity("ID invalide");
	}
	if (nomElmt.required && !nomElmt.checkValidity()) {
		nomElmt.setCustomValidity("Le nom doit être renseigné");
	}
	if (prenomElmt.required && !prenomElmt.checkValidity()) {
		prenomElmt.setCustomValidity("Le prenom doit être renseigné");
	}
	
	var form = document.getElementById("personneForm");
	//
	if (form.checkValidity()) {
		form.submit();
	}
}
