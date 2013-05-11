package lisw.audimetrosocial.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import lisw.audimetrosocial.business.TwitterQuery;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class ObtenerDatos
 */
@WebServlet("/ObtenerDatos")
public class ObtenerDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Integer> datosMapa = new HashMap<String, Integer>();
	private Map<String, Integer> datosHora = new HashMap<String, Integer>();
	private Map<String, Integer> datosProgramas = new HashMap<String, Integer>();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ObtenerDatos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hashtag1 = request.getParameter("primerHashtag");
		if(hashtag1 == null) {
			hashtag1 = "#granhotel";
		}
		TwitterQuery twitterQuery = new TwitterQuery();
		String responseText = twitterQuery.query(hashtag1);
		datosMapa = twitterQuery.getDatosMapa();
		datosHora = twitterQuery.getDatosHora();
		datosProgramas = twitterQuery.getDatosProgramas();
		int count = twitterQuery.getCount();
          
		PrintWriter out = response.getWriter();
		out.println("<!doctype html><html>");
        out.println("<head>");
        out.println("<title>" + hashtag1 + "</title>");
        out.println("<meta http-equiv=\"content-type\" content=\"text/html;charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\" />");
        out.println("<script type='text/javascript' src='https://www.google.com/jsapi'></script>" +
        "<script type='text/javascript'>" + "\n" +
         "google.load('visualization', '1', {'packages': ['geochart']});" + "\n" +
         "google.setOnLoadCallback(drawRegionsMap);" + "\n" +

          "function drawRegionsMap() {" + "\n" +
            "var data = google.visualization.arrayToDataTable([" + "\n" +
              "['Provincia', 'Tweets'],");
        
        Iterator iterator = datosMapa.keySet().iterator();  
        
        while (iterator.hasNext()) {  
           String key = iterator.next().toString();  
           String value = datosMapa.get(key).toString();  
           
       		out.println("['" + key + "', " + value + "],");
        }
        
        out.println("]);" + "\n" +

            "var options = {" + "\n" +
    	         "displayMode: 'regions'," + "\n" +
    	         "region: 'ES'," + "\n" +
    	         "resolution: 'provinces'," + "\n" +
    	         "colorAxis: {colors: ['#FFDDDD', 'red']}" + "\n" +
    	       "};" + "\n" +

            "var chart = new google.visualization.GeoChart(document.getElementById('chart_div'));" + "\n" +
            "chart.draw(data, options);" + "\n" +
        "};" + "\n" +
        "</script>" + "\n");
        
        out.println("<script type=\"text/javascript\">\n" +
      "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});" + "\n" +
      "google.setOnLoadCallback(drawChart);" + "\n" +
      "function drawChart() {" + "\n" +
      "  var data = google.visualization.arrayToDataTable([" + "\n" +
      "    ['Hora', 'Tweets'],");
        
      
      for(int i = 0; i < 24; i++) {
    	  String key = "";
    	  	if(i < 10) key = "0" + i;
    	  	else key = i + "";
    		if(datosHora.containsKey(key)) {
    			String value = datosHora.get(key).toString();   
    			out.println("['" + key + "', " + value + "],");
    		} else {
    			out.println("['" + key + "', 0],");
    		}
      }
      
      
      out.println("  ]);" + "\n" +

      "  var options = {" + "\n" +
      "    vAxis: {title: 'Tweets',  titleTextStyle: {color: 'black'}}," + "\n" +
      "    hAxis: {title: 'Hora',  titleTextStyle: {color: 'black'}}," + "\n" +
      "  };" + "\n" +

      "  var chart1 = new google.visualization.AreaChart(document.getElementById('chart_div1'));" + "\n" +
      "  chart1.draw(data, options);" + "\n" +
      "}" + "\n" +
    "</script>");
      
      out.println("<script type=\"text/javascript\">\n" +
    "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});" + "\n" +
    "google.setOnLoadCallback(drawVisualization);" + "\n" +
    "function drawVisualization() {" + "\n" +
  "var data = google.visualization.arrayToDataTable([" + "\n" +
  "  ['Graphic', 'Tweets'],");

     iterator = datosProgramas.keySet().iterator();  
      
      while (iterator.hasNext()) {  
         String key = iterator.next().toString();  
         String value = datosProgramas.get(key).toString();  
         
     		out.println("['" + key + "', " + value + "],");
      }
  
  	out.println("]);" + "\n" +

  "new google.visualization.ColumnChart(document.getElementById('chart_div2'))." + "\n" +
  "    draw(data" + "\n" +
  "    );" + "\n" +
  "}" + "</script>");



        out.println("</head>");
        out.println("<body>");
        out.println("<h1>Visualizando datos sobre: " + hashtag1 + "</h1>");
        out.println("<div style=\"display:block; float: right;\">" +
        			"<form  action=\"ObtenerDatos\" method=\"get\">" +
        			"<input type=\"text\" name=\"primerHashtag\">" +
        			"<input type=\"submit\" value=\"Evaluar\"></div>");
        out.println("<table border=\"0\" cellpadding=\"1\" cellspacing=\"1\" style=\"width: 100%;\">");
        out.println("<tbody>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<h3>Evolución Horaria</h3>");
        out.println("<div id=\"chart_div1\" style=\"width: 500px;\"></div>");
        out.println("</td>");
        out.println("<td rowspan=\"2\">");
        out.println("<h3>Mapa de Actividad</h3>");
        out.println("<div id=\"chart_div\" style=\"width: 700px;\"></div>");
        out.println("<br />");
        out.println("<br />");
        out.println("</td>");
        out.println("</tr>");
        out.println("<tr>");
        out.println("<td>");
        out.println("<h3>Top 5 Programas</h3>");
        out.println("<div id=\"chart_div2\" style=\"width: 500px;\"></div>");
        out.println("</td>");
        out.println("</tr>");
        out.println("</tbody>");
        out.println("</table>");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<br />");
        out.println("<div>");
        out.println(responseText);
        out.println("<br />");
        out.println("<br />");
        out.println("Tweets totales analizados: " + count);
        out.println("</div></body>");
        out.println("</html>"); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}