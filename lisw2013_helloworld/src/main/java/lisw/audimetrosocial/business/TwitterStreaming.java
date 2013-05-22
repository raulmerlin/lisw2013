package lisw.audimetrosocial.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.websocket.StreamInbound;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Class that will initiate the streaming from the Twitter API. The stream will be filtered with
 * a list of hashtags including the one the user are searching for.
 * 
 * The Stream will be launched in a different Thread, that will be stop when the user close the 
 * connection. 
 * @author lisw
 *
 */
public class TwitterStreaming implements Runnable{

	/**
	 * Connection with the client.
	 * @see lisw.audimetrosocial.servlet.StreamingServlet
	 */
	private StreamInbound connection;
	
	/**
	 * Configuration for accessing the Twitter API
	 */
	private ConfigurationBuilder cb;
	
	/**
	 * Variable used to managed the thread.
	 */
	private volatile boolean running;
	
	/**
	 * Hashtag the user are searching
	 */
	private String hashtag;
	
	/**
	 * Default list of hashtags.
	 */
	private List<String> programs = new ArrayList<String>();
	
	/**
	 * Constructor of the streaming. 
	 * @param connection Connection with the client.
	 * @param hashtag Hashtag that the user is searching for.
	 * @throws IOException
	 */
	public TwitterStreaming(StreamInbound connection, String hashtag) throws IOException{
		PropertiesLoader authentication = new PropertiesLoader();
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
        .setOAuthConsumerKey(authentication.getConsumerKey())
        .setOAuthConsumerSecret(authentication.getConsumerSecret())
        .setOAuthAccessToken(authentication.getAccessToken())
        .setOAuthAccessTokenSecret(authentication.getAccessTokenSecret());
		
		this.connection = connection;
		running = true;
		this.hashtag = hashtag;
		
		programs.add(ShowList.LASEXTA.getHashtag());
		programs.add(ShowList.SIMPSON.getHashtag());
		programs.add(ShowList.SPAIN.getHashtag());
		programs.add(ShowList.TELECINCO.getHashtag());
		programs.add(ShowList.TVE.getHashtag());
		programs.add(ShowList.WALKING_DEAD.getHashtag());
		
		programs.add(hashtag);
	}
	
	/**
	 * This will launch the streaming from Twitter API. The Listener is instantiate here with 
	 * the user connection so it cans send back the data. 
	 * 
	 * The Thread will keep running until the variable {@link #running running} is true.
	 */
	@Override
	public void run() {
		TwitterListener listener = new TwitterListener(connection, hashtag);
		TwitterStream stream = new TwitterStreamFactory(cb.build()).getInstance();
		stream.addListener(listener);
		
		String[] hashtagArray = new String[programs.size()];
		for (int i = 0; i < programs.size(); i++){
			hashtagArray[i] = programs.get(i);
		}
		
		// Get the stream
		stream.filter(new FilterQuery(0, null, hashtagArray));
		while (running){
			
		}
		stream.cleanUp();
	}
	
	/**
	 * This will set the {@link #running running} variable to false and cause
	 * the end of the streaming.
	 */
	public void stopStream(){
		running = false;
	}

}
