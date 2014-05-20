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
import java.util.Iterator;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.signature.SignatureMethod;

import json.JSONArray;
import json.JSONObject;

public class TwitterTweetOauth {
	/*
	 * Author: Nilesh Singh, George Mason University, MS Computer Science Date:
	 * May, 20th, 2014.
	 * 
	 * Topic:This is an app console based application that will be executed in
	 * terminal or from the command line. This app accepts a hashtag keyword (
	 * the hashtag keyword used with twitter) as an argument and then only print
	 * out unique urls(Source, Image urls, and Image https urls) found in the
	 * 100 most recent tweets that matched the hashtag.
	 * 
	 * 
	 * API: Twitter search API. (GET Search)Version: 1.1
	 * 
	 * JSON.org: I am using JSON.org`s JSON parser to parse the received
	 * data(JSON) into JSONArray and then process the data.
	 * 
	 * Note: Twitter search API, version 1.1 does not require user
	 * authentication. We can search the most recent tweetsfrom the twitter
	 * anonymously.
	 * 
	 * *Modified the JSON parser file( JSONObject.java) method getString.
	 * Problem: In the Twitter application sometimes the User-url value is null
	 * then it was throwing an exceptiong.Modified the method int he library to
	 * suit the application requirement.O
	 */

	public static void main(String[] args) throws Exception {
		// Effects: Prints the unique URLs from the recent tweets.
		// Effects: Prints the unique URLs from the recent tweets.

		// Used signPost api for the oAuth authentication (Twitter)
		OAuthConsumer consumer = new DefaultOAuthConsumer(
				"XVc6JleivdpCBuvhBVypQ",
				"zzzQNDtEOdPbHqEl1xURzyvPV2wyo04WHuWAGcdsU",
				SignatureMethod.HMAC_SHA1);

		OAuthProvider provider = new DefaultOAuthProvider(consumer,
				"https://api.twitter.com/oauth/request_token",
				"https://api.twitter.com/oauth/access_token",
				"https://api.twitter.com/oauth/authorize");

		System.out.println("Fetching request token from Twitter...");

		// Twitter does not support callbacks, thus pass OOB
		String authUrl = provider.retrieveRequestToken(OAuth.OUT_OF_BAND);

		// Print the Token and Token secret received
		System.out.println("Request token: " + consumer.getToken());
		System.out.println("Token secret: " + consumer.getTokenSecret());

		// URL to authenticate the request and authorize the application .
		System.out.println("\n\nKindly visit:  " + authUrl
				+ "  ... to authorize this application\n");

		System.out
				.print("Enter the PIN code received and hit ENTER when you're done:");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		String pin = reader.readLine();

		// send the pin to twitter and ask for the Access Token and Acess Token
		// Secret.
		provider.retrieveAccessToken(pin);

		// prints the access token received from the twitter
		System.out.println("Access token: " + consumer.getToken());
		System.out.println("Token secret: " + consumer.getTokenSecret());

		try {

			File file = new File("D:/Demo.txt");
			FileWriter fstream = new FileWriter(file, true); // the true will
																// append the
																// new data
			BufferedWriter wr = new BufferedWriter(fstream);

			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));

			String decider = "";

			// Program run check. This provides an option to the user to run the
			// process again or quit.
			while (!(decider.toLowerCase()).equals("q")) {

				/*
				 * Twitter`s GET search URL. where
				 * http://search.twitter.com/search.json is the structure of the
				 * URL and "q" is the required parameter. rpp is the optional
				 * parameter but in this example its 100.
				 */
				String u = "https://api.twitter.com/1.1/search/tweets.json?count=100&q=";

				System.out.print("Enter the String to be searched: #");
				u += br.readLine();
				URL url = new URL(u);

				// Creates a HTTP connection usign URL.
				HttpURLConnection request = (HttpURLConnection) url
						.openConnection();

				consumer.sign(request);

				System.out.println("Sending request to Twitter...");
				request.connect();

				StringBuffer buffer = new StringBuffer();

				BufferedReader in = new BufferedReader(new InputStreamReader(
						request.getInputStream()));

				int c;
				while ((c = in.read()) != -1) {
					buffer.append((char) c);
				}

				System.out.println(buffer.toString());

				// Json object and JSONArray is used to parse the JSON data
				JSONObject js = new JSONObject(buffer.toString());
				JSONArray tweets = js.getJSONArray("statuses");
				JSONObject tweet;

				HashMap<Integer, String> text = new HashMap<Integer, String>();

				int count = 0;// counts the number of Tweets Processed.

				for (int i = 0; i < tweets.length(); i++) {
					tweet = tweets.getJSONObject(i);
					String tweetText = tweet.getString("text");
					StringBuffer strBuff = new StringBuffer();
					for (int m = 0; m < tweetText.length(); m++) {
						char temp = tweetText.charAt(m);
						int ascii = (int) temp;
						if (ascii > 31 && ascii < 127) {
							strBuff.append(temp);
						}
					}

					text.put(i, strBuff.toString());
					count++;
				} // end for loop

				Collection<String> coll = text.values();
				Iterator<String> textItr = coll.iterator();

				while (textItr.hasNext()) {
					String temp2 = (String) textItr.next();
					System.out.println("Tweet: " + temp2);
					if (temp2 != null) {
						wr.write(temp2);// appends the string to the file
						wr.newLine();
					}
				}

				// Prints the total number of urls found
				System.out
						.println("total number of Tweets Processed: " + count);

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
			wr.close();
			fstream.close();

		} // try ends
		catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}

	}
}
