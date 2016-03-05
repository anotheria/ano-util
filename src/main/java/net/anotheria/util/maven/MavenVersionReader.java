package net.anotheria.util.maven;

import net.anotheria.util.IOUtils;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class MavenVersionReader {
	
	public static final MavenVersion readVersionFromJar(File f){
		if (!f.exists())
			throw new IllegalArgumentException("File doesn't exists");
		try{
			JarFile jar = new JarFile(f);
			Enumeration<JarEntry> entries = jar.entries();
			while(entries.hasMoreElements()){
				JarEntry entry = entries.nextElement();
				if (entry.getName().startsWith("META-INF") && entry.getName().endsWith("/pom.properties")){
					String myName = entry.getName();
					JarInputStream input = new JarInputStream(new FileInputStream(f));
					JarEntry fromStream = null;
					while(fromStream==null || (!fromStream.getName().equals(myName))){
						fromStream = input.getNextJarEntry();
					}
					int size = (int)fromStream.getSize();
					if (size>0 ) {
					    byte b[]  = new byte[size];
					    input.read(b);
					    String content = new String(b);
					    return readVersionFromString(content, f.lastModified());
				    }
					if (size == -1){
						StringBuilder b = new StringBuilder();
						int c;
						while( (c = input.read())!=-1){
							b.append((char)c);
						}
						return readVersionFromString(b.toString(), f.lastModified());

					}
				}
				
			}
			
			return null;
		}catch(IOException e){
			throw new IllegalArgumentException("Couldn't parse jar file", e);
		}

		
	}
	public static final MavenVersion readVersionFromString(String content, long timestamp){
		String lines[] = StringUtils.tokenize(StringUtils.removeChar(content, '\r'), '\n');
		HashMap<String, String> properties = new HashMap<String, String>();
		for (String line: lines){
			if (line!=null)
				line = line.trim();
			if (line==null || line.length()==0 || line.startsWith("#") )
				continue;
			String tokens[] = StringUtils.tokenize(line,'=');
			if (tokens!=null && tokens.length==2)
				properties.put(tokens[0], tokens[1]);
		}
		MavenVersion ret = new MavenVersion();
		ret.setArtifact(properties.get("artifactId"));
		ret.setVersion(properties.get("version"));
		ret.setGroup(properties.get("groupId"));
		ret.setFileTimestamp(NumberUtils.makeISO8601TimestampString(timestamp));
		return ret;
	}
		
	public static final MavenVersion readVersionFromFile(File f){
		if (!f.exists())
			throw new IllegalArgumentException("File doesn't exists");
		try{
			
			String content = IOUtils.readFileAtOnceAsString(f);
			return readVersionFromString(content,f.lastModified());
		}catch(IOException e){
			throw new IllegalArgumentException("Couldn't parse file", e);
		}
	}
	
	public static final MavenVersion findVersionInDirectory(File dir){
		if (!dir.exists())
			throw new IllegalArgumentException("Directory "+dir.getAbsolutePath()+" doesn't exists.");
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Directory "+dir.getAbsolutePath()+" is not a directory.");
		File files[] = dir.listFiles();
		for (int i=0; i<files.length; i++){
			if (files[i].getName().equals("pom.properties")){
				return readVersionFromFile(files[i]);
			}
		}
		
		for (int i=0; i<files.length; i++){
			File aDir = files[i];
			if (!aDir.isDirectory())
				continue;
			MavenVersion v = findVersionInDirectory(aDir);
			if (v!=null)
				return v;
		}
		
		return null;
		
		
	}
	
	public static final MavenVersion findJarInDirectory(File dir, String artifactName){
		if (!dir.exists())
			throw new IllegalArgumentException("Directory "+dir.getAbsolutePath()+" doesn't exists.");
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Directory "+dir.getAbsolutePath()+" is not a directory.");
		File files[] = dir.listFiles();
		for (int i=0; i<files.length; i++){
			if (files[i].getName().startsWith(artifactName)){
				return readVersionFromJar(files[i]);
			}
		}
		return null;
		
		
	}

	public static void main(String[] args) {

		//System.out.println(readVersionFromJar(new File("/Users/another/.m2/repository/net/anotheria/moskito-webui/2.6.3/moskito-webui-2.6.3.jar")));
		System.out.println(readVersionFromJar(new File("/Users/another/.m2/repository/net/anotheria/moskito-webui/2.7.0/moskito-webui-2.7.0.jar")));
		System.out.println(readVersionFromJar(new File("/opt/small_tomcat/webapps/moskito/WEB-INF/lib/moskito-webui-2.7.1-SNAPSHOT.jar")));
/*		System.out.println(readVersionFromFile(new File("/opt/small-tomcat/webapps/moskitodemo/META-INF/maven/net.anotheria/moskitodemo/pom.properties")));
		System.out.println(readVersionFromJar(new File("/opt/small-tomcat/webapps/moskitodemo/WEB-INF/lib/ano-prise-1.0.0.jar")));
		System.out.println(findVersionInDirectory(new File("/opt/small-tomcat/webapps/moskitodemo/META-INF")));
		System.out.println(findJarInDirectory(new File("/opt/small-tomcat/webapps/moskitodemo/WEB-INF/lib"), "moskito-webui"));*/
			
	}
}
