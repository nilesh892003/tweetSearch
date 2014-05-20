package twitterCal;

import java.io.BufferedReader;
import java.io.FileReader;

public class twitterInfo {
	
	public static void main(String[] args) throws Exception
	{
	
	BufferedReader br = new BufferedReader(new FileReader("D:/twitterDataOutput.txt"));
	
	StringBuffer buffer = new StringBuffer();
	int c;
	while ((c = br.read()) != -1) {
		buffer.append((char) c);
	//	System.out.println(c);
	}
	
	//String temp=br.readLine();
	System.out.println(buffer.toString());
	
	
	
	
	
	}
	
}
