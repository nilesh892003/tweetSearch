// Author: Nilesh Singh, George Mason University
// This class provides methods to decode the URL and print it in proper form without tags 

package twitterSearch;

public class decodeURL {
	
public static String returnDecoded(String str){
	//requires: URL String input 
	//Effects: Remove the tags form the URL String and returns the string in proper URL form 
		
		
	int i;
		StringBuffer sb=new StringBuffer();
		for(i=0;i<str.length();i++){
			
			if(str.charAt(i)=='"')
					break;
			
			if(i==(str.length())-1)
				return str;
	
		}
		i=i+1;
		while(str.charAt(i)!='"'){
			sb.append(str.charAt(i));
			i++;
	
		}
		
		
		
			
		return sb.toString();
	}
	
	
}
