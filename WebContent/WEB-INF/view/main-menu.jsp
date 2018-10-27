<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<title>Fantasy Football</title>
<head>
</head>
<body>
	<jsp:include page="header.jsp" />
	<table id="table_id" class="display"
		style="text-align: center; margin-left: 15px; padding-right: 30px; width:98%; margin-left:2%">
		<thead>
			<tr style="text-align: center; color: #4c16aa">
				<th style="min-width:160px">Team Name</th>
				<th>Manager</th>
				<th>Team Value</th>
				<th>Transfers</th>
				<th>Wildcard</th>
				<th data-toggle="tooltip" title="Free Hit Played">FH</th>
				<th data-toggle="tooltip" title="Bench Boost Played">BB</th>
				<th data-toggle="tooltip" title="Triple Captain Played">TC</th>
				<!--<th>Expected Points</th>-->
				<th>Captain (Points)</th>
				<th data-toggle="tooltip" title="Gameweek Points">GW Points</th>
				<th>Total Points</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="team" items="${teams}">
				<tr>
					<td style="min-width:150px"><b>${team.teamName}</b>
						<button class="mainScreenInfoButtons" type="button"
							data-toggle="modal"
							data-target="#${team.teamName.replace('\'',' ')}">
							<i class="fas fa-info"></i>
						</button></td>
					<td>${team.managerName}</td>
					<td>${(team.teamValue+team.bank)/10}</td>
					<td>${team.totalTransfers}</td>
					<td>${team.wildcard==true ? "Yes" : ""}</td>
					<td>${team.freeHit==true ? "Yes" : ""}</td>
					<td>${team.benchBoost==true ? "Yes" : ""}</td>
					<td>${tTeam.tripleCaptain==true ? "Yes" : ""}</td>
					<!--<td></td>-->
					<td>${team.captain.webName}(${team.captain.gameweekPoints*2})</td>
					<td>${team.gameweekPoints}<button
							class="mainScreenInfoButtons"  type="button"
							data-toggle="modal"
							data-target="#${team.teamName.replace('\'',' ')}players">
							<i class="fas fa-info"></i>
						</button></td>
					<td>${team.totalPoints}</td>
				</tr>
				<div class="modal fade" id="${team.teamName.replace('\'',' ')}"
					role="dialog" aria-labelledby="exampleModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">${team.teamName}</h5>
							</div>
							<div class="modal-body">
								<ul>
									<li><b>Overall Rank:</b> 
										<span class="teamInfoValues">${team.overallRank}</span>
									</li>
									<li><b>Points On The Bench:</b>
										<span class="teamInfoValues">${team.substitutePoints}</span>
									</li>
								</ul>
								<p>
									<b>Weekly Transfers:</b>
								</p>
								<c:forEach var="transfer" items="${team.weeklyTransfers}">
									<ul>
										<li>${transfer.key.webName}
											(${transfer.key.gameweekPoints}) -> ${transfer.value.webName}
											(${transfer.value.gameweekPoints})</li>
									</ul>
								</c:forEach>
							</div>
							<div class="modal-footer"></div>
						</div>
					</div>
				</div>
				<div class="modal fade"
					id="${team.teamName.replace('\'',' ')}players" role="dialog"
					aria-labelledby="exampleModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h5 class="modal-title" id="exampleModalLabel">${team.teamName}</h5>
							</div>
							<div class="modal-body">
								<p>
									<b>Players Points</b>
								</p>
								<ul>
									<c:forEach var="player" items="${team.players}">
										<c:if test="${player.position == 1}">
											<li><span style="color:grey; margin-right:5px">GK:</span> ${player.webName} 
											<c:choose>
												<c:when test="${player.webName.equals(team.captain.webName)}"><b>(C)</b><span class="playerModalInfo">${player.gameweekPoints*2}</span></c:when>
												<c:otherwise><span class="playerModalInfo">${player.gameweekPoints}</span></c:otherwise>
											</c:choose>
											</li>
										</c:if>
									</c:forEach>
									<c:forEach var="player" items="${team.players}">
										<c:if test="${player.position == 2}">
											<li><span style="color:grey; margin-right:5px">DF:</span> ${player.webName} 
											<c:choose>
												<c:when test="${player.webName.equals(team.captain.webName)}"><b>(C)</b> <span class="playerModalInfo">${player.gameweekPoints*2}</span></c:when>
												<c:otherwise><span class="playerModalInfo">${player.gameweekPoints}</span></c:otherwise>
											</c:choose>
											</li>
										</c:if>
									</c:forEach>
									<c:forEach var="player" items="${team.players}">
										<c:if test="${player.position == 3}">
											<li><span style="color:grey; margin-right:5px">MF:</span> ${player.webName} 
											<c:choose>
												<c:when test="${player.webName.equals(team.captain.webName)}"><b>(C)</b> <span class="playerModalInfo">${player.gameweekPoints*2}</span></c:when>
												<c:otherwise><span class="playerModalInfo">${player.gameweekPoints}</span></c:otherwise>
											</c:choose>
											</li>
										</c:if>
									</c:forEach>
									<c:forEach var="player" items="${team.players}">
										<c:if test="${player.position == 4}">
											<li><span style="color:grey; margin-right:5px">FW:</span> ${player.webName} 
											<c:choose>
												<c:when test="${player.webName.equals(team.captain.webName)}"><b>(C)</b> <span class="playerModalInfo">${player.gameweekPoints*2}</span></c:when>
												<c:otherwise><span class="playerModalInfo">${player.gameweekPoints}</span></c:otherwise>
											</c:choose>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
							<div class="modal-footer"></div>
						</div>
					</div>
				</div>
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
table.dataTable.no-footer {
    border-bottom: none !important;
}
</style>
</html>