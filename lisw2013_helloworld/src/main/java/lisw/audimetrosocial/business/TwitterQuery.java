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
		String code = Locale.getLocalCode(location);
		if(code != "") {
			if(datosMapa.containsKey(code)) {
				int habia = (Integer)datosMapa.get(code);
				datosMapa.put(code, (habia + 1));
			} else {
				datosMapa.put(code, 1);
			}
		}
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
