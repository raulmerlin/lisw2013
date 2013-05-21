package lisw.audimetrosocial.esper;

import java.io.IOException;
import java.nio.CharBuffer;

import org.apache.catalina.websocket.StreamInbound;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CEPListener implements UpdateListener{

	private StreamInbound connection;
	private ObjectMapper mapper;
	
	public CEPListener(StreamInbound connection){
		this.connection = connection;
		this.mapper = new ObjectMapper();
	}
	
	@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		try{
			String json = mapper.writeValueAsString(newData[0].get("nbTweets"));
			CharBuffer buffer = CharBuffer.wrap(json);
			 connection.getWsOutbound().writeTextMessage(buffer);
		} catch (JsonProcessingException e){
			
		} catch (IOException e1){
			
		}
		System.out.println(newData[0].get("nbTweets"));
	}

}
