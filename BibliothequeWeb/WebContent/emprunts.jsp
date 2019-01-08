<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<table>
	<tr>
		<td>
			<form action="emprunt" method="get" id="empruntForm">
				<h2>Emprunt</h2>
				<table>
					<tr>
						<td>
							<label for="id">Id Adhérent</label>
						<input type="email" 
							name="id" id="id" value="${adherent.id}" required="required">
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<label for="reference">Réf. Article</label>
							<input type="number" min="1"  step="1" name="reference"
							id="reference" value="${reference}" required="required">
						</td>
					</tr>
					<tr>
						<td><button>Emprunter</button></td>
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
