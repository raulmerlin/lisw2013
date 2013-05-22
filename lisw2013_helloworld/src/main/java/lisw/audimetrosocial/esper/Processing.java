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
	
	public Processing(StreamInbound connection, String hashtag){
		cepConfig = new Configuration();
		cepConfig.addEventType("Tweet", Tweet.class);
		
		cep = EPServiceProviderManager.getProvider("tweetProvider", cepConfig);
		cepRT = cep.getEPRuntime();
		
		String userHashtagStatement = "SELECT locale, count(*) as nbTweets FROM Tweet.win:time_batch(2 sec) WHERE userHashtag = '" + hashtag + "' GROUP BY locale";
		String top5Statement = "SELECT hashtag, count(*) as nbTweets FROM Tweet.win:time_batch(2 min) GROUP BY hashtag ORDER BY nbTweets";
		
		EPAdministrator cepAdm = cep.getEPAdministrator();
		EPStatement cepStatement = cepAdm.createEPL(userHashtagStatement);
		cepStatement.addListener(new UserHashtagListener(connection));
		
		EPStatement topStatement = cepAdm.createEPL(top5Statement);
		topStatement.addListener(new Top5Listener(connection));
	}
	
	public void newTweet(Tweet tweet){
		cepRT.sendEvent(tweet);
	}
}
