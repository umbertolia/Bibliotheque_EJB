<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table>
	<tr>
		<td>
			<form action="personne" method="get" id="personneForm">
				<h2>Personne</h2>
					<table>
					<tr>
							<td>Id</td>
							<td><input type="text"  name="id" id="id" o value="${id}"  oninput="setCustomValidity('')"></td>
						</tr>
						<tr>
							<td>Nom</td>
							<td><input type="text" pattern="[A-Za-z-_ ]+" name="nom" id="nom" value="${nom}" oninput="setCustomValidity('')"></td>
						</tr>
						<tr>
							<td>Prenom</td>
							<td><input type="text" pattern="[A-Za-z-_ ]+" name="prenom" id="prenom" value="${prenom}" oninput="setCustomValidity('')"></td>
						</tr>
						<tr>
							<td><input type="submit" name="action" id="action" value="login" onclick="validerFormPersonne(this)"></td>
							<c:if test="${(adherent != null)}">
							<td>
								<input type="submit" name="action" id="action" value="logout">
							</td>
							</c:if>
							<c:if test="${(adherent != null)}">
							<td>
								<input type="submit" name="action" id="action" value="modifier"  onclick="validerFormPersonne(this)">
							</td>
							</c:if>
							<td><input type="submit" name="action" id="action" value="ajouter" onclick="validerFormPersonne(this)"></td>
						</tr>
					</table>
				</form>
		</td>
	</tr>
	
	<c:if test="${(adherent != null)||(mtMsgErr != null)}">
	<tr>
		<td>
			<div id="personne">
						<form>
							<div>
								<h1>Adhérent :</h1>
								<table class="table-style-two">
									<thead>
										<tr>
											<th>ID</th>
											<th>NOM</th>
											<th>PRENOM</th>
										</tr>
									</thead>
										<tr>
											<td>${adherent.id}</td>
											<td>${adherent.nom}</td>
											<td>${adherent.prenom}</td>
										</tr>
								</table>
							</div>
						</form>
			</div>
		</td>	
	</tr>
	</c:if>
	
	<c:if test="${personneException!=null}">
	<tr>
		<td>
			<div id="errors">
				<form>
					<span class="erreur">${personneException}</span>
				</form>
			</div>
		</td>
	</c:if>
</table>
