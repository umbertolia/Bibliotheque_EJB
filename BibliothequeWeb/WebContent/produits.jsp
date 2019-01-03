<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="article" method="get" id="articleForm">
	<input type="hidden" name="id"/>
				<h2>Article</h2>
				<table>
					<tr>
						<td>Référence id : ${id} </td>
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