package lisw.audimetrosocial.business;

import java.text.SimpleDateFormat;

import lisw.audimetrosocial.esper.Processing;

import org.apache.catalina.websocket.StreamInbound;

import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

public class TwitterListener implements StatusListener{
	
	private String hashtag;
	private Processing processer;
	
	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss");
	
	public TwitterListener(StreamInbound connection, String hashtag){
		this.processer = new Processing(connection, hashtag);
		this.hashtag = hashtag;
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
  	  	tweet.setUserHashtag(hashtag);
  	  	tweet.setHashtag(getHashtag(status));

  	  	processer.newTweet(tweet);
	}

	@Override
	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
	}
	
	private String getHashtag(Status status){
		HashtagEntity[] hashtags = status.getHashtagEntities();
		String hashtag = null;
		for (int i = 0; i < hashtags.length; i++){
			hashtag = ShowList.isHashtag("#" + hashtags[i].getText());
			if (hashtag != null){
				return hashtag;
			}
			if (this.hashtag.equalsIgnoreCase("#" + hashtags[i].getText())){
				return this.hashtag;
			}
		}
		return null;
	}
}
