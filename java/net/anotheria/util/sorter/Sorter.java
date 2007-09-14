package net.anotheria.util.sorter;

import java.util.Enumeration;
import java.util.List;


/**
 * A Sorter.
 */

public interface Sorter<T extends IComparable> {
    /**
     * Sorts the given List of IFIComparables after given method and order.
     */
	public abstract List<T> sort(List<T> source, SortType how);

    /**
     * Sorts the given Enumeration of IFIComparables after given method and order.
     */
 	public abstract List<T> sort(Enumeration<T> source, SortType how);

}