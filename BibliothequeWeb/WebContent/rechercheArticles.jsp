<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="ISO-8859-1">
	<link href="css/main.css" rel="stylesheet" type="text/css"/>
	<script src="javascript/fonctions.js" type="text/javascript"></script> 
	<title>BIBLIOTHEQUE</title>
	</head>
<body>

<table>
	<tr>
		<td valign="top">
				<form action="personne" method="get" id="personneForm">
				<h2>Personne</h2>
					<table>
					<tr>
							<td>Id</td>
							<td><input type="text" name="id" id="id" value="${id}"></td>
							<td>${errCode}</td> 
						</tr>
						<tr>
							<td>Nom</td>
							<td><input type="text" name="nom" id="nom" value="${nom}"></td>
							<td>${errCode}</td> 
						</tr>
						<tr>
							<td>Prenom</td>
							<td><input type="text" name="prenom" id="prenom" value="${prenom}"></td>
							<td>${errCode}</td>
						</tr>
						<tr>
							<td><input type="submit" name="action" id="action" value="ajouter" onclick="validerFormPersonne(this)"></td>
						</tr>
					</table>
				</form>
		</td>	
		<td valign="top">				
			<form action="article" method="get" id="articleForm">
				<h2>Article</h2>
				<table>
					<tr>
						<td>Référence</td>
						<td><input type="number" min="1" step="1" name="reference" id="reference" value="${reference}"></td>
						<td>${errCode}</td> 
					</tr>
					<tr>
						<td>Intitule</td>
						<td><input type="text"  name="intitule" id="intitule" value="${intitule}"></td>
						<td>${errCode}</td>
					</tr>
					<tr>
						<td><input type="submit" name="action" id="action" value="ajouter" onclick="validerForm(this)"></td>
						<td><input type="submit" name="action" id="action" value="consulter" onclick="validerForm(this)"></td>
						<td><input type="submit" name="action" id="action" value="inventaire"></td>
					</tr>
				</table>
			</form>

		<c:if test="${(articles!=null)||(mtMsgErr!=null)}">
			<p>
			<div id="articles">
				<form>
					<div>
						<h1>Articles :</h1>
						<table class="table-style-two">
							<thead>
								<tr>
									<th>REF</th>
									<th>INTITULE</th>
									<th>DISPONIBILITE</th>
									<th>DATE DE PUBLICATION</th>
								</tr>
							</thead>
							<c:forEach items="${articles}" var="article">
								<tr>
									<td>${article.reference}</td>
									<td>${article.intitule}</td>
									<td>${article.dispo}</td>
									<td>${article.datePublicationParsee}</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</form>
			</div>
		</c:if>
		<c:if test="${inventaire!=null}">
			<p>
			<div id="inventaire">
				<form>
					<table class="table-style-two">
						<thead>
							<tr>
								<th>REF</th>
								<th>INTITULE</th>
								<th>DISPONIBILITE</th>
								<th>DATE DE PUBLICATION</th>
							</tr>
						</thead>
						<c:forEach items="${inventaire}" var="article">
							<tr>
								<td>${article.reference}</td>
								<td>${article.intitule}</td>
								<td>${article.dispo}</td>
								<td>${article.datePublicationParsee}</td>
							</tr>
						</c:forEach>
					</table>
				</form>
			</div>
		</c:if>
		<c:if test="${exception!=null}">
			<p>
			<div id="errors">
				<form>
					<span class="erreur">${exception}</span>
				</form>
			</div>
		</c:if>

		</td>
	</tr>
	</table>	
	


</body>
</html>
