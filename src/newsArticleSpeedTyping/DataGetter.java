package newsArticleSpeedTyping;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import rss.RssData;

public class DataGetter {
	
	public static ArrayList<String> getData(int index) {
		ArrayList<String> words = new ArrayList<String>();
		try {
			String data[] = RssData.getDescription(index).split(" ");
			for (String dataWord : data) {
				if (!dataWord.isEmpty()) {
					words.add(dataWord); // adds individual words to String arraylist
				}
			}
		} catch (Exception e) {
			System.out.println("No more data!");
			System.exit(0);
		}
		return words;
	}

	
	public static ArrayList<String> getData(File file) {
		if (file.exists() != true) {
			System.out.println("DATA FILE NOT FOUND!\nCREATING NEW FILE");
		}
		ArrayList<String> words = new ArrayList<String>();
		String data[] = new String[214748364];
		try {
			Scanner fileReader = new Scanner(file, "UTF-8");
			while (fileReader.hasNextLine()) {
				data = fileReader.nextLine().split(" "); // splits line from file into array of strings
				for (int i = 0; i < data.length; i++) {
					if (!data[i].isEmpty()) {
						words.add(data[i]); // adds individual words to String arraylist
					}
				}
			}
			fileReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return words;
	}
}
