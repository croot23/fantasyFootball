<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<title>Fantasy Football</title>

<head>
<jsp:include page="header.jsp" />
<body>
	<div style="width: 90%; margin:auto">
		Click on a player or search for a player to see which other teams include him.
		<table id="table_id" class="display">
			<thead>
				<tr style="text-align: center; color:#4c16aa">
					<th>Team Name</th>
					<th>GK</th>
					<th>GK</th>
					<th>DEF</th>
					<th>DEF</th>
					<th>DEF</th>
					<th>DEF</th>
					<th>DEF</th>
					<th>MID</th>
					<th>MID</th>
					<th>MID</th>
					<th>MID</th>
					<th>MID</th>
					<th>FWD</th>
					<th>FWD</th>
					<th>FWD</th>
				</tr>
			</thead>
			<tbody class="context">
				<c:forEach var="team" items="${teams}">

					<tr style="text-align: center">
						<td style="width:150px"><b>${team.teamName}</b></td>
						<c:forEach var="player" items="${team.allPlayers}">
							<c:if test="${player.position == 1}">
								<td>${player.webName}</td>
							</c:if>
						</c:forEach>
						<c:forEach var="player" items="${team.allPlayers}">
							<c:if test="${player.position == 2}">
								<td>${player.webName}</td>
							</c:if>
						</c:forEach>
						<c:forEach var="player" items="${team.allPlayers}">
							<c:if test="${player.position == 3}">
								<td>${player.webName}</td>
							</c:if>
						</c:forEach>
						<c:forEach var="player" items="${team.allPlayers}">
							<c:if test="${player.position == 4}">
								<td>${player.webName}</td>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
				</tbody>
		</table>
	</div>
</body>

<script>
	$(document).ready(function() {
		$('#table_id').DataTable({
			paging : false,
			"bSort" : false
		});
	});
	$(document).click(function(event) {
		var text = $(event.target).text();
		$(".context").unmark();
		$(".context").mark(text);
	});

	$(function() {

		var mark = function() {

			var keyword = $("input[type='search']").val();

			var options = {};
			$("input[name='opt[]']").each(function() {
				options[$(this).val()] = $(this).is(":checked");
			});

			$(".context").unmark({
				done : function() {
					$(".context").mark(keyword, options);
				}
			});
		};
		$("input[type='search']").on("input", mark);
	});
</script>

<style>
	table {
		width: 90%
	}
	td {
		padding: 1px 1px !important;
		font-size:small !important;
	}
	th {
		padding: 2px 2px 15px !important;
		font-size:small !important;
	}
	mark {
		padding: initial !important;
		background-color:yellow !important;
	}
	tr {
	height: 25px;
	}
	.all-teams {
	color:yellow !important;
	}
</style>
</html>