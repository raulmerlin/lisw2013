package lisw.audimetrosocial.business;

import java.io.IOException;
import java.nio.CharBuffer;
import java.text.SimpleDateFormat;

import lisw.audimetrosocial.esper.Processing;

import org.apache.catalina.websocket.StreamInbound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

public class TwitterListener implements StatusListener{
	
	private StreamInbound connection;
	private Processing processer;
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
	
	public TwitterListener(StreamInbound connection){
		this.connection = connection;
		this.processer = new Processing(connection);
	}

	@Override
	public void onException(Exception ex) {
		ex.printStackTrace();	
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	}

	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	}

	@Override
	public void onStallWarning(StallWarning warning) {
		System.out.println("Got stall warning:" + warning);
	}

	@Override
	public void onStatus(Status status) {
		Tweet tweet = new Tweet();
		tweet.setTweetMessage(status.getText());
		tweet.setDate(dateFormatter.format(status.getCreatedAt()));
		
		User user = status.getUser();
		String locale = null;
  	  	if(user.getLocation().length() > 0) locale = Locale.getLocalCode(user.getLocation());
  	  	tweet.setLocale(locale);

  	  	processer.newTweet(tweet);
  	  	sendTweet(tweet);
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	}

	private void sendTweet(Tweet tweet){
		ObjectMapper mapper = new ObjectMapper();
		String jsonRep = null;
		try{
			jsonRep = mapper.writeValueAsString(tweet);
			 CharBuffer buffer = CharBuffer.wrap(jsonRep);
			// connection.getWsOutbound().writeTextMessage(buffer);
		} catch (JsonProcessingException e1){
			e1.printStackTrace();
		} catch (IOException e2){
			e2.printStackTrace();
		}
	}
}
