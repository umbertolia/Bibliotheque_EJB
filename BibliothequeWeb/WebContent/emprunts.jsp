<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<form action="emprunt" method="get" id="empruntForm">
	<h2>Emprunt</h2>
	<table>
		<tr>
			<td>Id Adhérent</td>
			<td><input type="text" min="1" step="1" name="id" id="id"
				value="${id}"></td>
			<td>${errCode}</td>
		</tr>
		<tr>
			<td><input type="submit" name="action" id="action" value="emprunter" onclick="validerFormEmprunt(this)"></td>
		</tr>


	</table>
</form>
