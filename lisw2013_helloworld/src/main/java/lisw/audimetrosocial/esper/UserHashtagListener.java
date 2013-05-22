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

public class UserHashtagListener implements UpdateListener{

	private StreamInbound connection;
	private ObjectMapper mapper;
	
	public UserHashtagListener(StreamInbound connection){
		this.connection = connection;
		this.mapper = new ObjectMapper();
	}
	
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
