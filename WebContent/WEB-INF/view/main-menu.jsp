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
					<th>Wildcard Used</th>
					<th>Free Hit Used</th>
					<th>Bench Boost</th>
					<th>Triple Captain</th>
					<th>Expected Points</th>
					<th>Captain</th>
					<th>Gameweek Points</th>
					<th>Total Points</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="tempTeam" items="${teams}">
				<tr>
				<td>${tempTeam.teamName}</td>
				<td>${tempTeam.managerName}</td>
				<td>${(tempTeam.teamValue+tempTeam.bank)/10}</td>
				<td>${tempTeam.totalTransfers}</td>
				<td>${tempTeam.wildcard==true ? "Yes" : ""}</td>
				<td>${tempTeam.freeHit==true ? "Yes" : ""}</td>
				<td>${tempTeam.benchBoost==true ? "Yes" : ""}</td>
				<td>${tempTeam.tripleCaptain==true ? "Yes" : ""}</td>
				<td></td>
				<td></td>
				<td>${tempTeam.gameweekPoints}</td>
				<td>${tempTeam.totalPoints}</td>
				</tr>
				</c:forEach>
				</tbody>
			
	
	</table>


</body>
<script>$(document).ready( function () {
    $('#table_id').DataTable( {
        paging: false,
        "order": [[ 11, "desc" ]]
    } );
} );
</script>
</html>