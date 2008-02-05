package net.anotheria.util.sorter;

/**
 * This interface should be implemented by all classes which wish to be sorted
 * by the standart sorter.
 * @see com.eqs.fi.shared.common.util.Sorter.html
 */
public interface IComparable {
   public int compareTo(IComparable anotherObject, int method);
}

 