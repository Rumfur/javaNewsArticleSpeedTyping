package rss;

import java.util.ArrayList;

import newsArticleSpeedTyping.MainMenu;

public class RssData {
	
	 public static ArrayList<String> descriptionData = new ArrayList<String>();
	 public static ArrayList<String> linkData = new ArrayList<String>();
	
	public RssData(String site) {
		descriptionData.clear();
		linkData.clear();
		RSSFeedParser parser = new RSSFeedParser(site);
        Feed feed = parser.readFeed();
        for (FeedMessage message : feed.getMessages()) {
        	descriptionData.add(message.getDescription());
        	linkData.add(message.getLink());
        }
        MainMenu.articleCount = descriptionData.size();
	}
	
	public static String getDescription(int index){
		return descriptionData.get(index);
	}
}
