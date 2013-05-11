package lisw.audimetrosocial.business;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterQuery {
	
	private ConfigurationBuilder cb;
	private Twitter twitter;
	private Map<String, Integer> datosMapa = new HashMap<String, Integer>();
	private Map<String, Integer> datosHora = new HashMap<String, Integer>();
	private Map<String, Integer> datosProgramas = new HashMap<String, Integer>();
	private int count;
	

	public TwitterQuery() throws IOException{
		PropertiesLoader authentication = new PropertiesLoader();
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
        .setOAuthConsumerKey(authentication.getConsumerKey())
        .setOAuthConsumerSecret(authentication.getConsumerSecret())
        .setOAuthAccessToken(authentication.getAccessToken())
        .setOAuthAccessTokenSecret(authentication.getAccessTokenSecret());
		
		twitter = new TwitterFactory(cb.build()).getInstance();
		count = 0; 
	}
	
	public String query(String hashtag){
		Query query = new Query(hashtag);
        query.setCount(100); // Cogemos los máximos posibles

  	  	// Cogemos tweets de ayer y hoy
        int DAYSTOGET = 1;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0 - DAYSTOGET);
        System.out.println(dateFormat.format(cal.getTime()));
        query.setSince(dateFormat.format(cal.getTime()));
        
        String responseText = "";
        try {
            count = 0;

	        boolean finished = false;

	        for (int j = 0; j < 10; j++) {
		        QueryResult result = twitter.search(query);
		        List<Status> tweets = result.getTweets();
		        System.out.println(tweets.size());
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
	        datosProgramas.put(hashtag, count);
	        System.out.println("Tweets totales: " + count);
	        return responseText;
        } catch(Exception e) {
        	System.out.println(e.toString());
        }
        return null;
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

	public Map<String, Integer> getDatosMapa() {
		return datosMapa;
	}

	public Map<String, Integer> getDatosHora() {
		return datosHora;
	}

	public Map<String, Integer> getDatosProgramas() {
		return datosProgramas;
	}
	
	public int getCount(){
		return count;
	}
}
