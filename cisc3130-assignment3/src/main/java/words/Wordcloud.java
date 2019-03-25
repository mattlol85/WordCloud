package words;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import java.nio.file.Paths;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

public class Wordcloud {
	// This program reads in a text file and then counts the
	// occurrences of words in the file (ignoring case).  It then reports the
	// frequencies sorted by most frequent first.

	// use: java WordCount "Bohemian_Rhapsody.txt"

	// author: Katherine Chuang
	// github: @katychuang
	// Brooklyn College, CISC 3130
	
	// Edited By Matthew Fitzgerald
	//I changed the title (I apologze professor but i left the credit!)



	    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {

	      // Create a list from elements of HashMap
	      List<Map.Entry<String, Integer> > list = new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

	      // Sort the list
	      Collections.sort(list, new java.util.Comparator<Map.Entry<String, Integer> >() {
	      	public int compare(Map.Entry<String, Integer> o1,
	                           Map.Entry<String, Integer> o2) {
	        	return (o2.getValue()).compareTo(o1.getValue());
	        }
	      });

	      // put data from sorted list to hashmap
	      HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
	      for (Map.Entry<String, Integer> aa : list) {
	      	temp.put(aa.getKey(), aa.getValue());
	      }
	      return temp;
	    }

	    public static void main(String[] args) throws IOException {
	        // open the file
	        Scanner console = new Scanner(System.in);
	        String fileName = "Bohemian_Rhapsody.txt";
	        Scanner input = new Scanner(new File(fileName));

	        // count occurrences
	        HashMap<String, Integer> wordCounts = new HashMap<String, Integer>();
	        while (input.hasNext()) {
	            String next = input.next().toLowerCase();
	            if (!wordCounts.containsKey(next)) {

	            	next.replaceAll("\\p{Punct}", "");

	                wordCounts.put(next, 1);
	            } else {
	                wordCounts.put(next, wordCounts.get(next) + 1);
	            }
	        }
	        

	        System.out.println("Total words = " + wordCounts.size());

	        HashMap<String, Integer> sortedMapAsc = sortByValue(wordCounts);

	        // Report frequencies
	        for (String word : sortedMapAsc.keySet()) {
	            int count = sortedMapAsc.get(word);
	            System.out.println(count + ":\t" + word);
	     
	        }
	        //TEST WORDCLOUDING
            final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
            frequencyAnalyzer.setWordFrequenciesToReturn(300);
            frequencyAnalyzer.setMinWordLength(2);


            final java.util.List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("Bohemian_Rhapsody.txt");

            final Dimension dimension = new Dimension(600, 600);
            final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
            wordCloud.setPadding(2);
            wordCloud.setBackground(new PixelBoundryBackground("freddie.png"));
            wordCloud.setFontScalar(new LinearFontScalar(20, 50));
            wordCloud.setBackgroundColor(new Color(0x000000));
            wordCloud.setColorPalette(new ColorPalette(new Color(0xFFFF00), new Color(0xFFFFFF)));


            wordCloud.build(wordFrequencies);
            wordCloud.writeToFile("wordcloud.png");   
	    }
	}

