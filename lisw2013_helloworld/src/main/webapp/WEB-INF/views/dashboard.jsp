<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Visualizando datos sobre: ${hashtag}</title>
		<meta http-equiv="content-type" content="text/html;charset=ISO-8859-1" />
		<link type="text/css" rel="stylesheet" href="css/stylesheet.css">
		<link href='http://fonts.googleapis.com/css?family=Special+Elite' rel='stylesheet' type='text/css'>
		
		<script type="text/javascript" src="https://www.google.com/jsapi"></script>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
		<script type="text/javascript" src="js/options.js"></script>
		<script type="text/javascript">
			google.load("visualization", "1", {"packages": ["geochart"]});
			google.setOnLoadCallback(drawRegionsMap);
			function drawRegionsMap(){
				Datas.map = new google.visualization.DataTable();
				Datas.map.addColumn('string', "Provincia");
				Datas.map.addColumn('number', "Tweets");
					
				Charts.map = new google.visualization.GeoChart(document.getElementById("region_chart_div"));
				Charts.map.draw(Datas.map, Options.map);
			}
		</script>

		<script type="text/javascript">
			google.load("visualization", "1", {packages:["corechart"]});
			google.setOnLoadCallback(drawChart);

			currentHour = "${hour}";
			function drawChart() {
				Datas.hour = new google.visualization.DataTable();
				Datas.hour.addColumn('string', "Hora");
				Datas.hour.addColumn('number', "Tweets");		
				
				Charts.hour = new google.visualization.AreaChart(document.getElementById("chart_div"));
				Charts.hour.draw(Datas.hour, Options.hour);
			}
		</script>
		
		<script type="text/javascript">
			google.load("visualization", "1", {packages:["corechart"]});
			google.setOnLoadCallback(drawVisualization);
			
			function drawVisualization() {
				Datas.programs = new google.visualization.DataTable();
				Datas.programs.addColumn('string', "Graphic");
				Datas.programs.addColumn('number', "Tweets");
				
				Charts.programs = new google.visualization.ColumnChart(document.getElementById('column_chart_div'));
				Charts.programs.draw(Datas.programs);
			}
		</script>
		
		<script type="text/javascript" src="js/streaming.js"></script>
		<script type="text/javascript">
			Streaming.initialize("${hashtag}");
		</script>
	</head>
	
	<body>
		<h1>Visualizando datos sobre: ${hashtag}</h1>
		
		<div class="right">
			<form  action="ObtenerDatos" method="get">
			<input type="text" name="primerHashtag"><input type="submit" value="Evaluar">
			</form>
		</div>
		
		<div class="clear"></div>
		
		<div id="chart_div"></div>
		<div id="column_chart_div"></div>
		<div id="mapa">
			<h4>Distrubuci&oacute;n geogr&aacute;fica</h4>
		</div>
		
		<div id="region_chart_div"></div>
		
	</body>	
</html>