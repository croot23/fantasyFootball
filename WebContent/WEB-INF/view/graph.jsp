<!DOCTYPE HTML>
<html>
<title>Fantasy Football</title>
<head>

<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

<script type="text/javascript">
	function Get(yourUrl) {
		var Httpreq = new XMLHttpRequest();
		Httpreq.open("GET", yourUrl, false);
		Httpreq.send(null);
		return Httpreq.responseText;
	}

	function predicateBy(prop) {
		return function(a, b) {
			if (a[prop] > b[prop]) {
				return 1;
			} else if (a[prop] < b[prop]) {
				return -1;
			}
			return 0;
		}
	}

	window.onload = function() {

		var graphDat = JSON.parse(Get("/fantasy-football/getTeamPoints"));

		graphData = graphDat.sort(predicateBy("y"));

		var chart = new CanvasJS.Chart("chartContainer", {
			title : {
				text : "Fantasy Football Points"
			},
			data : [ {
				// Change type to "doughnut", "line", "splineArea", etc.
				type : "bar",
				dataPoints : graphData
			} ]
		});
		chart.render();
	}
</script>
</head>

<body>
	<jsp:include page="header.jsp" />
	<div id="chartContainer"
		style="height: 300px; margin: auto; width: 70%;"></div>
</body>

<style>
	.graph {
		color: yellow !important;
	}
</style>

</html>