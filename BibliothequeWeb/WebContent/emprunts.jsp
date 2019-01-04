<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="emprunt" method="get" id="empruntForm">
	<h2>Emprunt</h2>
	<table>
		<tr>
			<td>Id Adhérent</td>
			<td><input type="text" readonly="readonly" min="1" step="1"
				name="id" id="id" value="${adherent.id}"></td>
			<td>${errCode}</td>
		</tr>
		<tr>
			<td>Réf. Produit</td>
			<td><input type="number" min="1" step="1" name="reference"
				id="reference" value="${reference}"></td>
			<td>${errCode}</td>
		</tr>
		<tr>
			<td><input type="submit" name="action" id="action"
				value="emprunter" onclick="validerFormEmprunt(this)"></td>
		</tr>


	</table>
</form>


<c:if
	test="${(adherent !=null && adherent.emprunts.size() > 0)||(mtMsgErr!=null)}">
	<p>
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
						</tr>
					</thead>
					<c:forEach items="${adherent.emprunts}" var="emprunt">
						<tr>
							<td>${emprunt.value.reference}</td>
							<td>${emprunt.value.intitule}</td>
							<td>${emprunt.value.dispo}</td>
							<td>${emprunt.value.datePublicationParsee}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</form>
	</div>
</c:if>
