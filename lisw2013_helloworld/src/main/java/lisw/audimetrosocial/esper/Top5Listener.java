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
 * Esper Listener used to create the top 5 of the programs. Aggregate the data and 
 * send them back to the connection initiated with the 
 * {@link lisw.audimetrosocial.servlet.StreamingServlet websocket servlet}. 
 * @author lisw
 *
 */
public class Top5Listener implements UpdateListener{

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
	public Top5Listener(StreamInbound connection){
		this.connection = connection;
		this.mapper = new ObjectMapper();
	}
	
	/**
	 * For each new tick (2 minutes for this listener), the newData array will contain all the 
	 * the corresponding results for the statement. One row of this array is a array containing 
	 * the following:
	 * <ul>
	 * 	<li>
	 * 		<em>hashtag</em>: One of the hashtag from the 
	 * 		{@link lisw.audimetrosocial.business.ShowList enumeration}.
	 * 	</li>
	 * 	<li>
	 * 		<em>nbTweets</em>: The number of tweets that appeared for this hashtag the last 2 minutes.
	 * 	</li>
	 * </ul>
	 */
	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		List<Message> messages = new ArrayList<Message>();
		for (int i = 0; i < newData.length && i < 5; i++){
			Message message = new Message();
			message.setNbTweets((Long) newData[i].get("nbTweets"));
			message.setHashtag((String) newData[i].get("hashtag"));
			message.setType("Top5");
			
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
