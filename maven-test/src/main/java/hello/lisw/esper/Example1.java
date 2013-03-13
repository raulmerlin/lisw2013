package hello.lisw.esper;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import com.espertech.esper.client.*;

import java.util.*;

public class Example1 {

    // Listener for new tweets
    public static class LocationListener implements UpdateListener{
        @Override
        public void update(EventBean[] newData, EventBean[] oldData){
            System.out.println(newData[0].getUnderlying());
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("java twitter4j.examples.search.SearchTweets [query]");
            System.exit(-1);
        }
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("oYPepKXb9Wg4uJFPfbapcw")
          .setOAuthConsumerSecret("ajNVQHyniOxk71XnJHfGraZzANRB6n9HvGFAfQRBQpg")
          .setOAuthAccessToken("1257614030-i1WO6giOGtq5840pP23Ffj4CHfhpOPDJ3lfDivV")
          .setOAuthAccessTokenSecret("YuT7ML0XahMnbqnDzJdKJ6lBUEPZbNg0HpDmeo6CQ");
        TwitterFactory tf = new TwitterFactory(cb.build());
        
        Twitter twitter = tf.getInstance();

        //Listener configuration 
        Configuration config = new Configuration();
        config.addEventType("TWEET", Tweet.class.getName());

        EPServiceProvider provider = EPServiceProviderManager.getProvider("TWEETEngine", config);
        EPRuntime runtime = provider.getEPRuntime();
    
        EPAdministrator admin = provider.getEPAdministrator();

        EPStatement eplRetweet = admin.createEPL("select * from TWEET(count>100)");
        eplRetweet.addListener(new LocationListener());

        //Retrieve the tweets
        try {
            Query query = new Query(args[0]);
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                for (Status tweet : tweets) {
                    Tweet status = new Tweet(tweet.getText(), tweet.getUser().getScreenName(), tweet.getRetweetCount());
                    runtime.sendEvent(status);
                }
            } while ((query = result.nextQuery()) != null);
            System.exit(0);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            System.exit(-1);
        }
    }
}
