package net.anotheria.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * TODO Please remind lrosenberg to comment this class.
 * @author lrosenberg
 * Created on 25.06.2004
 */
public class WC {
	
	private static String EXT = ".java";
	
	static int totalLines, totalWords, totalChars;
	static int totalFiles;
	
	public static void main(String a[]){
		
		totalLines = totalWords = totalChars = 0;
		totalFiles = 0;
		
		for (int i=0; i<a.length; i++){
			String toCheck = a[i];
			System.out.println(toCheck);
			proceed(new File(toCheck));
		}
		
		System.out.println("Total files:"+totalFiles+" C:"+totalChars+", W:"+totalWords+" L:"+totalLines);
	}
	
	private static void proceed(File f){
		if (f.isDirectory())
			proceedDir(f);
		else
			proceedFile(f);
	}
	
	private static void proceedFile(File f){
		if (!f.getName().endsWith(EXT))
			return;		
		System.out.print("Checking "+f.getAbsolutePath()+" ");
		try{	
			FileInputStream fIn = new FileInputStream(f);
			byte d[] = new byte[fIn.available()];
			fIn.read(d);
			String s = new String(d);
			//s = StringUtils.r
			//System.out.print(s.length());
			
			//System.out.println();
			
			//System.out.println(s);
			//System.out.println("=========================");
			
			s = StringUtils.removeChar(s, '\r');
			s = StringUtils.removeCComments(s);
			s = StringUtils.removeCPPComments(s);
			
			char c;
			int i =0 ;
			
			int words, lines, chars;
			boolean inWord = false;
			
			words = lines = chars = 0;
			
			String currentLine = "";
			
			while( i<s.length()){
				c = s.charAt(i);
				//System.out.print(c);
				
				if (c==' ' || c=='\t' || c=='\n'){
					if (inWord)
						inWord = false;
				}else{
					chars++;
					currentLine += c;
					if (!inWord){
						inWord=true;
						words++;
					}
				}
				
				if (c=='\n'){
					if (currentLine.length()>0 && 
							(!currentLine.startsWith("import")) && 
							(!currentLine.startsWith("package")) /*&&
							/*(!currentLine.equals("}"))*/)
						lines++;
					currentLine = new String("");
				}
				
				i++;
			}
			
			System.out.print(" C: "+chars);
			System.out.print(" W: "+words);
			System.out.print(" L: "+lines);
			System.out.println();
			
			totalLines += lines;
			totalWords += words;
			totalChars += chars;
			totalFiles++;
			
			
			//System.exit(0);
			
		}catch(IOException e){
			e.printStackTrace();		
		}
	}
	
	private static void proceedDir(File f){
		File files[] = f.listFiles();
		for (int i=0; i<files.length; i++){
			File aFile = files[i];
			proceed(aFile);
		}
	}
}
