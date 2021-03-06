package rss;

import java.util.ArrayList;
import java.util.List;

public class Feed {

	final String title;
	final String link;
	final String description;

	final List<FeedMessage> entries = new ArrayList<FeedMessage>();

	public Feed(String title, String link, String description) {
		this.title = title;
		this.link = link;
		this.description = description;
	}

	public List<FeedMessage> getMessages() {
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}
}
//35 lines, 1 constructor, 4 methods