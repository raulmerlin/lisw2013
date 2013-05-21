package lisw.audimetrosocial.esper;

import org.apache.catalina.websocket.StreamInbound;

import lisw.audimetrosocial.business.Tweet;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class Processing {

	private Configuration cepConfig;
	private EPServiceProvider cep;
	private EPRuntime cepRT;
	private StreamInbound connection;
	
	public Processing(StreamInbound connection){
		cepConfig = new Configuration();
		cepConfig.addEventType("Tweet", Tweet.class);
		
		cep = EPServiceProviderManager.getProvider("tweetProvider", cepConfig);
		cepRT = cep.getEPRuntime();
		
		this.connection = connection;

		EPAdministrator cepAdm = cep.getEPAdministrator();
		EPStatement cepStatement = cepAdm.createEPL("SELECT count(*) as nbTweets FROM Tweet.win:time_batch(20 sec)");
		cepStatement.addListener(new CEPListener(connection));
	}
	
	public void newTweet(Tweet tweet){
		cepRT.sendEvent(tweet);
	}
}
