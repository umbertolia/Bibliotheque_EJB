<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="personne" method="get" id="personneForm">
				<h2>Personne</h2>
					<table>
					<tr>
							<td>Id</td>
							<td><input type="text" min="1" step="1" name="id" id="id" value="${id}"></td>
							<td>${errCode}</td> 
						</tr>
						<tr>
							<td>Nom</td>
							<td><input type="text" pattern="[A-Za-z]+" name="nom" id="nom" value="${nom}"></td>
							<td>${errCode}</td> 
						</tr>
						<tr>
							<td>Prenom</td>
							<td><input type="text" pattern="[A-Za-z]+" name="prenom" id="prenom" value="${prenom}"></td>
							<td>${errCode}</td>
						</tr>
						<tr>
							<td><input type="submit" name="action" id="action" value="se_loguer" onclick="validerFormPersonne(this)"></td>
							<td><input type="submit" name="action" id="action" value="ajouter" onclick="validerFormPersonne(this)"></td>
						</tr>
					</table>
				</form>
				<c:if test="${(adherent!=null)||(mtMsgErr!=null)}">
					<p>
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
				</c:if>