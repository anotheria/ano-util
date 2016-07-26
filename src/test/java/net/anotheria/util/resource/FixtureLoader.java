package net.anotheria.util.resource;


public class FixtureLoader implements ResourceLoader{
	
	public static final String EXISTING_RESOURCE = "existingResource";
	public static final String ORIGINAL_CONTENT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";
	public static final long ORIGINAL_LAST_UPDATE = 1000L;
	
	private static String content = null;
	private static long lastUpdateTimestamp = System.currentTimeMillis();
	
	static {
		reset();
	}

	@Override
	public String getContent(String resourceName) {
		chechExistance(resourceName);
		return content;
	}

	@Override
	public long getLastChangeTimestamp(String resourceName) {
		chechExistance(resourceName);
		return lastUpdateTimestamp;
	}

	@Override
	public boolean isAvailable(String resourceName) {
		return EXISTING_RESOURCE.equals(resourceName) && content!=null;
	}
	
	public static void setContent(String aContent){
		content = aContent;
		lastUpdateTimestamp = System.currentTimeMillis();
	}
	
	public static void reset(){
		content = ORIGINAL_CONTENT;
		lastUpdateTimestamp = ORIGINAL_LAST_UPDATE;
	}
	
	private static void chechExistance(String resourceName){
		if(!EXISTING_RESOURCE.equals(resourceName))
			throw new IllegalArgumentException("Resource with name " + resourceName + " doesn't exist!");
	}

}
