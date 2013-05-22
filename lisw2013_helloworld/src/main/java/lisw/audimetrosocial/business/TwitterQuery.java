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

/**
 * Class that get the tweets from one days and send them back to the user.
 * @deprecated Not use anymore
 * @author lisw
 *
 */
public class TwitterQuery {
	
	/**
	 * Configuration builder for the Twitter API
	 */
	private ConfigurationBuilder cb;
	
	/**
	 * Twitter object for the connection with the API
	 */
	private Twitter twitter;
	
	/**
	 * Number of tweets by region
	 */
	private Map<String, Integer> datosMapa = new HashMap<String, Integer>();
	
	/**
	 * Number of tweets based on time
	 */
	private Map<String, Integer> datosHora = new HashMap<String, Integer>();
	
	/**
	 * Number of tweets based on the hashtag
	 */
	private Map<String, Integer> datosProgramas = new HashMap<String, Integer>();
	
	/**
	 * Number of tweets based on minutes for a view of 15 minutes
	 */
	private Map<String, Integer> quinzeMinutos = new HashMap<String, Integer>();
	
	/**
	 * Number of tweets grabed
	 */
	private int count;

	/**
	 * Default constructor. Build the configuration for the Twitter API.
	 * @throws IOException
	 */
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
	
	/**
	 * Get the tweets that contain the hashtag.
	 * @param hashtag The hashtag the user is searching.
	 * @return The concatenation of all text messages contained in the tweets.
	 */
	public String query(String hashtag){
		Query query = new Query(hashtag);
        query.setCount(100); // Cogemos los máximos posibles

  	  	// Cogemos tweets de ayer y hoy
        int DAYSTOGET = 1;
        
        // Current date
        Date now = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0 - DAYSTOGET);
        System.out.println(dateFormat.format(cal.getTime()));
        query.setSince(dateFormat.format(cal.getTime()));
        
        String responseText = "";
        Date createdAt;
        SimpleDateFormat hora = new SimpleDateFormat("HH");
        SimpleDateFormat minutos = new SimpleDateFormat("HH:mm");
        String date;
        
        
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
		        	  createdAt = t.getCreatedAt();
		        	  date = hora.format(createdAt);
		        	  addHora(date);
		        	  
		        	  if (((now.getTime() - createdAt.getTime()) * 1000 * 60) < 15){
		        		  date = minutos.format(createdAt);
		        		  addMinutos(date);
		        	  }
		        	  
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
	
	/**
	 * Add a new tweets to the corresponding hour in {@link #datosHora datosHora}.
	 * @param hora The hour the tweet was posted.
	 */
	private void addHora(String hora) {
		if(datosHora.containsKey(hora)) {
			int habia = (Integer)datosHora.get(hora);
			datosHora.put(hora, (habia + 1));
		} else {
			datosHora.put(hora, 1);
		}
	}
	
	/**
	 * Add a new tweets to the corresponding minute in {@link #quinzeMinutos quinzeMinutos}.
	 * @param minuto The minute the tweet was posted.
	 */
	private void addMinutos(String minuto){
		if (quinzeMinutos.containsKey(minuto)){
			int habia = (Integer)quinzeMinutos.get(minuto);
			quinzeMinutos.put(minuto, (habia + 1));
		} else{
			quinzeMinutos.put(minuto, 1);
		}
	}
	
	/**
	 * Add a new tweet to the corresponding location in {@link #datosMapa datosMapa}.
	 * This will check if the location correspond to one of the 
	 * {@link lisw.audimetrosocial.business.Locale region}.
	 * 
	 * @param location The location to add
	 */
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

	/**
	 * Return {@link #datosMapa datosMapa}
	 *
	 * @return the datosMapa
	 */
	public Map<String, Integer> getDatosMapa() {
		return datosMapa;
	}

	/**
	 * Return {@link #datosHora datosHora}
	 *
	 * @return the datosHora
	 */
	public Map<String, Integer> getDatosHora() {
		return datosHora;
	}

	/**
	 * Return {@link #datosProgramas datosProgramas}
	 *
	 * @return the datosProgramas
	 */
	public Map<String, Integer> getDatosProgramas() {
		return datosProgramas;
	}

	/**
	 * Return {@link #quinzeMinutos quinzeMinutos}
	 *
	 * @return the quinzeMinutos
	 */
	public Map<String, Integer> getQuinzeMinutos() {
		return quinzeMinutos;
	}

	/**
	 * Return {@link #count count}
	 *
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
}
