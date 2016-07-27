package net.anotheria.util.tools;

import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Wordcount utility. Walks recursively through a directory and counts all lines/words/chars in all files which matches the extension (for now .java). 
 * @author lrosenberg
 * @since 25.06.2004
 */
public final class WC {
	/**
	 * Default extension. Files with another extension will be ignored.
	 */
	private static final String EXT = ".java";

	private static final Logger log = LoggerFactory.getLogger(WC.class);

	private static int totalLines, totalWords, totalChars;
	private static int totalFiles;
	
	public static void main(String... a){
		
		totalLines = totalWords = totalChars = 0;
		totalFiles = 0;
		
		if (a.length==0){
			a = new String[1];
			a[0] = ".";
		}
		
		List<String> toCheck = Arrays.asList(a);
		new Walker(new WCWorker()).start(toCheck);
		
		System.out.println("Total files:"+totalFiles+" total chars: "+totalChars+", total words: "+totalWords+", total length: "+totalLines);
	}
	
	private static class WCWorker implements Worker{

		@Override
		public void processFile(File file) {
			if (!file.getName().endsWith(EXT))
				return;
			try (FileInputStream fIn = new FileInputStream(file)) {
				byte[] d = new byte[fIn.available()];
				fIn.read(d);
				String s = new String(d);

				s = StringUtils.removeChar(s, '\r');
				s = StringUtils.removeCComments(s);
				s = StringUtils.removeCPPComments(s);

				int lines, chars;

				int words = lines = chars = 0;

				String currentLine = "";

				boolean inWord = false;
				int i = 0;
				while( i<s.length()){
					char c = s.charAt(i);

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
								(!currentLine.startsWith("package")))
							lines++;
						currentLine = "";
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

			}catch(IOException e){
				log.error(e.getMessage(), e);
			}
		}
		
	}
	
	private WC(){
		//prevent initialization.
	}
}
