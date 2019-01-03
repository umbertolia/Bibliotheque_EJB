<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<link href="css/main.css" rel="stylesheet" type="text/css"/>
	<script src="javascript/fonctions.js" type="text/javascript"></script> 
	<title>BIBLIOTHEQUE</title>
	</head>
<body>

<table>
	<tr>
		<td valign="top">
			<c:import url="personnes.jsp"></c:import>
		</td>	
		<td valign="top">				
			<c:import url="produits.jsp"></c:import>
		</td>
		<td valign="top">				
			<c:import url="emprunts.jsp"></c:import>
		</td>
	</tr>
	<tr>
		<td>
			<c:if test="${personneException!=null}">
					<p>
					<div id="errors">
						<form>
							<span class="erreur">${personneException}</span>
						</form>
					</div>
			</c:if>
		</td>
		<td>
			<c:if test="${articleException!=null}">
					<p>
					<div id="errors">
						<form>
							<span class="erreur">${articleException}</span>
						</form>
					</div>
			</c:if>
		</td>
		<td>
			<c:if test="${empruntException!=null}">
					<p>
					<div id="errors">
						<form>
							<span class="erreur">${empruntException}</span>
						</form>
					</div>
			</c:if>
		</td>
	</tr>
	</table>	
	


</body>
</html>
