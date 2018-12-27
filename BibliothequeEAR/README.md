<p>Demo appelant les services d'un EJB en remote ou en local
<p> La demo contient un projet EAR contenant :
<li>un module EJB avec la partie métier
	<ul>
	<li>un intercepteur initialisant l'EJB</li>
	</ul>
</li>
<li>un module WEB 
	<ul> 
		<li> la partie web utilise une servlet de base</li>
		<li> le client web appelle la servlet via http://localhost:8080/BibliothequeWeb/<urlPatterns></li>
		<li> un service Soap pour acceder aux services de l' EJB</li>
	<ul>
</li>


<p>
<li> La demo permet de simuler l'emprunt de livres d'une bibliothèque :
	<ul> 
		<li> rechercher / ajouter un article</li>
		<li> rechercher / ajouter une personne</li>
		<li> emprunter / restituer des articles</li>
		<li>etc...</li>
	</ul>
</li>

<li> un module ClientBibliothequeEJB permet de tester l'appel de l'EJB

<li> Contexte technique :
	<ul> 
		<li> JBOSS AS 7.1</li>
		<li> JAVA 7</li>
		<li> ajout des jars jboss-logging-3.1.0.ga.jar et javax.interceptor-api-1.2.jar</li>
		<li> IDE : eclipse</li>
		<li> la datasource est simulée avec des Maps</li>
		<li> SoapUI pour tester l' EJB</li>
	</ul>
</li>