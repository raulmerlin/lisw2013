package lisw.audimetrosocial.esper;

import org.apache.catalina.websocket.StreamInbound;

import lisw.audimetrosocial.business.Tweet;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

/**
 * Sets up the configuration for the usage of Esper. This will take every tweet from the 
 * {@link lisw.audimetrosocial.business.TwitterListener#onStatus(twitter4j.Status) Twitter stream}
 * and process them to get: 
 * <ul>
 * 	<li>
 * 		The number of tweets for the hashtag the user is searching for within a window of 
 * 		2 seconds for a real time view of the geographical localization of tweets and the social
 * 		activity
 * 	</li>
 * 	<li>
 * 		The number of tweets group by hahstags based on the list of hashtags of the 
 * 		{@link lisw.audimetrosocial.business.ShowList enumeration}. This will be used to build the
 * 		top 5 of the programs based on their activity on twitter.
 * </ul>
 * @author lisw
 *
 */
public class Processing {

	/**
	 * Configuration object for Esper
	 */
	private Configuration cepConfig;
	
	/**
	 * Provider for Esper
	 */
	private EPServiceProvider cep;
	
	/**
	 * Runtime object for Esper
	 */
	private EPRuntime cepRT;
	
	/**
	 * Constructor for the processing object.
	 * @param connection Connection to the user.
	 * @param hashtag Hashtag the user is looking for.
	 */
	public Processing(StreamInbound connection, String hashtag){
		// Standard configuration for Esper
		cepConfig = new Configuration();
		// Add the tweet class as an Esper processable event
		cepConfig.addEventType("Tweet", Tweet.class);
		
		cep = EPServiceProviderManager.getProvider("tweetProvider", cepConfig);
		cepRT = cep.getEPRuntime();
		
		// Statement used to get the number of tweets that contains the hashtag the user 
		// looking within a window of 2 seconds. It groups it by locale so it can be used to
		// update the map at the same time.
		String userHashtagStatement = "SELECT locale, count(*) as nbTweets FROM Tweet.win:time_batch(2 sec) WHERE userHashtag = '" + hashtag + "' GROUP BY locale";
		
		// Statement used to create the top 5 of the activity on twitter within a window of 2 minutes 
		String top5Statement = "SELECT hashtag, count(*) as nbTweets FROM Tweet.win:time_batch(2 min) GROUP BY hashtag ORDER BY nbTweets";
		
		EPAdministrator cepAdm = cep.getEPAdministrator();
		
		// Add the statements and associated listeners
		EPStatement cepStatement = cepAdm.createEPL(userHashtagStatement);
		cepStatement.addListener(new UserHashtagListener(connection));
		
		EPStatement topStatement = cepAdm.createEPL(top5Statement);
		topStatement.addListener(new Top5Listener(connection));
	}
	
	/**
	 * Send a new Tweet event to be processed by the runtime Object 
	 * @param tweet The tweet object
	 */
	public void newTweet(Tweet tweet){
		cepRT.sendEvent(tweet);
	}
}
