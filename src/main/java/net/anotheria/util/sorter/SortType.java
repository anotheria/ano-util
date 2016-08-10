package net.anotheria.util.sorter;

import java.io.Serializable;

/**
 * SortType is the instruction for the sorter how to handle the incoming data.
 * It consist of a sortAfter parameter, which is passed to the IComparable,
 * and the sortOrder parameter, which instructs the Sorter if he must sort in ascending or
 * descending order.
 *
 * @author another
 * @version $Id: $Id
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
     */
	private int sortBy;

 	/**
     * Order which is used by the sorter, acsending or descending.
     */
    private boolean sortOrder;

    /**
     * Creates a new SortType with given sortby method and order.
     *
     * @param aSortBy a int.
     * @param aSortOrder a boolean.
     */
    public SortType(int aSortBy, boolean aSortOrder){
    	this.sortBy = aSortBy;
     	this.sortOrder = aSortOrder;
    }

    /**
     * Creates a new SortType with given aSortBy method and ascending sort order.
     *
     * @param aSortBy a int.
     */
    public SortType(int aSortBy){
    	this(aSortBy, ASC);
    }

	/**
	 * Returns the sortAfter parameter.
	 *
	 * @return a int.
	 */
	public int getSortBy(){
 		return sortBy;
  	}

   	/**
   	 * Returns true if the ascending sort order is selected.
   	 *
   	 * @return a boolean.
   	 */
   	public boolean isASC(){
    	return sortOrder==ASC;
    }

   	/**
   	 * Returns true if the descending sort order is selected.
   	 *
   	 * @return a boolean.
   	 */
   	public boolean isDESC(){
    	return sortOrder==DESC;
    }

    /**
     * Returns the sort order.
     *
     * @return a boolean.
     */
    public boolean getSortOrder(){
    	return sortOrder;
    }
    
    /**
     * Returns a sorttype which is opposite to this sorttype (switched sort order).
     *
     * @return a {@link net.anotheria.util.sorter.SortType} object.
     */
    public SortType reverse(){
        return new SortType(sortBy, !sortOrder);
    }

    /**
     * {@inheritDoc}
     *
     * Returns the string representation of the object.
     */
    @Override public String toString(){
		return "sortBy: "+sortBy+" sortOder: "+(sortOrder==ASC?"ASC":"DESC");
    }
}
