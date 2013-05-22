package lisw.audimetrosocial.servlet;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import javax.servlet.http.HttpServletRequest;

import lisw.audimetrosocial.business.TwitterStreaming;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

/**
 * Websocket servlet that will create a permanent connection with the user. This will be called
 * once the user acces the servlet {@link lisw.audimetrosocial.servlet.ObtenerDatos ObtenerDatos} if 
 * its browser is compatible. 
 * @author lisw
 *
 */
public class StreamingServlet extends WebSocketServlet {

	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected StreamInbound createWebSocketInbound(String subProtocol,
			HttpServletRequest request){

		try{
			return new StreamingRequestInbound((String)request.getParameter("hashtag"));
		} catch (IOException error){
			return null;
		}
	}

	/**
	 * This class will abstract the user connection with websocket. Will create the
	 * object that is used to access the Twitter streaming API.
	 * 
	 * The data will be sent back using an instance of this class.
	 * @author lisw
	 *
	 */
	private final class StreamingRequestInbound extends MessageInbound{
		
		/**
		 * The streaming API
		 * @see lisw.audimetrosocial.business.TwitterStreaming
		 */
		TwitterStreaming query;
		
		/**
		 * Constructor for the connection. Create the object for the streaming and launch it in 
		 * a new Thread.
		 * @param hashtag Hashtag the user is looking for
		 * @throws IOException
		 */
		private StreamingRequestInbound(String hashtag) throws IOException{
			query = new TwitterStreaming(this, hashtag);
			Thread streaming = new Thread(query);
			streaming.start();
		}
		
		/**
		 * When the user close the connection, we stop the streaming API.
		 */
		@Override
		protected void onClose(int status){
			query.stopStream();
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void onBinaryMessage(ByteBuffer message) throws IOException {
			throw new UnsupportedOperationException("Binary message not supported.");
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void onTextMessage(CharBuffer message) throws IOException {
			throw new UnsupportedOperationException("Text message not supported.");
		}
	}
}
