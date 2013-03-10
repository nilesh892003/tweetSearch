package twitterSearch;

public class decodeURL {
	
public static String returnDecoded(String str){
		
		//char[] charURL=str.toCharArray();
		
	int i;
		StringBuffer sb=new StringBuffer();
		for(i=0;i<str.length();i++){
			
			if(str.charAt(i)=='"')
					break;
			
			if(i==(str.length())-1)
				return str;
		//System.out.println(i);
		}
		i=i+1;
		while(str.charAt(i)!='"'){
			sb.append(str.charAt(i));
			i++;
		//	System.out.println(i);
		}
		
		
		
		//	while(str.charAt(i)!)
			
		return sb.toString();
	}
	
	
}
