package lisw.audimetrosocial.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.websocket.StreamInbound;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreaming implements Runnable{

	private StreamInbound connection;
	private ConfigurationBuilder cb;
	private volatile boolean running;
	private String hashtag;
	private List<String> programs = new ArrayList<String>();
	
	public TwitterStreaming(StreamInbound connection, String hashtag) throws IOException{
		this();
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
	
	public void stopStream(){
		running = false;
	}

}
