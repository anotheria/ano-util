package net.anotheria.util.sorter;

import java.util.Enumeration;
import java.util.List;


/**
 * A Sorter interface.
 */

public interface Sorter<T extends IComparable> {
    /**
     * Sorts the given List of IFIComparables after given method and order.
     */
    List<T> sort(List<T> source, SortType how);

    /**
     * Sorts the given Enumeration of IFIComparables after given method and order.
     * @deprecated Use {@link #sort(List, SortType)} instead
     */
 	@Deprecated
    List<T> sort(Enumeration<T> source, SortType how);

}