<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="WEB-INF/tld/mytags.tld" prefix="mytag"%>
<%@page import="metier.constantes.ActionEnum" %>

<table>
	<tr>
		<td>
			<form action="emprunt" method="get" id="empruntForm">
				<h2>Emprunt</h2>
				<table>
					<tr>
						<td>Adhérent ${adherent.prenom} ${adherent.nom} </td>
					</tr>
					<tr>
						<td>Réf. Article</td>
						<td><input type="text" name="reference" id="reference" oninput="setCustomValidity('')"></td>
					</tr>
					<tr>
						<td><input type="submit" name="action" id="action" value=<mytag:action actionEnum="<%=ActionEnum.EMPRUNTER %>"/> onclick="validerFormEmprunt('<mytag:action actionEnum="<%=ActionEnum.EMPRUNTER %>"/>')"></td>
					</tr>
				</table>
			</form>
		</td>
	</tr>
	
	<c:if test="${(adherent !=null && adherent.emprunts.size() > 0)||(mtMsgErr!=null)}">
	<tr>
		<td>
		 	<div id="emprunts">
				<form>
					<div>
						<h1>Emprunts :</h1>
						<table class="table-style-two">
							<thead>
								<tr>
									<th>REF</th>
									<th>INTITULE</th>
									<th>DISPONIBILITE</th>
									<th>DATE DE PUBLICATION</th>
									<th></th>
								</tr>
							</thead>
							<c:forEach items="${adherent.emprunts}" var="emprunt">
								<tr>
									<td>${emprunt.value.reference}</td>
									<td>${emprunt.value.intitule}</td>
									<td>${emprunt.value.dispo}</td>
									<td>${emprunt.value.datePublicationParsee}</td>
									<td>
										
										<c:url value = "/emprunt" var = "empruntURL">
   										<c:param name = "reference" value = "${emprunt.value.reference}"/>
   										<c:param name = "action" value = "restituer"/>
										</c:url>
										<a href="${empruntURL}"><img alt="restituer" src=" img/restituer.jpg" onclick=""></a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
				</form>
			</div>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${empruntException!=null}">
		<tr>
			<td>
				<div id="errors">
					<form>
						<span class="erreur">${empruntException}</span>
					</form>
				</div>
			</td>
		 </tr>	 
	</c:if>
	
</table>
