// Author: Nilesh Singh, George Mason University
//This class provides a method to take a string input and returns the valid URL 

package twitterSearch;
public class decode {

	public static String returnDecoded(String str){
	//Requires: A string input
	//Effects: Returns a Valid URL string
		
		StringBuffer sb=new StringBuffer();
		for(int i=11;i<str.length();i++){
			
			
			int end=0;
			if(str.charAt(i)!='&')
			{
				sb.append(str.charAt(i));
			}
			
			else{
				
				StringBuilder test=new StringBuilder();
				test.append("&");
				while(str.charAt(i+end)!=';')
					{
					end++;
					test.append(str.charAt(i+end));
					}

				if((test.toString()).equals("&gt;"))
					{
					
				
					break;
					}
			
				i=i+end;
			}
			
		}
		
		return sb.toString();
	}
	
	
}
