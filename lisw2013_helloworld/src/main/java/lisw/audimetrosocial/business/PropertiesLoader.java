package lisw.audimetrosocial.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
	
	public PropertiesLoader() throws IOException{
		Properties prop = new Properties();
		InputStream propertiesFile = getClass().getResourceAsStream("/auth.properties");
		prop.load(propertiesFile);
		consumerKey = prop.getProperty("twitter.consumer.key", null);
		consumerSecret = prop.getProperty("twitter.consumer.secret", null);
		accessToken = prop.getProperty("twitter.access.token", null);
		accessTokenSecret = prop.getProperty("twitter.access.token.secret", null);
	}

	public String getConsumerKey() {
		return consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}
}
