<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<title>Fantasy Football</title>

<head>

</head>

<body>


	<c:forEach var="tempTeam" items="${teams}">
		<div style="float: left">
			<table style="text-align: center; margin-left: 15px">
				<thead>
				<tr style="text-align: center">
					<!-- <th>${tempTeam.teamName}</th> -->
				</tr>
				</thead>
				<tbody>
				<c:forEach var="tempPlayer" items="${tempTeam.players}">
					<c:if test="${tempPlayer.position == 1}">
						<tr style="width: 30px">
							<td>GK</td>
							<td>${tempPlayer.webName}</td>
							<!--  <td>${tempPlayer.form}</td>-->
						</tr>
					</c:if>
				</c:forEach>
				<c:forEach var="tempPlayer" items="${tempTeam.players}">
					<c:if test="${tempPlayer.position == 2}">
						<tr style="width: 30px">
							<td>DEF</td>
							<td>${tempPlayer.webName}</td>
							<!-- <td>${tempPlayer.form}</td>-->
						</tr>
					</c:if>
				</c:forEach>
				<c:forEach var="tempPlayer" items="${tempTeam.players}">
						<c:if test="${tempPlayer.position == 3}">
						<tr style="width: 30px">
						<td>MID</td>
							<td>${tempPlayer.webName}</td>
							<!-- <td>${tempPlayer.form}</td>-->
							</tr>
						</c:if>
				</c:forEach>
								<c:forEach var="tempPlayer" items="${tempTeam.players}">
						<c:if test="${tempPlayer.position == 4}">
						<tr style="width: 30px">
						<td>FWD</td>
							<td>${tempPlayer.webName}</td>
							<!--  <td>${tempPlayer.form}</td>-->
							</tr>
						</c:if>
				</c:forEach>
				<tr>
				</tr>
				</tbody>
			</table>
		</div>
	</c:forEach>


</body>
<script>$(document).ready( function () {
    $('#table_id').DataTable();
} );
</script>
</html>