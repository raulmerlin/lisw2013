package lisw.audimetrosocial.esper;

public class Message {

	private String type;
	private String locale;
	private long nbTweets;
	private String hashtag;
	
	public Message(){
		
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public long getNbTweets() {
		return nbTweets;
	}
	public void setNbTweets(long nbTweets) {
		this.nbTweets = nbTweets;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}
	
	
}
