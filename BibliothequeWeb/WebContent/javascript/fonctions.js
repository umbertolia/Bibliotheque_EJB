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
	var idElmt = document.getElementById("reference");
	var intituleElmt = document.getElementById("intitule");
	
	idElmt.required = true;
	intituleElmt.required = true;
	
	if (elmtAction.value == "consulter") {
		if (intituleElmt.value == "") {
			idElmt.required = true;
			intituleElmt.required = false;
		} else if (idElmt.value == "") {
			idElmt.required = false;
			intituleElmt.required = true;
		}
	} 

	// controles de surface
	elementMustHaveIntegerValue(idElmt);
	elementMustHaveStringValue(intituleElmt);
}


function elementMustHaveIntegerValue(element) {
	if (element != null && element.required) {
		if (element.value == "") {
			element.setCustomValidity(element.name+" ne doit pas etre vide");
		}
		else if (isNaN(element.value)) {
			element.setCustomValidity(element.name+" doit etre numerique");
		}
		else {
			var number = Number(element.value);
			if (!Number.isInteger(number)) {
				element.setCustomValidity(element.name+" doit etre un entier");
			}
		}
	}
	
}

function elementMustHaveStringValue(element) {
	if (element != null && element.required) {
		if (element.required && element.value == "") {
			element.setCustomValidity(element.name + " doit être renseigné");
		}
		else if (element.required && element.validity.patternMismatch) {
			element.setCustomValidity(element + " doit être un texte");
		}
	}
}