package lisw.audimetrosocial.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServletRequest;

import lisw.audimetrosocial.business.TwitterStreaming;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

public class StreamingServlet extends WebSocketServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request){

		try{
			return new StreamingRequestInbound((String)request.getParameter("hashtag"));
		} catch (IOException error){
			return null;
		}
	}

	private final class StreamingRequestInbound extends MessageInbound{
		
		TwitterStreaming query;
		
		private StreamingRequestInbound(String hashtag) throws IOException{
			query = new TwitterStreaming(this, hashtag);
			Thread streaming = new Thread(query);
			streaming.start();
		}
		
		@Override
		protected void onClose(int status){
			query.stopStream();
		}
		
		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			throw new UnsupportedOperationException("Binary message not supported.");
			
		}

		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			throw new UnsupportedOperationException("Text message not supported.");
		}
	}
}
