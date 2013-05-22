package lisw.audimetrosocial.esper;

/**
 * POJO used to send the datas to the user.
 * @author lisw
 *
 */
public class Message {

	/**
	 * Type of the message. It could be a count of the tweets for the last 2 seconds, or 
	 * a count of tweet for a determined Hashtag that will serve to create the top 5 (and sent 
	 * every 2 minutes).
	 */
	private String type;
	
	/**
	 * Location of the tweet. Use only for the updates every two seconds.
	 */
	private String locale;
	
	/**
	 * Number of tweets
	 */
	private long nbTweets;
	
	/**
	 * Hashtag associated to the count. Used only for the top 5 updates.
	 */
	private String hashtag;
	
	public Message(){
		
	}

	/**
	 * Return {@link #type type}
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Set {@link #type type}
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	 * Return {@link #nbTweets nbTweets}
	 *
	 * @return the nbTweets
	 */
	public long getNbTweets() {
		return nbTweets;
	}

	/**
	 * Set {@link #nbTweets nbTweets}
	 *
	 * @param nbTweets the nbTweets to set
	 */
	public void setNbTweets(long nbTweets) {
		this.nbTweets = nbTweets;
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
