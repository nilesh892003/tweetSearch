/*Author: Nilesh Singh, George Mason University, MS Computer Science
 * Date: March, 9th, 2013.
 * 
 * Topic:This is an app console based application that will be executed in terminal or from the command line.
 * This app accepts a hashtag keyword ( the hashtag keyword used with twitter) as an argument and 
 * then only print out unique urls(Source, Image urls, and Image https urls) found in the 100 most recent tweets that matched the  hashtag.
 * 
 * 
 *API: Twitter REST API 1.1. (GET Search/Tweets)
 *Version: 1.1
 *
 *JSON.org: I am using JSON.org`s JSON parser to parse the received data(JSON) into JSONArray and then process the data.
 *
 *Note: Twitter search API, version 1.1 requires user authentication by oAuth. In this example signpost API for 
 *oAuth Twitter authentication is used for to authorize the user requests.
 *
 *Modified the JSON parser file( JSONObject.java) method getString. 
 *Problem: In the Twitter application sometimes the User-url value is null then it was throwing an exceptiong.
 *Modified the method int he library to suit the application requirement.
 *
 *Application Output Description:
 *
 *  total number of Records: the number of tweets we received from Twitter.
 *	total number of unique Source URLs: The source URL available in the JSON Object
 *	total Number of unique Entity Urls: In the Twitter JSON tree it containts. statuses->entities-Urls->url array and 
 *		         						statuses->user->entities->description->URls->url array 
 *	total number of USER URLs: statuses->user-> url value.
 *	
 *	total Number of unique images: "statuses->user->profile and background images.
 *	total Number of unique Https images: statuses->user->profile and background images with HTTPs protocol
 *	    
 *
 */

package twitterSearch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
//imports to parse the JSON 
import json.JSONArray;
import json.JSONObject;

//import for the aurthorization
import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.signature.SignatureMethod;

	public class searchTwitterAuthentication {

	    public static void main(String[] args) throws Exception {
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

	     //   Twitter does not support callbacks, thus pass OOB
	        String authUrl = provider.retrieveRequestToken(OAuth.OUT_OF_BAND);

	        //Print the Token and Token secret received 
	        System.out.println("Request token: " + consumer.getToken());
	        System.out.println("Token secret: " + consumer.getTokenSecret());

	        
	        //URL to authenticate the request and authorize the application .
	       System.out.println("\n\nKindly visit:  " + authUrl+ "  ... to authorize this application\n");
	      
	       System.out.print("Enter the PIN code received and hit ENTER when you're done:");
        
	        
	       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        String pin = br.readLine();

	        
	        //send the pin to twitter and ask for the Access Token and Acess Token Secret.
	        provider.retrieveAccessToken(pin);
	        
	      //prints the access token received from the twitter
	        System.out.println("Access token: " + consumer.getToken());
	        System.out.println("Token secret: " + consumer.getTokenSecret());

	  
	        
	        
	        String decider="";
	        
	        while(!(decider.toLowerCase()).equals("q")){
	        
	        	
	    	String u="https://api.twitter.com/1.1/search/tweets.json?count=100&q=";
	    	
	    	// Accepts hashtag value from user and append this value to the URL
	    	System.out.print("Enter the String to be searched: #");
	    	u+=br.readLine();
	    	URL url = new URL(u);
	    	
	    	// Creates a HTTP connection usign URL.
	        HttpURLConnection request = (HttpURLConnection) url.openConnection();

	        consumer.sign(request);

	        
	        
	        
	        System.out.println("Sending request to Twitter...");
	        request.connect();
	        
	        
	        StringBuffer buffer = new StringBuffer();
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        
	        
	        
	        int c;
	        while((c=in.read())!=-1)
	        {
	            buffer.append((char)c);
	        }
	        
	       // System.out.println(buffer.toString());
	     //   in.close();
	        
	        // JsonObject Api to parse the inputStream into Json.
	        JSONObject js = new JSONObject(buffer.toString());
	       
	        JSONArray tweets = js.getJSONArray("statuses");
	        
	        JSONObject tweet;
	        
	        HashSet<String> urlImage=new HashSet<String>();
	        HashSet<String> urlSource=new HashSet<String>();
	        
	        HashSet<String> urlImageHTTPs=new HashSet<String>();
	        
	        HashSet<String> urlEntity=new HashSet<String>();
	        
	        HashSet<String> userUrl=new HashSet<String>();
	        
	        
	        int count=0;
	     
	        for(int i=0;i<tweets.length();i++) {
	          
	        	tweet = tweets.getJSONObject(i); //select one JSON object at once.
	            
	            JSONObject tweetUser=tweet.getJSONObject("user");
	            
	       
	            //find all the URL`s from User->Entities->Description->URLS->URL array.
	            JSONObject entityDesc=(tweetUser.getJSONObject("entities")).getJSONObject("description");
	           
	            JSONArray descURL=entityDesc.getJSONArray("urls");
	            
	            for(int j=0;j<descURL.length();j++){
	            	JSONObject temp=descURL.getJSONObject(j);
	            	
	            	urlEntity.add((String) temp.getString("url"));
	            }
	            
	            
	            //Find all the URL`s from entities->URLs-> url array
	        JSONObject entity=tweet.getJSONObject("entities");
	        JSONArray entityUrls=entity.getJSONArray("urls");
	        
	        for(int j=0;j<entityUrls.length();j++){
	        	JSONObject temp=entityUrls.getJSONObject(j);
            	
            	urlEntity.add((String) temp.getString("url"));
	        }
	            
	        
	        //Add source URL to urlSource HashSet
	            String source=decodeURL.returnDecoded(tweet.getString("source"));
	            urlSource.add(source);
	        
	            //Add user Profile Image and profile_background_image URL to HashSet urlImage
	           	urlImage.add((String)tweetUser.getString("profile_image_url"));	           	    
	           	urlImage.add((String)tweetUser.getString("profile_background_image_url"));
	           	
	           	//Add user profile image HTTPS links and background_image HTTPs image links to urlImageHTTPs hashset
	           	urlImageHTTPs.add((String)tweetUser.getString("profile_image_url_https"));
	           	urlImageHTTPs.add((String)tweetUser.getString("profile_background_image_url_https"));
	           	
	           	//Add user URL to userURL HashSet
	           	userUrl.add(tweetUser.getString("url"));
	            
	            //Count the total number of tweets processed.
	           	count++;
	        }   //end for loop
	        
	        
	        
	        //Iterate over all the HashSets to print all the URLs.
	        Iterator<String> sourceItr=urlSource.iterator();
	        System.out.println("\n\n Unique Source urls:\n\n");
	        while(sourceItr.hasNext()){
	        	System.out.println("Unique Source URL: "+sourceItr.next());
	        }
	        
	        System.out.println("\n\nUser Description and Entity  URLS\n\n");
	        Iterator<String> entityItr=urlEntity.iterator();
	        while(entityItr.hasNext()){
	        	System.out.println(entityItr.next());
	        }
	        
	        System.out.println("\n\nUser URLS\n\n");
	        Iterator<String> userItr=userUrl.iterator();
	        while(userItr.hasNext()){
	        	System.out.println(userItr.next());
	        }
	        
	        
	        Iterator<String> imageItr=urlImage.iterator();
	        System.out.println("\n\n Unique image urls:\n\n");
	        while(imageItr.hasNext()){
	        	System.out.println("Unique user image URL: "+imageItr.next());
	        }
	        
	        System.out.println("\n\n Unique Https image urls:\n\n");
	        Iterator<String> imageItrHttps=urlImageHTTPs.iterator();
	        
	        while(imageItrHttps.hasNext()){
	        	System.out.println("Unique user image URL: "+imageItrHttps.next());
	        }
	        
	     
	        
	        
	        System.out.println("\ntotal number of Records: "+count);
	        System.out.println("total number of unique Source URLs: "+urlSource.size());
	        System.out.println("total Number of unique Entity Urls: "+urlEntity.size());	         
	        System.out.println("total number of USER URLs: "+userUrl.size());
	        System.out.println("total Number of unique images: "+urlImage.size());
	        System.out.println("total Number of unique Https images: "+urlImageHTTPs.size());
	       
	        if (request.getResponseCode() == 200)
				System.out.println("\nSearch Completed Successfully\n");
			else {
				System.out.println("Response from Twitter: "
						+ request.getResponseCode() + " "
						+ request.getResponseMessage());
			}

	   
	        
	        System.out.println("Do you want to search a new Hashtag then press enter or press Q to quit");
	        
	        decider=br.readLine();
	    }//end while loop.. check to continue or stop
	        System.out.println("Thank you for using my Twitter App");

	    }
	    
	   
	}


