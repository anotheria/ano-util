package net.anotheria.util.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.anotheria.util.StringUtils;

/**
 * Wordcount utility. Walks recursively through a directory and counts all lines/words/chars in all files which matches the extension (for now .java). 
 * @author lrosenberg
 * @created 25.06.2004
 */
public final class WC {
	/**
	 * Default extension. Files with another extension will be ignored.
	 */
	private static final String EXT = ".java";
	
	private static int totalLines, totalWords, totalChars;
	private static int totalFiles;
	
	public static void main(String a[]){
		
		totalLines = totalWords = totalChars = 0;
		totalFiles = 0;
		
		if (a.length==0){
			a = new String[1];
			a[0] = ".";
		}
		
		List<String> toCheck = Arrays.asList(a);
		new Walker(new WCWorker()).start(toCheck);
		
		System.out.println("Total files:"+totalFiles+" C:"+totalChars+", W:"+totalWords+" L:"+totalLines);
	}
	
	private static class WCWorker implements Worker{

		@Override
		public void processFile(File file) {
			if (!file.getName().endsWith(EXT))
				return;
			FileInputStream fIn = null;
			System.out.print("Checking "+file.getAbsolutePath()+" ");
			try{	
				fIn = new FileInputStream(file);
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
			}finally{
				if (fIn!=null){
					try{
						fIn.close();
					}catch(IOException ignored){}
				}
			}
		}
		
	}
	
	private WC(){
		//prevent initialization.
	}
}
