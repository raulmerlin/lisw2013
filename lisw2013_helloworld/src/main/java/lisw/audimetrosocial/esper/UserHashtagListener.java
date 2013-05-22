package lisw.audimetrosocial.esper;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.websocket.StreamInbound;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Esper listener used for determine the social activity associated with the hashtag the user is
 * looking for. Aggregate the data every two seconds grouping them by region and send them back 
 * to the connection initiated with the 
 * {@link lisw.audimetrosocial.servlet.StreamingServlet websocket servlet}. 
 * @author lisw
 *
 */
public class UserHashtagListener implements UpdateListener{

	/**
	 * Websocket connection to the client
	 * @see lisw.audimetrosocial.servlet.StreamingServlet
	 */
	private StreamInbound connection;
	
	/**
	 * Mapper object used to parse the object to a JSON String
	 */
	private ObjectMapper mapper;
	
	/**
	 * Constructor of the listener
	 * @param connection The client connection
	 */
	public UserHashtagListener(StreamInbound connection){
		this.connection = connection;
		this.mapper = new ObjectMapper();
	}
	
	/**
	 * For each new tick (2 seconds for this listener), the newData array will contain all the 
	 * the corresponding results for the statement. One row of this array is a array containing 
	 * the following:
	 * <ul>
	 * 	<li>
	 * 		<em>locale</em>: An ISO 3166-1 encoded region
	 * 	</li>
	 * 	<li>
	 * 		<em>nbTweets</em>: The number of tweets that appeared for this location the last 2 seconds.
	 * 	</li>
	 * </ul>
	 */
	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		List<Message> messages = new ArrayList<Message>();
		for (int i = 0; i < newData.length; i++){
			Message message = new Message();
			message.setType("tweetCount");
			message.setLocale((String) newData[i].get("locale"));
			message.setNbTweets((Long) newData[i].get("nbTweets"));
			
			messages.add(message);
		}
		try{
			String json = mapper.writeValueAsString(messages);
			CharBuffer buffer = CharBuffer.wrap(json);
			connection.getWsOutbound().writeTextMessage(buffer);
		} catch (JsonProcessingException e){
			e.printStackTrace();
		} catch (IOException e1){
			e1.printStackTrace();
		}
	}

}
