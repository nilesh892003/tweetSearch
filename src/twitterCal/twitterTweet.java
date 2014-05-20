package twitterCal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import twitterSearch.decode;

import json.JSONArray;
import json.JSONObject;


public class twitterTweet {
	/*Author: Nilesh Singh, George Mason University, MS Computer Science
	 * Date: March, 9th, 2013.
	 * 
	 * Topic:This is an app console based application that will be executed in terminal or from the command line.
	 * This app accepts a hashtag keyword ( the hashtag keyword used with twitter) as an argument and 
	 * then only print out unique urls(Source, Image urls, and Image https urls) found in the 100 most recent tweets that matched the  hashtag.
	 * 
	 * 
	 *API: Twitter search API. (GET Search)
	 *Version: 1.0
	 *
	 *JSON.org: I am using JSON.org`s JSON parser to parse the received data(JSON) into JSONArray and then process the data.
	 *
	 *Note: Twitter search API, version 1.0 does not require user authentication. We can search the most recent tweets 
	 *from the twitter anonymously.
	 *
	 * *Modified the JSON parser file( JSONObject.java) method getString. 
	 *Problem: In the Twitter application sometimes the User-url value is null then it was throwing an exceptiong.
	 *Modified the method int he library to suit the application requirement.O
	 */

		public static void main(String[] args) throws Exception {
			// Effects: Prints the unique URLs from the recent tweets.

			try{
				
				File file=new File("D:/Demo.txt");
				FileWriter fstream= new FileWriter(file,true); //the true will append the new data
				BufferedWriter wr=new BufferedWriter(fstream);
				
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			String decider = "";

			// Program run check. This provides an option to the user to run the
			// process again or quit.
			while (!(decider.toLowerCase()).equals("q")) {

				/*
				 * Twitter`s GET search URL. where
				 * http://search.twitter.com/search.json is the structure of the URL
				 * and "q" is the required parameter. rpp is the optional parameter
				 * but in this example its 100.
				 */
				String u="http://search.twitter.com/search.json?rpp=100&q=";
				
				System.out.print("Enter the String to be searched: #");
				u += br.readLine();
				
				for(int n=1;n<=15;n++){
				u = u+"&page="+n;

				// Accepts hashtag value from user and append this value to the URL
				
				URL url = new URL(u);

				// Creates a HTTP connection usign URL.
				HttpURLConnection request = (HttpURLConnection) url
						.openConnection();

				System.out.println("Sending request to Twitter...");

				// BufferedReader is used to get the inputstream data.
				BufferedReader in = new BufferedReader(new InputStreamReader(
						request.getInputStream()));

				
				
				// The stringbuffer here stores the bufferedReader data that is
				// passed for the JSON parsing
				StringBuffer buffer = new StringBuffer();
				int c;
				while ((c = in.read()) != -1) {
					buffer.append((char) c);
				//	System.out.println(c);
				}
				
				System.out.println(buffer.toString());

				// Json object and JSONArray is used to parse the JSON data

				JSONObject js = new JSONObject(buffer.toString());
				JSONArray tweets = js.getJSONArray("results");
				JSONObject tweet;

				
				HashMap<Integer,String> text=new HashMap<Integer,String>();

				int count = 0;// counts the number of Tweets Processed.

				for (int i = 0; i < tweets.length(); i++) {
					tweet = tweets.getJSONObject(i);

				//	String source = decode.returnDecoded(tweet.getString("source"));
				//	urlSource.add(source);

				//	urlImage.add((String) tweet.getString("profile_image_url"));

				//	urlImageHTTPs.add((String) tweet
					//		.getString("profile_image_url_https"));

					String tweetText=tweet.getString("text");
					
					//char[] textArray=tweetText.toCharArray();
					
					StringBuffer strBuff=new StringBuffer();
					for(int m=0;m<tweetText.length();m++){
						
						char temp=tweetText.charAt(m);
						
						int ascii=(int) temp;
						
						if(ascii>31 && ascii<127){
							strBuff.append(temp);
						}
						
												
					}
					
					text.put(i,strBuff.toString());
					count++;

				} // end for loop

						/*		
				Iterator<String> textItr = text.iterator();

				while (textItr.hasNext()) {
					System.out
							.println("Tweet: " + textItr.next());
				
					if(textItr.next()!=null)
					wr.write(textItr.next());//appends the string to the file
					
				}
				*/
				
				Collection<String> coll=text.values();
				
				Iterator<String> textItr = coll.iterator();
				
				while (textItr.hasNext()) {
					
					String temp2=(String) textItr.next();
				
					System.out
							.println("Tweet: " + temp2);
				
					if(temp2!=null){
					wr.write(temp2);//appends the string to the file
					wr.newLine();
					}
					
				}

				//wr.write(arg0)

				// Prints the total number of urls found
				System.out.println("total number of Tweets Processed: " + count);
			
				// check the response received from the server and notify user
				// accordingly.
				if (request.getResponseCode() == 200)
					System.out.println("\nSearch Completed Successfully\n");
				else {
					System.out.println("Response from Twitter: "
							+ request.getResponseCode() + " "
							+ request.getResponseMessage());
				}

				}//for loop with int m ends here
				
				
				System.out
						.println("Do you want to search a new Hashtag then press enter or press Q to quit");

				decider = br.readLine();
			} // end while loop.. check to process another request or stop
			System.out.println("Thank you for using my Twitter App");

			
			wr.close();
			fstream.close();
			
			} //try ends
			catch(IOException ioe)
			{
			System.err.println("IOException: " + ioe.getMessage());
			}
			
			}

	}
