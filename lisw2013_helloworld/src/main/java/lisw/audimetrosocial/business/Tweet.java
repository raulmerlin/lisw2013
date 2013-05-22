package lisw.audimetrosocial.business;

public class Tweet {
	
	private String tweetMessage;
	private String date;
	private String locale;
	private String userHashtag;
	private String hashtag;
	
	public Tweet(){
		
	}

	public String getTweetMessage() {
		return tweetMessage;
	}

	public void setTweetMessage(String tweetMessage) {
		this.tweetMessage = tweetMessage;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getUserHashtag() {
		return userHashtag;
	}

	public void setUserHashtag(String userHashtag) {
		this.userHashtag = userHashtag;
	}

	public String getHashtag() {
		return hashtag;
	}

	public void setHashtag(String hashtag) {
		this.hashtag = hashtag;
	}

	public String toString(){
		return date + " - " + tweetMessage;
	}

}
