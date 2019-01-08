function loadEvents(idAdherent) {
	if (idAdherent != null) {
		var idElmt = document.getElementById("id");
		if (idElmt != null) {
			//idElmt.readOnly = true;			
		}
	}
}


function validerFormPersonne(elmtAction) {
	var idElmt = document.getElementById("id");
	var nomElmt = document.getElementById("nom");
	var prenomElmt = document.getElementById("prenom");
	idElmt.required = true;
	if (elmtAction.value == "ajouter" || elmtAction.value == "modifier") {
		nomElmt.required = true;
		prenomElmt.required = true;
	}
	// controles de surface avec messages persos
	elementMustHaveIntegerValue(idElmt);
	elementMustHaveStringValue(nomElmt);
	elementMustHaveStringValue(prenomElmt);
}

function validerFormArticle(elmtAction) {
	var refElmt = document.getElementById("reference");

	var intituleElmt = document.getElementById("intitule");

	if (elmtAction.value == "consulter") {
		if (intituleElmt.value == "") {
			refElmt.required = true;
			intituleElmt.required = false;
		} else if (refElmt.value == "") {
			intituleElmt.required = true;
		}
	} else if (elmtAction.value == "ajouter") {
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
	/*
	 * if (form.checkValidity()) { form.submit(); }
	 */
}


function elementMustHaveIntegerValue(element) {
	if (element != null) {
		if (element.value == "") {
			element.setCustomValidity("ID ne doit pas etre vide");
		}
		else if (isNaN(element.value)) {
			element.setCustomValidity("ID  doit etre numerique");
		}
		else {
			var number = Number(element.value);
			if (!Number.isInteger(number)) {
				element.setCustomValidity("ID  doit etre un entier");
			}
		}
	}
	
}

function elementMustHaveStringValue(element) {
	if (element != null) {
		if (element.required && element.value == "") {
			element.setCustomValidity("Le nom doit être renseigné");
		}
		else if (element.required && element.validity.patternMismatch) {
			element.setCustomValidity("Le nom doit être un texte");
		}
	}
}