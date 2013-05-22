package lisw.audimetrosocial.business;

import lisw.audimetrosocial.esper.Processing;

import org.apache.catalina.websocket.StreamInbound;

import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

/**
 * Listener that will process all the new tweets from the Twitter Streaming API.
 * 
 * @author lisw
 *
 */
public class TwitterListener implements StatusListener{
	
	/**
	 * Hashtag the user are looking for
	 */
	private String hashtag;
	
	/**
	 * Esper processer for the stream of tweets
	 * @see lisw.audimetrosocial.esper.Processing
	 */
	private Processing processer;
	
	/**
	 * Constructor for the listener
	 * @param connection The websocket connection that will be used to send data to the user
	 * @param hashtag The hashtag the user are looking for
	 */
	public TwitterListener(StreamInbound connection, String hashtag){
		this.processer = new Processing(connection, hashtag);
		this.hashtag = hashtag;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onException(Exception ex) {
		ex.printStackTrace();	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStallWarning(StallWarning warning) {
		System.out.println("Got stall warning:" + warning);
	}

	/**
	 * Get the data from all the new tweets in the stream of the API. The data will be formatted
	 * in a {@link lisw.audimetrosocial.business.Tweet Tweet POJO} and pass to 
	 * {@link lisw.audimetrosocial.esper.Processing Esper} for processing.
	 */
	@Override
	public void onStatus(Status status) {
		Tweet tweet = new Tweet();
		tweet.setTweetMessage(status.getText());
		
		User user = status.getUser();
		String locale = null;
  	  	if(user.getLocation().length() > 0) locale = Locale.getLocalCode(user.getLocation());
  	  	tweet.setLocale(locale);
  	  	tweet.setUserHashtag(hashtag);
  	  	tweet.setHashtag(getHashtag(status));

  	  	processer.newTweet(tweet);
	}

	/**
	 * {@inheritDoc}
	 */
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
