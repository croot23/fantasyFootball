<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>

<html>
<title>Fantasy Football</title>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
  
<script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

</head>

<body>

<a href="/fantasy-football/graph">League Graph</a>

<table  id="table_id" class="display" style="text-align: center; margin-left: 15px; margin-right: 15px">
	
				<thead>
				<tr style="text-align: center">
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
					<th>Gameweek Points</th>
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
				<td>${team.captain.webName} (${team.captain.gameweekPoints*2})</td>
				<td>${team.gameweekPoints}</td>
				<td>${team.totalPoints}</td>
				</tr>
				</c:forEach>
				</tbody>
			
	
	</table>


</body>
<script>$(document).ready( function () {
    $('#table_id').DataTable( {
        paging: false,
        "order": [[ 10, "desc" ]]
    } );
} );
</script>
</html>