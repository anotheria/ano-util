package net.anotheria.util.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>Walker class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public class Walker {
	
	private Worker worker;
	private int touched;
	private int dirs;
	private int files;
	
	
	
	/**
	 * <p>Constructor for Walker.</p>
	 */
	public Walker(){
		touched = dirs = files = 0;
	}
	
	/**
	 * <p>Constructor for Walker.</p>
	 *
	 * @param aWorker a {@link net.anotheria.util.tools.Worker} object.
	 */
	public Walker(Worker aWorker){
		this();
        worker = aWorker;
    }
	
	/**
	 * <p>Setter for the field <code>worker</code>.</p>
	 *
	 * @param aWorker a {@link net.anotheria.util.tools.Worker} object.
	 */
	public void setWorker(Worker aWorker){
		worker = aWorker;
	}

	/**
	 * <p>start.</p>
	 *
	 * @param directories a {@link java.lang.Iterable} object.
	 */
	public void start(Iterable<String> directories){
		if (worker==null)
			throw new IllegalStateException("No worker configured!");
		for (String directory : directories)
			process(new File(directory));
		System.out.println("Scanned file "+touched+", directories: "+dirs+", files: "+files);
	}
	
	/**
	 * <p>start.</p>
	 */
	public void start(){
		Collection<String> toDo = new ArrayList<>(1);
		toDo.add(".");
		start(toDo);
	}
	
	private void process(File f){
		touched++;
		if (touched/10000*10000==touched)
			System.out.println("Scanning file "+touched+", directories: "+dirs+", files: "+files);
		if (f.isDirectory())
			processDir(f);
		else
			processFile(f);
	}
	
	private void processFile(File f){
		files++;
		worker.processFile(f);
	}
	
	private void processDir(File f){
		dirs++;
		File[] ff = f.listFiles();
		for (File file : ff)
			process(file);
	}
		
}
