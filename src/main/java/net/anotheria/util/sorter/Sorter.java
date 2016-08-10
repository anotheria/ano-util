package net.anotheria.util.sorter;

import java.util.Enumeration;
import java.util.List;


/**
 * A Sorter interface.
 *
 * @author another
 * @version $Id: $Id
 */
public interface Sorter<T extends IComparable> {
    /**
     * Sorts the given List of IFIComparables after given method and order.
     *
     * @param source a {@link java.util.List} object.
     * @param how a {@link net.anotheria.util.sorter.SortType} object.
     * @return a {@link java.util.List} object.
     */
    List<T> sort(List<T> source, SortType how);

 	/**
 	 * Sorts the given Enumeration of IFIComparables after given method and order.
 	 *
 	 * @deprecated Use {@link #sort(List, SortType)} instead
 	 * @param source a {@link java.util.Enumeration} object.
 	 * @param how a {@link net.anotheria.util.sorter.SortType} object.
 	 * @return a {@link java.util.List} object.
 	 */
 	@Deprecated
    List<T> sort(Enumeration<T> source, SortType how);

}
