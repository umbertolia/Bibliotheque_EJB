<p>Demo appelant les services d'un EJB en remote ou en local
<p> La demo contient un projet EAR contenant :
<li>un module EJB avec la partie metier
	<ul>
		<li>un intercepteur initialisant l'EJB</li>
	</ul>
</li>

<li>un module WEB 
	<ul> 
		<li> la partie web utilise une servlet de base</li>
		<li> le client web appelle la servlet via http://localhost:8080/BibliothequeWeb/<urlPatterns></li>
		<li> un service Soap pour acceder aux services de l' EJB</li>
	</ul>
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

<li> un module Standalone permettant de tester l'appel de l'EJB

<p>

<li> Contexte technique :
	<ul> 
		<li> JBOSS AS 7.1</li>
		<li> JAVA 7</li>
		<li> ajout des jars log4j-1.2.17.jar</li>
		<li> eclipse IDE 2018-12 </li>
		<li> la datasource est simulee avec des Maps</li>
		<li> SoapUI pour tester les services Soap</li>
	</ul>
</li>

<li> Solved Issues
	<ul>
		<li> 
			Declarer des fichiers properties/xml depuis le EAR
				<ul> Ajouter les fichiers de conf dans un jar et ajouter le jar dans EAR/lib </ul>
		</li>
		<li> Logs 
			<ul> Afin d'utilise un Log4J perso et de by-passer les logs Jboss, il faut :
				<li> ajouter une exclusion dans jboss-deployment-structure.xml </li>
				<li> ajouter sa conf de logs dans un Jar (ici biblioProperties.jar issu d'un module contenant le log4j.xml) et ajouter ce jar dans EAR/lib </li>
			</ul>
		</li>		
	</ul>
</li>