package net.anotheria.util.sorter;

public class TestSortType extends SortType{
	public static final int SORT_BY_STRING = 1;
	public static final int SORT_BY_INTEGER = 2;
	public static final int SORT_BY_DOUBLE  = 3;
	public static final int UNSUPPORTED_SORT_TYPE = 100;
	
	public TestSortType(int aSortType){
		super(aSortType);
	}

	public TestSortType(int aSortType, boolean order){
		super(aSortType, order);
	}
}
