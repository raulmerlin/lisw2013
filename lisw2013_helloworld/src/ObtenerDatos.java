

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.*;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Servlet implementation class ObtenerDatos
 */
@WebServlet("/ObtenerDatos")
public class ObtenerDatos extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> datosMapa = new HashMap();
	private HashMap<String, Integer> datosHora = new HashMap();
	private HashMap<String, Integer> datosProgramas = new HashMap();
       
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
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("oYPepKXb9Wg4uJFPfbapcw")
          .setOAuthConsumerSecret("ajNVQHyniOxk71XnJHfGraZzANRB6n9HvGFAfQRBQpg")
          .setOAuthAccessToken("1257614030-i1WO6giOGtq5840pP23Ffj4CHfhpOPDJ3lfDivV")
          .setOAuthAccessTokenSecret("YuT7ML0XahMnbqnDzJdKJ6lBUEPZbNg0HpDmeo6CQ");
        
    	
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Query query = new Query(hashtag1);
        query.setCount(100); // Cogemos los máximos posibles

  	  	// Cogemos tweets de ayer y hoy
        int DAYSTOGET = 2;
        SimpleDateFormat getDay = new SimpleDateFormat ("dd");
  	  	SimpleDateFormat getMonthYear = new SimpleDateFormat ("yyyy-MM-");
  	  	int theDay = Integer.parseInt(getDay.format(new Date().getTime()));
        query.setSince(getMonthYear.format(new Date().getTime()) + (theDay + 1 - DAYSTOGET));
        
        int count = 0;
        String responseText = "";
        try {
            count = 0;
            datosHora.clear();
            datosMapa.clear();
	        
	        
	        boolean finished = false;
	        
	        for (int j = 0; j < 10; j++) {
		        QueryResult result = twitter.search(query);
		        List<Status> tweets = result.getTweets();
		        for (int i = 0; i < tweets.size(); i++) {
		        	  Status t = tweets.get(i);
		        	  count++;
		        	  
		        	  String msg = t.getText();
		        	  Date d = t.getCreatedAt();
		        	  SimpleDateFormat ft = new SimpleDateFormat ("HH");
		        	  String date = ft.format(d);
		        	  addHora(date);
		        	  User user = t.getUser();
		        	  if(user.getLocation().length() > 0) addLocalCode(user.getLocation());
		        	  	  responseText += date + " - " + msg + "<br />\n";
		        	  
		       
	        	}
		        query = result.nextQuery();
		        if(query == null) break;
		        if(finished) break;
	        }
	        datosProgramas.put(hashtag1, count);
	        System.out.println("Tweets totales: " + count);
        } catch(Exception e) {
        	System.out.println(e.toString());
        }
          
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
	

	private void addHora(String hora) {
		if(datosHora.containsKey(hora)) {
			int habia = (Integer)datosHora.get(hora);
			datosHora.put(hora, (habia + 1));
		} else {
			datosHora.put(hora, 1);
		}
	}
	
	private void addLocalCode(String location) {
		String code = getLocalCode(location);
		if(code != "") {
			if(datosMapa.containsKey(code)) {
				int habia = (Integer)datosMapa.get(code);
				datosMapa.put(code, (habia + 1));
			} else {
				datosMapa.put(code, 1);
			}
		}
	}
	
	private String getLocalCode(String location) {
		if(location.matches(".*Álava.*")) return "ES-PV";
		else if(location.matches(".*Albacete.*")) return "ES-CM";
		else if(location.matches(".*Alicante.*")) return "ES-VC";
		else if(location.matches(".*Almería.*")) return "ES-AN";
		else if(location.matches(".*Asturias.*")) return "ES-AS";
		else if(location.matches(".*Ávila.*")) return "ES-CL";
		else if(location.matches(".*Badajoz.*")) return "ES-EX";
		else if(location.matches(".*Balear.*")) return "ES-IB";
		else if(location.matches(".*Barcelona.*")) return "ES-CT";
		else if(location.matches(".*Burgos.*")) return "ES-CL";
		else if(location.matches(".*Cáceres.*")) return "ES-EX";
		else if(location.matches(".*Cádiz.*")) return "ES-AN";
		else if(location.matches(".*Cantabria.*")) return "ES-CB";
		else if(location.matches(".*Castellón.*")) return "ES-VC";
		else if(location.matches(".*Ciudad Real.*")) return "ES-CM";
		else if(location.matches(".*Córdoba.*")) return "ES-AN";
		else if(location.matches(".*Cuenca.*")) return "ES-CM";
		else if(location.matches(".*Girona.*")) return "ES-CT";
		else if(location.matches(".*Granada.*")) return "ES-AN";
		else if(location.matches(".*Guadalajara.*")) return "ES-CM";
		else if(location.matches(".*Gipuzkoa.*")) return "ES-PV";
		else if(location.matches(".*Huelva.*")) return "ES-AN";
		else if(location.matches(".*Huesca.*")) return "ES-AR";
		else if(location.matches(".*Jaén.*")) return "ES-AN";
		else if(location.matches(".*La Rioja.*")) return "ES-RI";
		else if(location.matches(".*Las Palmas.*")) return "ES-CN";
		else if(location.matches(".*León.*")) return "ES-CL";
		else if(location.matches(".*Lleida.*")) return "ES-CT";
		else if(location.matches(".*Lugo.*")) return "ES-GA";
		else if(location.matches(".*Madrid.*")) return "ES-MD";
		else if(location.matches(".*Málaga.*")) return "ES-AN";
		else if(location.matches(".*Murcia.*")) return "ES-MC";
		else if(location.matches(".*Navarra.*")) return "ES-NC";
		else if(location.matches(".*Ourense.*")) return "ES-GA";
		else if(location.matches(".*Palencia.*")) return "ES-CL";
		else if(location.matches(".*Pontevedra.*")) return "ES-GA";
		else if(location.matches(".*Salamanca.*")) return "ES-CL";
		else if(location.matches(".*Tenerife.*")) return "ES-CN";
		else if(location.matches(".*Segovia.*")) return "ES-CL";
		else if(location.matches(".*Sevilla.*")) return "ES-AN";
		else if(location.matches(".*Soria.*")) return "ES-CL";
		else if(location.matches(".*Tarragona.*")) return "ES-CT";
		else if(location.matches(".*Teruel.*")) return "ES-AR";
		else if(location.matches(".*Toledo.*")) return "ES-CM";
		else if(location.matches(".*Valencia.*")) return "ES-VC";
		else if(location.matches(".*Vizcaya.*")) return "ES-PV";
		else if(location.matches(".*Zamora.*")) return "ES-CL";
		else if(location.matches(".*Zaragoza.*")) return "ES-AR";
		else if(location.matches(".*Andalucía.*")) return "ES-AN";
		else if(location.matches(".*Aragón.*")) return "ES-AR";
		else if(location.matches(".*Asturias.*")) return "ES-AS";
		else if(location.matches(".*Canarias.*")) return "ES-CN";
		else if(location.matches(".*Cantabria.*")) return "ES-CB";
		else if(location.matches(".*Mancha.*")) return "ES-CM";
		else if(location.matches(".*Catal.*")) return "ES-CT";
		else if(location.matches(".*Extrem.*")) return "ES-EX";
		else if(location.matches(".*Galicia.*")) return "ES-GA";
		else if(location.matches(".*Balea.*")) return "ES-IB";
		else if(location.matches(".*Rioja.*")) return "ES-RI";
		else if(location.matches(".*Navarra.*")) return "ES-NC";
		else if(location.matches(".*Vasco.*")) return "ES-PV";
		else if(location.matches(".*Euska.*")) return "ES-PV";
		else if(location.matches(".*Ceuta.*")) return "ES-CE";
		else if(location.matches(".*Melilla.*")) return "ES-ML";
		
		// AÑADIDOS A MANO, QUE SE HAN ENCONTRADO MUCHOS
		else if(location.matches(".*Canar.*")) return "ES-CN";
		else if(location.matches(".*Mallor.*")) return "ES-IB";
		else if(location.matches(".*Menorc.*")) return "ES-IB";
		else return "";
		
		
	}

}
