<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<title>Fantasy Football</title>

<head>

</head>

<body>


	<c:forEach var="team" items="${teams}">
		<div style="float: left">
			<table style="text-align: center; margin-left: 15px">
				<thead>
					<tr style="text-align: center">
						<th>${team.teamName}</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="player" items="${team.players}">
						<c:if test="${player.position == 1}">
							<tr style="width: 30px">
								<td>GK</td>
								<td>${player.webName}</td>
							</tr>
						</c:if>
					</c:forEach>
					<c:forEach var="player" items="${team.players}">
						<c:if test="${player.position == 2}">
							<tr style="width: 30px">
								<td>DEF</td>
								<td>${player.webName}</td>
							</tr>
						</c:if>
					</c:forEach>
					<c:forEach var="player" items="${team.players}">
						<c:if test="${player.position == 3}">
							<tr style="width: 30px">
								<td>MID</td>
								<td>${player.webName}</td>
							</tr>
						</c:if>
					</c:forEach>
					<c:forEach var="player" items="${team.players}">
						<c:if test="${player.position == 4}">
							<tr style="width: 30px">
								<td>FWD</td>
								<td>${player.webName}</td>
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
<script>
	$(document).ready(function() {
		$('#table_id').DataTable();
	});
</script>
</html>