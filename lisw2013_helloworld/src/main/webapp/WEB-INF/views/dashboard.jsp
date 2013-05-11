<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<meta http-equiv="content-type" content="text/html;charset=ISO-8859-1" />
		<link type="text/css" rel="stylesheet" href="css/stylesheet.css">
		
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
		<script type="text/javascript" src="js/options.js"></script>
		<script type="text/javascript">
			google.load("visualization", "1", {"packages": ["geochart"]});
			google.setOnLoadCallback(drawRegionsMap);
			function drawRegionsMap(){
				Datas.map = google.visualization.arrayToDataTable([
					["Provincia", "Tweets"],
					<c:forEach var="tweet" items="${datosMapa}">
						["${tweet.key}", ${tweet.value}],
					</c:forEach>
				]);
					
				Charts.map = new google.visualization.GeoChart(document.getElementById("chart_div"));
				Charts.map.draw(Datas.map, Options.map);
			}
		</script>

		<script type="text/javascript">
			google.load("visualization", "1", {packages:["corechart"]});
			google.setOnLoadCallback(drawChart);
			
			function drawChart() {
				Datas.hour = google.visualization.arrayToDataTable([
						["Hora", "Tweets"],
						<c:forEach var="i" begin="0" end="24" step="1">
							<c:set var="key" value="" />
							<c:choose>
								<c:when test="${ i < 10 }">
									<c:set var="key" value="0${i}" />
								</c:when>
								<c:otherwise>
									<c:set var="key" value="${i}" />
								</c:otherwise>
							</c:choose>
							
							<c:set var="tweets" value="0" />
							<c:forEach var="tweet" items="${datosHora}">
								<c:if test="${tweet.key eq key}">
									<c:set var="tweets" value="${tweet.value}" />
								</c:if>
							</c:forEach>
							["${key}", ${tweets}],
						</c:forEach>
				]);
				
				Charts.hour = new google.visualization.AreaChart(document.getElementById("chart_div1"));
				Charts.hour.draw(Datas.hour, Options.hour);
			}
		</script>
		
		<script type="text/javascript">
			google.load("visualization", "1", {packages:["corechart"]});
			google.setOnLoadCallback(drawVisualization);
			
			function drawVisualization() {
				Datas.programs = google.visualization.arrayToDataTable([
						['Graphic', 'Tweets'],
						<c:forEach var="tweet" items="${datosProgramas}">
				    		["${tweet.key}", ${tweet.value}],
						</c:forEach>
				]);
				
				Charts.programs = new google.visualization.ColumnChart(document.getElementById('chart_div2'));
				Charts.programs.draw(Datas.programs);
			}
		</script>
		
		<script type="text/javascript" src="js/streaming.js"></script>
		<script type="text/javascript">
			Streaming.initialize("${hashtag}");
		</script>
	</head>
	
	<body>
		<h1>Visualizando datos sobre: #granhotel</h1>
		
		<div class="right">
			<form  action="ObtenerDatos" method="get">
			<input type="text" name="primerHashtag"><input type="submit" value="Evaluar">
			</form>
		</div>
		
		<table border="0" cellpadding="1" cellspacing="1">
			<tbody>
				<tr>
					<td>
						<h3>Evolución Horaria</h3>
						<div id="chart_div1" style="width: 500px;"></div>
					</td>
					
					<td rowspan="2">
						<h3>Mapa de Actividad</h3>
						<div id="chart_div" style="width: 700px;"></div>
					</td>
				</tr>
				
				<tr>
					<td>
						<h3>Top 5 Programas</h3>
						<div id="chart_div2" style="width: 500px;"></div>
					</td>
				</tr>
			</tbody>
		</table>
		
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<br />
		<div id="tweets">
			${responseText}
			Tweets totales analizados: ${count}
		</div>
		
	</body>	
</html>