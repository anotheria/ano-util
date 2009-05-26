package net.anotheria.util.sorter;

import java.io.Serializable;

/**
 * SortType is the instruction for the sorter how to handle the incoming data.
 * It consist of a sortAfter parameter, which is passed to the IFIComparable,
 * and the sortOrder parameter, which instructs the Sorter if he must sort in ascending or
 * descending order.
 * @see com.eqs.fi.shared.common.data.IFIComparable.html
 * @see com.eqs.fi.shared.common.util.Sorter.html
 */
public class SortType implements Serializable{

	private static final long serialVersionUID = 2536090774100419017L;
	
	/**
  	 *Constant for ascending sort order.
  	 */
	public static final boolean ASC  = true;
	/**
  	 *Constant for descending sort order.
  	 */
 	public static final boolean DESC = false;

  	/**
     * The method which is used by the comparison of single objects.
     * @see com.eqs.fi.shared.common.data.IFICompareable.html#compareTo
     */
	private int sortBy;

 	/**
     * Order which is used by the sorter, acsending or descending.
     */
    private boolean sortOrder;

    /**
     * Creates a new SortType with given SortAfter method and order.
     */
    public SortType(int aSortBy, boolean aSortOrder){
    	this.sortBy = aSortBy;
     	this.sortOrder = aSortOrder;
    }

    /**
     * Creates a new SortType with given sortAfter method and ascending sort order.
     */
    public SortType(int aSortAfter){
    	this(aSortAfter, ASC);
    }

    /**
     * Returns the sortAfter parameter.
     */
	public int getSortBy(){
 		return sortBy;
  	}

   	/**
     * Returns true if the ascending sort order is selected.
     */
   	public boolean isASC(){
    	return sortOrder==ASC;
    }

    /**
     * Returns true if the descending sort order is selected.
     */
   	public boolean isDESC(){
    	return sortOrder==DESC;
    }

    /**
     * Returns the sort order.
     */
    public boolean getSortOrder(){
    	return sortOrder;
    }
    
    public SortType reverse(){
    	return new SortType(getSortBy(), !getSortOrder());
    }

    /**
     * Returns the string representation of the object.
     */
    public String toString(){
		return "sortBy: "+sortBy+" sortOder: "+(sortOrder==ASC?"ASC":"DESC");
    }
}
