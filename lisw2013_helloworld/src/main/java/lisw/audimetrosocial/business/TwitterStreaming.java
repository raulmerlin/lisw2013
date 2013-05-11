package lisw.audimetrosocial.business;

import java.io.IOException;

import org.apache.catalina.websocket.StreamInbound;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreaming implements Runnable{

	private StreamInbound connection;
	private String hashtag;
	private ConfigurationBuilder cb;
	
	public TwitterStreaming(StreamInbound connection, String hashtag) throws IOException{
		this();
		this.connection = connection;
		this.hashtag = hashtag;
	}
	
	public TwitterStreaming() throws IOException{
		PropertiesLoader authentication = new PropertiesLoader();
		cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
        .setOAuthConsumerKey(authentication.getConsumerKey())
        .setOAuthConsumerSecret(authentication.getConsumerSecret())
        .setOAuthAccessToken(authentication.getAccessToken())
        .setOAuthAccessTokenSecret(authentication.getAccessTokenSecret());
	}
	
	@Override
	public void run() {
		TwitterListener listener = new TwitterListener(connection);
		TwitterStream stream = new TwitterStreamFactory(cb.build()).getInstance();
		stream.addListener(listener);
		
		String[] hashtagArray = new String[1];
		hashtagArray[0] = this.hashtag;
		
		// Get the stream
		stream.filter(new FilterQuery(0, null, hashtagArray));
		
	}

}
