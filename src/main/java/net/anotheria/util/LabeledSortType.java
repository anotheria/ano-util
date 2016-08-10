package net.anotheria.util;

import net.anotheria.util.sorter.SortType;

/**
 * <p>Abstract LabeledSortType class.</p>
 *
 * @author another
 * @version $Id: $Id
 */
public abstract class LabeledSortType extends SortType{
	/**
	 * <p>Constructor for LabeledSortType.</p>
	 *
	 * @param aMethod a int.
	 */
	public LabeledSortType(int aMethod){
		super(aMethod);
	}
	
	/**
	 * <p>Constructor for LabeledSortType.</p>
	 *
	 * @param aMethod a int.
	 * @param order a boolean.
	 */
	public LabeledSortType(int aMethod, boolean order){
		super(aMethod, order);
	}

	/**
	 * <p>getDescriptions.</p>
	 *
	 * @return an array of {@link java.lang.String} objects.
	 */
	public abstract String[] getDescriptions();
	
}
