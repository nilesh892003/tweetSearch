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
 *Modified the method int he library to suit the application requirement.
 */

package twitterSearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import twitterSearch.decode;

import json.JSONArray;
import json.JSONObject;

public class searchTwitter {

	public static void main(String[] args) throws Exception {
		// Effects: Prints the unique URLs from the recent tweets.

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

			String u = "http://search.twitter.com/search.json?rpp=100&q=";

			// Accepts hashtag value from user and append this value to the URL
			System.out.print("Enter the String to be searched: #");
			u += br.readLine();
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
			}

			// Json object and JSONArray is used to parse the JSON data

			JSONObject js = new JSONObject(buffer.toString());
			JSONArray tweets = js.getJSONArray("results");
			JSONObject tweet;

			// HashSet Does not store duplicate values. So here HashTag is used
			// to store the unique URLs(Source, Image and HTTPs image urls)

			HashSet<String> urlImage = new HashSet<String>();
			HashSet<String> urlSource = new HashSet<String>();
			HashSet<String> urlImageHTTPs = new HashSet<String>();

			int count = 0;// counts the number of Tweets Processed.

			for (int i = 0; i < tweets.length(); i++) {
				tweet = tweets.getJSONObject(i);

				String source = decode.returnDecoded(tweet.getString("source"));
				urlSource.add(source);

				urlImage.add((String) tweet.getString("profile_image_url"));

				urlImageHTTPs.add((String) tweet
						.getString("profile_image_url_https"));

				count++;

			} // end for loop

			// Iterate over the HashSets to print the corresponding Values.

			Iterator<String> sourceItr = urlSource.iterator();
			System.out.println("\n\n Unique Source urls:\n\n");
			while (sourceItr.hasNext()) {
				System.out.println("Unique Source URL: " + sourceItr.next());
			}

			Iterator<String> imageItr = urlImage.iterator();
			System.out.println("\n\n Unique image urls:\n\n");
			while (imageItr.hasNext()) {
				System.out.println("Unique user image URL: " + imageItr.next());
			}

			System.out.println("\n\n Unique Https image urls:\n\n");
			Iterator<String> imageItr1 = urlImageHTTPs.iterator();

			while (imageItr1.hasNext()) {
				System.out
						.println("Unique user image URL: " + imageItr1.next());
			}

			// Prints the total number of urls found
			System.out.println("total number of Tweets Processed: " + count);
			System.out.println("total Number of unique images: "
					+ urlImage.size());
			System.out.println("total Number of unique Https images: "
					+ urlImageHTTPs.size());

			System.out.println("total number of unique Source URLs: "
					+ urlSource.size());

			// check the response received from the server and notify user
			// accordingly.
			if (request.getResponseCode() == 200)
				System.out.println("\nSearch Completed Successfully\n");
			else {
				System.out.println("Response from Twitter: "
						+ request.getResponseCode() + " "
						+ request.getResponseMessage());
			}

			System.out
					.println("Do you want to search a new Hashtag then press enter or press Q to quit");

			decider = br.readLine();
		} // end while loop.. check to process another request or stop
		System.out.println("Thank you for using my Twitter App");

	}

}
