package newsArticleSpeedTyping;

import java.io.IOException;

import org.jsoup.*;
import org.jsoup.helper.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class Webscraper {

	public static void main(String[] args) throws IOException {
		Document doc = Jsoup.connect("https://www.imdb.com/").timeout(6000).get();
		Elements body = doc.select("tbody");
		System.out.println(body.select("tr").size());
	}

}
