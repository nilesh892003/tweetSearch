tweetSearch
===========

Welcome to tweetSearch. 

Description:tweetSearch is an twitter console based search application that returns the recent 100 tweets maching the entered Hashtag value
 
Version 1.0

Author Nilesh Singh.

Contributors

*Takahiro Horikawa <https://github.com/mttkay/signpost>

*Douglas Crockford (JSON-Java, json.org) <https://github.com/douglascrockford/JSON-java>


Libraries:

set these jar files in the classpath.

1: common-codec1.3.jar
2: signport-core1.1-SNAPSHOT.jar

Instruction:

This project has two different way to access twitter recent tweets.

1:Twitter search API 1.0 (deprecated) Does not requires Authentication, anonymously gets the data from server.
2:Twitter REST api 1.1 (GET Search/tweets) Does require oAuth authentication.


JSON.org: I am using JSON.org`s JSON parser to parse the received data(JSON) into JSONArray and then process the data.

Note: Modified the JSON parser file( JSONObject.java) method getString. 
      Problem: In the Twitter application sometimes the User-url value is null then the default implementation was throwing an exceptiong.Modified the method int he library to suit the application requirement.
 
 
Twitter Search API 1.0
 
How to use:

Steps:
 
 1:Compile and run searchTwitter.java
 2:enter the Hashtage value when prompted on the Screen
 3:Done, The application will print the output result on the console and option to Quit or try another Search
 
 Output: The source and Image URLs from the tweet data (Unigue values)
 
 Prints all the Source URLs
 Prints all the user profile image URLs
 Prints all the user profile image HTTPs URLs
 
 
 prints the total number of tweets processed, total number of source and image URLs printed.
 
 
 
 
 
 
Twitter REST API 1.1  

 How to use:

 Steps:

 1: compile and run searchTwitterAuthentication.java  
 2:visit the prompted URL on the console, Authorize the application and copy,paste the unique PIN from the authorization page to application console
 3: enter hashtag value when prompted on the command line.
 4:Done. the application will prints the output on the console and option to try another search or quit.( Note: Once we authorize the application then we can run any number of search on the same run)

 Output: All the URLs found in the JSON file ( Unique values)

 Prints all the source URLs
 Prints all the Entity URLs
 Prints all the User URLs
 Prints all the user Profile image URLs
 Prints all the user Profile image HTTPs URLs


 total number of Records: the number of tweets we received from Twitter.
 *  total number of unique Source URLs: The source URL available in the JSON Object
 *	total Number of unique Entity Urls: In the Twitter JSON tree it containts. statuses->entities-Urls->url array and 
 *		         						statuses->user->entities->description->URls->url array 
 *	total number of USER URLs: statuses->user-> url value.
 *	
 *	total Number of unique images: "statuses->user->profile and background images.
 *	total Number of unique Https images: statuses->user->profile and background images with HTTPs protocol
 


