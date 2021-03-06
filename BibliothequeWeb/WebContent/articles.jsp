<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="WEB-INF/tld/mytags.tld" prefix="mytag"%>
<%@page import="metier.constantes.ActionEnum" %>

<table>
	<tr>
		<td>
			<form action="article" method="get" id="articleForm">
			<input type="hidden" name="id"/>
						<h2>Article</h2>
						<table>
							<tr>
								<td>R�f�rence</td>
								<td><input type="text" name="reference" id="reference" value="${reference}" oninput="setCustomValidity('')"></td>
							</tr>
							<tr>
								<td>Intitule</td>
								<td><input type="text"  name="intitule" id="intitule" value="${intitule}" oninput="setCustomValidity('')"></td>
							</tr>
							<tr>
								<td><input type="submit" name="action" id="action" value=<mytag:action actionEnum="<%=ActionEnum.CONSULTER %>"/> onclick="validerFormArticle('<mytag:action actionEnum="<%=ActionEnum.CONSULTER %>"/>')"></td>
								<td><input type="submit" name="action" id="action" value=<mytag:action actionEnum="<%=ActionEnum.CREER %>"/> onclick="validerFormArticle('<mytag:action actionEnum="<%=ActionEnum.CREER %>"/>')"></td>
								<c:if test="${(reference != null && intitule != null)}" >
								<td>
									<input type="submit" name="action" id="action" value=<mytag:action actionEnum="<%=ActionEnum.MODIFIER %>"/>  onclick="validerFormArticle('<mytag:action actionEnum="<%=ActionEnum.MODIFIER %>"/>')">
								</td>
								</c:if>
								<td><input type="submit" name="action" id="action" value=<mytag:action actionEnum="<%=ActionEnum.INVENTAIRE %>"/> onclick="disableFormValidationAndSubmit('articleForm')" ></td>
							</tr>
						</table>
			</form>	
		</td>
	</tr>
	
	<c:if test="${(articles!=null)||(mtMsgErr!=null)}">
	<tr>
		<td>
			<div id="articles">
				<form>
					<div>
						<h1>Liste des Articles</h1>
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
		</td>
	</tr>
	</c:if>
	
	<c:if test="${articleException!=null}">
	<tr>
		<td>
			<div id="errors">
				<form>
					<span class="erreur">${articleException}</span>
				</form>
			</div>
		</td>
	</tr>
	</c:if>
	
	<c:if test="${inventaire!=null}">
	<tr>
		<td>
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
		</td>
	</tr>
	</c:if>
</table>
