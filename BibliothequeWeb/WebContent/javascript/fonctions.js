/**
 * validation du formulaire Personne
 */
function validerFormPersonne(action) {
	var idElmt = document.getElementById("id");
	var nomElmt = document.getElementById("nom");
	var prenomElmt = document.getElementById("prenom");
	idElmt.required = true;
	if (action == "ajouter" || action == "modifier") {
		nomElmt.required = true;
		prenomElmt.required = true;
	}
	// controles de surface avec messages persos
	elementMustHaveIntegerValue(idElmt);
	elementMustHaveStringValue(nomElmt);
	elementMustHaveStringValue(prenomElmt);
}

/**
 * validation du formulaire Article
 */
function validerFormArticle(action) {
	var idElmt = document.getElementById("reference");
	var intituleElmt = document.getElementById("intitule");
	
	idElmt.required = true;
	intituleElmt.required = true;
	
	if (action == "consulter") {
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

/**
 * validation du formulaire Emprunt
 */
function validerFormEmprunt(action) {
	var idElmt = document.getElementById("reference");
	
	idElmt.required = true;
	// controles de surface
	elementMustHaveIntegerValue(idElmt);
}


/***************************************************************************
 * CONTROLES DE SURFACE
 */
/**************************************************************************/

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

function disableFormValidationAndSubmit(formName) {
	var elmt =  document.getElementById(formName);
	if (elmt != null) {
		elmt.noValidate = true;
		elmt.submit();
	}
}