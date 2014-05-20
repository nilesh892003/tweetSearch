package twitterCal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class trial {
	public static HashMap<String,Integer> map=new HashMap<String,Integer>();
	public static HashMap classify=new HashMap();
	
	
	public static void main(String[] args){
		
		try{
			
			File file=new File("D:/TwitterBostondataForProcessing.txt");
			FileWriter fstream= new FileWriter(file,true); //the true will append the new data
			BufferedWriter wr=new BufferedWriter(fstream);
	//File file=new File("D:/twitterData2.txt");
	
	//FileWriter fstream= new FileWriter(file,true); //the true will append the new data
	//BufferedWriter wr=new BufferedWriter(fstream);
			BufferedReader br = new BufferedReader(new FileReader("D:\\out_BostonData.txt"));
			
			String s=br.readLine();
			// s=br.readLine();
		do{
			//System.out.println(s);
			
			String[] split=new String[141];
			String[] slashes=new String[5];
			
			split=s.split(" ");
			
			StringBuilder sb=new StringBuilder(); // new for every tweet
			String classOf="";
			
			for(int i=0;i<split.length;i++){
				//System.out.println(split[i]);
				
				slashes=split[i].split("/");
				
//classifier concept..
				
				
				String cl=slashes[1];
			//	System.out.println(cl.charAt(0)+"  "+slashes[1]);
				if(cl.charAt(0)=='B'){
					//System.out.println("Classifies is: "+ slashes[1]+ "B");
					
					sb.append(" "+slashes[0]);
					classOf=slashes[1].substring(2);
				}
				
				if(cl.charAt(0)=='I'){
					//System.out.println("Classifies is: "+ slashes[1]+ "B");
					sb.append(" "+slashes[0]);
					//System.out.println(sb.toString());
				}
				
				if(cl.charAt(0)=='O'){
					//System.out.println("Classifies is: "+ slashes[1]+ "B");
					//sb.append(" "+slashes[1]);
					System.out.println("Class of :" +sb.toString()+ " is :"+classOf);
					
					classOf="";
				}
				
				
				
				//	for(int j=0;j<slashes.length;j++){
						//System.out.println(slashes[j]);
						//System.out.println(slashes[0]+"  ");
						
						if(slashes[2].equals("NN") ||slashes[2].equals("JJ")||slashes[2].equals("NNS")||slashes[2].equals("NNP")||slashes[2].equals("NNPS")||slashes[2].equals("RB")){
							//||slashes[2].equals("JJ")||slashes[2].equals("NNS")||slashes[2].equals("NNP")||slashes[2].equals("NNPS")||slashes[2].equals("RB")
							//System.out.println(slashes[2]);
							slashes[0].trim();
							String lowerCaseVal=slashes[0].toLowerCase();
							if(!slashes[1].equals("O"))
						//	System.out.println("WOrd  "+slashes[0]+"  Classification  "+slashes[1]);
							
							if(map.containsKey(lowerCaseVal))
									{
								int m=map.get(lowerCaseVal); 
								
								//System.out.println(slashes[0]+"  "+m);
								m++;
							//	map.remove(slashes[0]);
								map.put(lowerCaseVal,m);
								//System.out.println(slashes[0]+"  ");
							
									}
							else
							{
								map.put(lowerCaseVal, 1);
							}
						} //upper if ends
					//}
				
			}//for ends
			
			
			
			//System.out.println("I am here");
			
			//System.out.println(s.split(" "));
			s=br.readLine();
			//System.out.println("hello"+s);
		}while(s!=null);
		
		
		Collection collKeys=map.keySet();
		Collection collValues=map.values();
		
		Iterator keyItr = collKeys.iterator();
		Iterator valuesItr=collValues.iterator();
//		
////		try {
////			while (keyItr.hasNext() && valuesItr.hasNext()) {
////				//System.out.println("im here");
////			
////				String key=""+keyItr.next();
////				String value=""+valuesItr.next();
////				
////				System.out.println("  "+key+"   "+value);
////			
////			String temp2=key+":"+value;
////			
//////			if(temp2!=null){
//////				if(Integer.parseInt(value)>5 && key.length()>=3){
//////			wr.write(temp2);//appends the string to the file
//////			
//////			wr.newLine();}
//////			}
//////			}
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		
//		
//	//	wr.close();
		} //ends try
		
		catch(Exception e){
			
		}
	
	}//ends main
}
