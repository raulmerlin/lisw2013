package lisw.audimetrosocial.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Class that will allow to load the property file. This file contains the credentials for
 * the Twitter API
 * 
 * @author lisw
 *
 */
public class PropertiesLoader {

	/**
	 * Consumer key for the API
	 */
	private String consumerKey;
	
	/**
	 * Consumer secret for the API
	 */
	private String consumerSecret;
	
	/**
	 * Access token for the API
	 */
	private String accessToken;
	
	/**
	 * Access token secret for the API
	 */
	private String accessTokenSecret;
	
	/**
	 * Load the property file resources/auth.properties
	 * 
	 * @throws IOException If the file doesn't exist.
	 */
	public PropertiesLoader() throws IOException{
		Properties prop = new Properties();
		InputStream propertiesFile = getClass().getResourceAsStream("/auth.properties");
		prop.load(propertiesFile);
		consumerKey = prop.getProperty("twitter.consumer.key", null);
		consumerSecret = prop.getProperty("twitter.consumer.secret", null);
		accessToken = prop.getProperty("twitter.access.token", null);
		accessTokenSecret = prop.getProperty("twitter.access.token.secret", null);
	}

	/**
	 * Return {@link #consumerKey consumerKey}
	 *
	 * @return the consumerKey
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * Set {@link #consumerKey consumerKey}
	 *
	 * @param consumerKey the consumerKey to set
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * Return {@link #consumerSecret consumerSecret}
	 *
	 * @return the consumerSecret
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * Set {@link #consumerSecret consumerSecret}
	 *
	 * @param consumerSecret the consumerSecret to set
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * Return {@link #accessToken accessToken}
	 *
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	/**
	 * Set {@link #accessToken accessToken}
	 *
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Return {@link #accessTokenSecret accessTokenSecret}
	 *
	 * @return the accessTokenSecret
	 */
	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	/**
	 * Set {@link #accessTokenSecret accessTokenSecret}
	 *
	 * @param accessTokenSecret the accessTokenSecret to set
	 */
	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

}
