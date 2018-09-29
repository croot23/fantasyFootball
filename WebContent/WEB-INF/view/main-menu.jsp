<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<title>Fantasy Football</title>
<head>
</head>
<body>
	<jsp:include page="header.jsp" />
	<table id="table_id" class="display"
		style="text-align: center; margin-left: 15px; padding-right: 30px;">
		<thead>
			<tr style="text-align: center; color: #4c16aa">
				<th>Team Name</th>
				<th>Manager</th>
				<th>Team Value</th>
				<th>Transfers</th>
				<th>Wildcard</th>
				<th>FH</th>
				<th>BB</th>
				<th>TC</th>
				<!--<th>Expected Points</th>-->
				<th>Captain (Points)</th>
				<th>GW Points</th>
				<th>Total Points</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="team" items="${teams}">
				<tr>
					<td>${team.teamName}</td>
					<td>${team.managerName}</td>
					<td>${(team.teamValue+team.bank)/10}</td>
					<td>${team.totalTransfers}</td>
					<td>${team.wildcard==true ? "Yes" : ""}</td>
					<td>${team.freeHit==true ? "Yes" : ""}</td>
					<td>${team.benchBoost==true ? "Yes" : ""}</td>
					<td>${tTeam.tripleCaptain==true ? "Yes" : ""}</td>
					<!--<td></td>-->
					<td>${team.captain.webName}(${team.captain.gameweekPoints*2})</td>
					<td>${team.gameweekPoints}</td>
					<td>${team.totalPoints}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<script>
	$(document).ready(function() {
		$('#table_id').DataTable({
			paging : false,
			"order" : [ [ 10, "desc" ] ]
		});
	});
</script>
<style>
	label {
		display: none
	}
	.home {
		color: yellow !important;
	}
</style>
</html>