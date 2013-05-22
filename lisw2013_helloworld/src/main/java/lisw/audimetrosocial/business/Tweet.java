package lisw.audimetrosocial.business;

/**
 * POJO that contains the standard data for a tweet. This data will be sent to be display in 
 * the user broswer
 * 
 * @author lisw
 *
 */
public class Tweet {
	
	/**
	 * Message of the tweet
	 */
	private String tweetMessage;
	
	/**
	 * Region where the tweet where posted
	 */
	private String locale;
	
	/**
	 * Hashtag the user are looking for
	 */
	private String userHashtag;
	
	/**
	 * Hashtag that match one of the {@link lisw.audimetrosocial.business.Locale Enumeration field}
	 */
	private String hashtag;
	
	/**
	 * Default constructor
	 */
	public Tweet(){
		
	}

	/**
	 * Return {@link #tweetMessage tweetMessage}
	 *
	 * @return the tweetMessage
	 */
	public String getTweetMessage() {
		return tweetMessage;
	}

	/**
	 * Set {@link #tweetMessage tweetMessage}
	 *
	 * @param tweetMessage the tweetMessage to set
	 */
	public void setTweetMessage(String tweetMessage) {
		this.tweetMessage = tweetMessage;
	}

	/**
	 * Return {@link #locale locale}
	 *
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * Set {@link #locale locale}
	 *
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * Return {@link #userHashtag userHashtag}
	 *
	 * @return the userHashtag
	 */
	public String getUserHashtag() {
		return userHashtag;
	}

	/**
	 * Set {@link #userHashtag userHashtag}
	 *
	 * @param userHashtag the userHashtag to set
	 */
	public void setUserHashtag(String userHashtag) {
		this.userHashtag = userHashtag;
	}

	/**
	 * Return {@link #hashtag hashtag}
	 *
	 * @return the hashtag
	 */
	public String getHashtag() {
		return hashtag;
	}

	/**
	 * Set {@link #hashtag hashtag}
	 *
	 * @param hashtag the hashtag to set
	 */
	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

}
