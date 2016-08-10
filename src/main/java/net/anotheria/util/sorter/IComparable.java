package net.anotheria.util.sorter;

/**
 * This interface should be implemented by all classes which wish to be sorted
 * by the standart sorter.
 *
 * @see Sorter
 * @author another
 * @version $Id: $Id
 */
public interface IComparable <T>{
   /**
    * <p>compareTo.</p>
    *
    * @param anotherObject a {@link net.anotheria.util.sorter.IComparable} object.
    * @param method a int.
    * @return a int.
    */
   int compareTo(IComparable<? extends T> anotherObject, int method);
}

 
