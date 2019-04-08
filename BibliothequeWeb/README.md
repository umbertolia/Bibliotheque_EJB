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
<li> La demo permet de simuler l'emprunt de livres d'une bibliothÃ¨que :
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
		<li> Pour les tests la datasource est simulee avec des Maps</li>
		<li> Hibernate 4.2.21  + Mysql 5.1.6 en guisee de sgbdr</li>
		<li> SoapUI pour tester les services Soap</li>
	</ul>
</li>

<li> Solved Issues
	<ul>
		<li>
			Clé d'une map non propagée en base 
			<ul>
				jBoss 7.1 embarque hibernate 4.0.1 par defaut. Avec cette version, lors de l'ajout d'un element dans une map, la clé de l'objet n'est pas propagée dans la base (ID_ART_KEYS). Il faut dans ce cas partir de la version 4.2 de hibernate pour que la clé soit présente en base.
			</ul>
		</li>
		<li>
			Dependances Hibernate / dépendance MySql
				<ul>
					JBoss utilise ces propres librairies Hibernate. Pour déclarer la dépendance MySql, il faut :
					<li>
					 	créer les dossiers com, mysql, main et créer un module.xml issu de  <jBoss_rep>\modules\com\mysql\main et y déposer le jar mysql-connector-java-5.1.6.jar
					 </li>
					 <li>
					 ajouter la dependance com.mysql dans le module.xml issu de <jBoss_rep>\modules\org.hibernate\main
					 </li>
				</ul>
		</li>
		<li> 
			Declarer des fichiers properties/xml depuis le EAR
				<ul> Ajouter les fichiers de conf dans un jar et ajouter le jar dans EAR/lib OU mettre la conf dans un module et declarer ce module dans le EAR (Ear Module Assembly) 
				</ul>
		</li>
		<li> Logs 
			<ul> Afin d'utilise un Log4J perso et de by-passer les logs Jboss, il faut :
				<li> ajouter une exclusion dans jboss-deployment-structure.xml </li>
				<li> ajouter sa conf de logs dans un Jar (ici biblioProperties.jar issu d'un module contenant le log4j.xml) et ajouter ce jar dans EAR/lib OU mettre la conf dans un module et declarer ce module dans le EAR (Ear Module Assembly) </li>
			</ul>
		</li>		
	</ul>
</li>