package twitterSearch;
public class decode {

	public static String returnDecoded(String str){
		
		//char[] charURL=str.toCharArray();
		
		StringBuffer sb=new StringBuffer();
		for(int i=11;i<str.length();i++){
			
			
			int end=0;
			if(str.charAt(i)!='&')
			{
				sb.append(str.charAt(i));
			}
			
			else{
				
			//	int start=i;
				StringBuilder test=new StringBuilder();
				test.append("&");
				while(str.charAt(i+end)!=';')
					{
					end++;
					test.append(str.charAt(i+end));
					}
				
				//test.append(";");
				
			//	System.out.println("found"+test.toString());
			//	System.out.println();
				if((test.toString()).equals("&gt;"))
					{
					
				//	System.out.println("found");
					break;
					}
			//	System.out.println(end);
				i=i+end;
			}
			
			
			
		}
		
		return sb.toString();
	}
	
	
}
