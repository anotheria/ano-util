package net.anotheria.util;

import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.IComparable;
import net.anotheria.util.sorter.StaticQuickSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Simple utility to traverse a directory and analyze the file names.
 *
 * @author lrosenberg
 * @version $Id: $Id
 */
public class NameAnalyzer {

	private static final Logger log = LoggerFactory.getLogger(NameAnalyzer.class);
	
	/**
	 * Number of traversed directories.
	 */
	private static long dirFound;
	/**
	 * Number of touched files.
	 */
	private static long filesFound;
	/**
	 * Number of touched java files.
	 */
	private static long javaFilesFound;
	/**
	 * Set with all names.
	 */
	private static Set<String> names = new HashSet<>();
	/**
	 * Set with extracted elements and their counts.
	 */
	private static HashMap<String,ElementCount> elements = new HashMap<>();
	/**
	 * Main.
	 *
	 * @param a a {@link java.lang.String} object.
	 */
	public static void main(String... a) {
		proceed(new File("."));
		
		log.info("Finished found files: "+filesFound+", dirs: "+dirFound+", java files: "+javaFilesFound);
		log.info("Found "+names.size()+" names, "+elements.size()+" elements.");
		
		List<ElementCount> list = new ArrayList<>(elements.values());
		list = StaticQuickSorter.sort(list, new DummySortType());
		for (ElementCount ec : list){
			log.info(ec.toString());
		}

	}
	
	private static void proceed(File f){
		filesFound++;
		if (f.isDirectory())
			proceedDirectory(f);
		else
			proceedFile(f);
	}
	
	private static void proceedFile(File f){
		String name = f.getName();
		if (!(name.endsWith(".java")))
			return;
		javaFilesFound++;
		name = name.substring(0, name.length()-".java".length());
		if (!names.add(name))
			log.info("Double name: "+name);
		
		List<String> listOfElements = uncamelCase(name);
		for (String s : listOfElements){
			ElementCount ec = elements.get(s);
			if (ec == null){
				ec = new ElementCount(s);
				elements.put(s, ec);
			}
			ec.increase();
		}
	}
	
	private static void proceedDirectory(File f){
		dirFound++;
		File[] files = f.listFiles();
		for (File afile : files)
			proceed(afile);
	}
	
	private static void test(){
		log.info("FactoryUtil -> "+uncamelCase("FactoryUtil"));
		log.info("PersonalDataController -> "+uncamelCase("PersonalDataController"));
		log.info("MatchingDAOFactory -> "+uncamelCase("MatchingDAOFactory"));
		
		
	}
	
	private static List<String> uncamelCase1(CharSequence s){
		List<String> list = new ArrayList<>();
		
		boolean inCamelCase = false;
		boolean previousCamelCase = false;
		String current = "";
		
		for (int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if (!inCamelCase && Character.isUpperCase(c)){
				if (!current.isEmpty() &&(!previousCamelCase))
					list.add(current);
				current = String.valueOf(c);
				previousCamelCase = true;
			}else{
				current += c;
				previousCamelCase = false;
				
			}
		}
		
		if (current!=null && !current.isEmpty())
			list.add(current);
			
		return list;
	}
	
	private static List<String> uncamelCase2(CharSequence s){
		List<String> list = new ArrayList<>();

		String current = "";
		
		for (int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if (Character.isUpperCase(c)){
				current += c;
			}else{
				if (current.length()>1){
					list.add(current);
				}
				current = "";
			}
		}
		
		if (current!=null && !current.isEmpty())
			list.add(current);
			
		return list;
	}

	private static List<String> uncamelCase(CharSequence s){
		List<String> ret = new ArrayList<>();
		ret.addAll(uncamelCase1(s));
		ret.addAll(uncamelCase2(s));
		return ret;
	}

	/**
	 * Element counter for different types of files.
	 */
	public static class ElementCount implements IComparable{
		String name;
		int count;
		
		public ElementCount(String aName){
			name = aName;
			count = 0;
		}
		
		@Override
		public int compareTo(IComparable anotherObject, int method) {
			return BasicComparable.compareLong(count, ((ElementCount)anotherObject).count);
		}
		
		public void increase(){
			count++;
		}
		
		@Override public String toString(){
			return name+" = "+count;
		}
		
		
	}
}























