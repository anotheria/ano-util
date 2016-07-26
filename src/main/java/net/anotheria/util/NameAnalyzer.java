package net.anotheria.util;

import net.anotheria.util.sorter.DummySortType;
import net.anotheria.util.sorter.IComparable;
import net.anotheria.util.sorter.StaticQuickSorter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Simple utility to traverse a directory and analyze the file names.
 * @author lrosenberg
 *
 */
public class NameAnalyzer {
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
	private static Set<String> names = new HashSet<String>();
	/**
	 * Set with extracted elements and their counts.
	 */
	private static HashMap<String,ElementCount> elements = new HashMap<String,ElementCount>();
	/**
	 * Main.
	 * @param a
	 * @throws IOException
	 */
	public static void main(String a[]) throws IOException{
		//test();
		///*
		proceed(new File("."));
		
		System.out.println("Finished found files: "+filesFound+", dirs: "+dirFound+", java files: "+javaFilesFound);
		System.out.println("Found "+names.size()+" names, "+elements.size()+" elements.");
		
		List<ElementCount> list = new ArrayList<ElementCount>();
		list.addAll(elements.values());
		list = StaticQuickSorter.sort(list, new DummySortType());
		for (ElementCount ec : list){
			System.out.println(ec);
		}
		
		//*/
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
			System.out.println("Double name: "+name);
		
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
		File files[] = f.listFiles();
		for (File afile : files)
			proceed(afile);
	}
	
	private static void test(){
		System.out.println("FactoryUtil -> "+uncamelCase("FactoryUtil"));
		System.out.println("PersonalDataController -> "+uncamelCase("PersonalDataController"));
		System.out.println("MatchingDAOFactory -> "+uncamelCase("MatchingDAOFactory"));
		
		
	}
	
	private static List<String> uncamelCase1(String s){
		List<String> list = new ArrayList<String>();
		
		boolean inCamelCase = false;
		boolean previousCamelCase = false;
		String current = "";
		
		for (int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if (!inCamelCase && Character.isUpperCase(c)){
				if (current.length()>0 &&(!previousCamelCase))
					list.add(current);
				current = "" + c;
				previousCamelCase = true;
			}else{
				current += c;
				previousCamelCase = false;
				
			}
		}
		
		if (current!=null && current.length()>0)
			list.add(current);
			
		return list;
	}
	
	private static List<String> uncamelCase2(String s){
		List<String> list = new ArrayList<String>();
		
		boolean inCamelCase = false;
		boolean previousCamelCase = false;
		String current = "";
		
		for (int i=0; i<s.length(); i++){
			char c = s.charAt(i);
			if (Character.isUpperCase(c)){
				current += c;
				inCamelCase = true;
			}else{
				inCamelCase = false;
				if (current.length()>1){
					list.add(current);
				}
				current = "";
			}
		}
		
		if (current!=null && current.length()>0)
			list.add(current);
			
		return list;
	}

	private static List<String> uncamelCase(String s){
		List<String> ret = new ArrayList<String>();
		ret.addAll(uncamelCase1(s));
		ret.addAll(uncamelCase2(s));
		return ret;
	}
	
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























