/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/IntVector.java,v $
  $Author: lro $
  $Date: 2004/02/17 19:30:39 $
  $Revision: 1.2 $


  Copyright 2003 by Anotheria.net, Berlin, Germany
  All rights reserved.

  This software is the confidential and proprietary information
  of BeagleSoft GmbH. ("Confidential Information").  You
  shall not disclose such Confidential Information and shall use
  it only in accordance with the terms of the license agreement
  you entered into with BeagleSoft GmbH.
  See www.anotheria.net for details.
** ------------------------------------------------------------------------- */
package net.anotheria.util;

import java.util.*;
import java.io.Serializable;

/**
 * The <code>IntVector</code> class implements a growable array of
 * integers. It´s source code is actually copied from Sun´sIntVectorclass,
 * but works with "int" instead of "Object"
 */

public class IntVector implements Cloneable, Serializable{
    /**
     * The array buffer into which the components of the vector are
     * stored. The capacity of the vector is the length of this array buffer.
     *
	 *
     */
    protected int[] elementData;
    /**
     * The number of valid components in the vector.
     *
	 *
     */
    protected int elementCount;
    /**
     * The amount by which the capacity of the vector is automatically
     * incremented when its size becomes greater than its capacity. If
     * the capacity increment is <code>0</code>, the capacity of the
     * vector is doubled each time it needs to grow.
     *
	 *
     */
    protected int capacityIncrement;

    /** use serialVersionUID from JDK 1.0.2 for interoperability */
    private static final long serialVersionUID = -2767605614048989439L;

    /**
     * Constructs an empty vector with the specified initial capacity and
     * capacity increment.
     *
     * @param   initialCapacity     the initial capacity of the vector.
     * @param   capacityIncrement   the amount by which the capacity is
     *                              increased when the vector overflows.
	 *
     */
    public IntVector (int initialCapacity, int capacityIncrement) {
      super();
      this.elementData = new int[initialCapacity];
      this.capacityIncrement = capacityIncrement;
    }

    /**
     * Constructs an empty vector with the specified initial capacity.
     *
     * @param   initialCapacity   the initial capacity of the vector.
	 *
     */
    public IntVector (int initialCapacity) {
      this(initialCapacity, 0);
    }

	public IntVector(int[] data){
	    if(data == null)
			throw new RuntimeException("Couldn't create Vector from NULL object");
		this.elementData  = data;
		this.elementCount = data.length;
	}

    /**
     * Constructs an empty vector.
     *
	 *
     */
    public IntVector () {
      this(10);
    }

    /**
     * Copies the components of this vector into the specified array.
     * The array must be big enough to hold all the objects in this  vector.
     *
     * @param   anArray   the array into which the components get copied.
	 *
     */
    public final synchronized void copyInto (int[] anArray) {
      int i = elementCount;
      while (i-- > 0) {
        anArray[i] = elementData[i];
      }
    }

    /**
     * Trims the capacity of this vector to be the vector's current
     * size. An application can use this operation to minimize the
     * storage of a vector.
     *
	 *
     */
    public final synchronized void trimToSize () {
      int oldCapacity = elementData.length;
      if (elementCount < oldCapacity) {
        int oldData[] = elementData;
        elementData = new int[elementCount];
        System.arraycopy(oldData, 0, elementData, 0, elementCount);
      }
    }

    /**
     * Increases the capacity of this vector, if necessary, to ensure
     * that it can hold at least the number of components specified by
     * the minimum capacity argument.
     *
     * @param   minCapacity   the desired minimum capacity.
	 *
     */
    public final synchronized void ensureCapacity (int minCapacity) {
      if (minCapacity > elementData.length) {
        ensureCapacityHelper(minCapacity);
      }
    }

    /**
     * This implements the unsynchronized semantics of ensureCapacity.
     * Synchronized methods in this class can internally call this
     * method for ensuring capacity without incurring the cost of an
     * extra synchronization.
     *
     * @see com.eqs.fi.shared.common.util.IntVector#ensureCapacity(int)
     */
    private void ensureCapacityHelper (int minCapacity) {
      int oldCapacity = elementData.length;
      int oldData[] = elementData;
      int newCapacity = (capacityIncrement > 0) ? (oldCapacity + capacityIncrement) :
          (oldCapacity*2);
      if (newCapacity < minCapacity) {
        newCapacity = minCapacity;
      }
      elementData = new int[newCapacity];
      System.arraycopy(oldData, 0, elementData, 0, elementCount);
    }

    /**
     * Sets the size of this vector. If the new size is greater than the
     * current size, new <code>null</code> items are added to the end of
     * the vector. If the new size is less than the current size, all
     * components at index <code>newSize</code> and greater are discarded.
     *
     * @param   newSize   the new size of this vector.
	 *
     */
    public final synchronized void setSize (int newSize) {
      if ((newSize > elementCount) && (newSize > elementData.length)) {
        ensureCapacityHelper(newSize);
      }
      else {
        for (int i = newSize; i < elementCount; i++) {
          elementData[i] = 0;
        }
      }
      elementCount = newSize;
    }

    /**
     * Returns the current capacity of this vector.
     *
     * @return  the current capacity of this vector.
	 *
     */
    public final int capacity () {
      return  elementData.length;
    }

    /**
     * Returns the number of components in this vector.
     *
     * @return  the number of components in this vector.
	 *
     */
    public final int size () {
      return  elementCount;
    }

    /**
     * Tests if this vector has no components.
     *
     * @return  <code>true</code> if this vector has no components;
     *          <code>false</code> otherwise.
	 *
     */
    public final boolean isEmpty () {
      return  elementCount == 0;
    }

    /**
     * Returns an enumeration of the components of this vector.
     *
     * @return  an enumeration of the components of this vector.
     * @see     <{Enumeration}>
	 *
     */
    public final synchronized Enumeration elements () {
      return  new VectorEnumerator(this);
    }

    /**
     * Tests if the specified object is a component in this vector.
     *
     * @param   elem   an object.
     * @return  <code>true</code> if the specified object is a component in
     *          this vector; <code>false</code> otherwise.
	 *
     */
    public final boolean contains (int elem) {
      return  indexOf(elem, 0) >= 0;
    }

    /**
     * Searches for the first occurence of the given argument, testing
     * for equality using the <code>equals</code> method.
     *
     * @param   elem   an object.
     * @return  the index of the first occurrence of the argument in this
     *          vector; returns <code>-1</code> if the object is not found.
     * @see     java.lang.Object#equals(java.lang.Object)
	 *
     */
    public final int indexOf (int elem) {
      return  indexOf(elem, 0);
    }

    /**
     * Searches for the first occurence of the given argument, beginning
     * the search at <code>index</code>, and testing for equality using
     * the <code>equals</code> method.
     *
     * @param   elem    an object.
     * @param   index   the index to start searching from.
     * @return  the index of the first occurrence of the object argument in
     *          this vector at position <code>index</code> or later in the
     *          vector; returns <code>-1</code> if the object is not found.
     * @see     java.lang.Object#equals(java.lang.Object)
	 *
     */
    public final synchronized int indexOf (int elem, int index) {
      for (int i = index; i < elementCount; i++) {
        if (elem==elementData[i]) {
          return  i;
        }
      }
      return  -1;
    }

    /**
     * Returns the index of the last occurrence of the specified object in
     * this vector.
     *
     * @param   elem   the desired component.
     * @return  the index of the last occurrence of the specified object in
     *          this vector; returns <code>-1</code> if the object is not found.
	 *
     */
    public final int lastIndexOf (int elem) {
      return  lastIndexOf(elem, elementCount - 1);
    }

    /**
     * Searches backwards for the specified object, starting from the
     * specified index, and returns an index to it.
     *
     * @param   elem    the desired component.
     * @param   index   the index to start searching from.
     * @return  the index of the last occurrence of the specified object in this
     *          vector at position less than <code>index</code> in the vector;
     *          <code>-1</code> if the object is not found.
	 *
     */
    public final synchronized int lastIndexOf (int elem, int index) {
      for (int i = index; i >= 0; i--) {
        if (elem==elementData[i]) {
          return  i;
        }
      }
      return  -1;
    }

    /**
     * Returns the component at the specified index.
     *
     * @param      index   an index into this vector.
     * @return     the component at the specified index.
     * @exception  ArrayIndexOutOfBoundsException  if an invalid index was
     *               given.
     */
    public final synchronized int elementAt (int index) {
      if (index >= elementCount) {
        throw  new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
      }
      /* Since try/catch is free, except when the exception is thrown,
       put in this extra try/catch to catch negative indexes and
       display a more informative error message.  This might not
       be appropriate, especially if we have a decent debugging
       environment - JP. */
      try {
        return  elementData[index];
      } catch (ArrayIndexOutOfBoundsException e) {
        throw  new ArrayIndexOutOfBoundsException(index + " < 0");
      }
    }

    /**
     * Returns the first component of this vector.
     *
     * @return     the first component of this vector.
     * @exception  NoSuchElementException  if this vector has no components.
     */
    public final synchronized int firstElement () {
      if (elementCount == 0) {
        throw  new NoSuchElementException();
      }
      return  elementData[0];
    }

    /**
     * Returns the last component of the vector.
     *
     * @return  the last component of the vector, i.e., the component at index
     *          <code>size()&nbsp;-&nbsp;1</code>.
     * @exception  NoSuchElementException  if this vector is empty.
*
     */
    public final synchronized int lastElement () {
      if (elementCount == 0) {
        throw  new NoSuchElementException();
      }
      return  elementData[elementCount - 1];
    }

    /**
     * Sets the component at the specified <code>index</code> of this
     * vector to be the specified object. The previous component at that
     * position is discarded.
     * <p>
     * The index must be a value greater than or equal to <code>0</code>
     * and less than the current size of the vector.
     *
     * @param      obj     what the component is to be set to.
     * @param      index   the specified index.
     * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
     * @see        com.eqs.fi.shared.common.util.IntVector#size()
     */
    public final synchronized void setElementAt (int obj, int index) {
      if (index >= elementCount) {
        throw  new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
      }
      elementData[index] = obj;
    }

    /**
     * Deletes the component at the specified index. Each component in
     * this vector with an index greater or equal to the specified
     * <code>index</code> is shifted downward to have an index one
     * smaller than the value it had previously.
     * <p>
     * The index must be a value greater than or equal to <code>0</code>
     * and less than the current size of the vector.
     *
     * @param      index   the index of the object to remove.
     * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
     * @see        com.eqs.fi.shared.common.util.IntVector#size()
     */
    public final synchronized void removeElementAt (int index) {
      if (index >= elementCount) {
        throw  new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
      }
      else if (index < 0) {
        throw  new ArrayIndexOutOfBoundsException(index);
      }
      int j = elementCount - index - 1;
      if (j > 0) {
        System.arraycopy(elementData, index + 1, elementData, index, j);
      }
      elementCount--;
      elementData[elementCount] = 0;
      /* to let gc do its work */

    }

    /**
     * Inserts the specified object as a component in this vector at the
     * specified <code>index</code>. Each component in this vector with
     * an index greater or equal to the specified <code>index</code> is
     * shifted upward to have an index one greater than the value it had
     * previously.
     * <p>
     * The index must be a value greater than or equal to <code>0</code>
     * and less than or equal to the current size of the vector.
     *
     * @param      obj     the component to insert.
     * @param      index   where to insert the new component.
     * @exception  ArrayIndexOutOfBoundsException  if the index was invalid.
     * @see        com.eqs.fi.shared.common.util.IntVector#size()
     */
    public final synchronized void insertElementAt (int elem, int index) {
      int newcount = elementCount + 1;
      if (index >= newcount) {
        throw  new ArrayIndexOutOfBoundsException(index + " > " + elementCount);
      }
      if (newcount > elementData.length) {
        ensureCapacityHelper(newcount);
      }
      System.arraycopy(elementData, index, elementData, index + 1, elementCount
          - index);
      elementData[index] = elem;
      elementCount++;
    }

    /**
     * Adds the specified component to the end of this vector,
     * increasing its size by one. The capacity of this vector is
     * increased if its size becomes greater than its capacity.
     *
     * @param   obj   the component to be added.
	 *
     */
    public final synchronized void addElement (int obj) {
      int newcount = elementCount + 1;
      if (newcount > elementData.length) {
        ensureCapacityHelper(newcount);
      }
      elementData[elementCount++] = obj;
    }

    /**
     * Removes the first occurrence of the argument from this vector. If
     * the object is found in this vector, each component in the vector
     * with an index greater or equal to the object's index is shifted
     * downward to have an index one smaller than the value it had previously.
     *
     * @param   obj   the component to be removed.
     * @return  <code>true</code> if the argument was a component of this
     *          vector; <code>false</code> otherwise.
	 *
     */
    public final synchronized boolean removeElement (int elem) {
      int i = indexOf(elem);
      if (i >= 0) {
        removeElementAt(i);
        return  true;
      }
      return  false;
    }

    /**
     * Removes all components from this vector and sets its size to zero.
     *
	 *
     */
    public final synchronized void removeAllElements () {
      for (int i = 0; i < elementCount; i++) {
        elementData[i] = 0;
      }
      elementCount = 0;
    }

    /**
     * Returns a clone of this vector.
     *
     * @return  a clone of this vector.
	 *
     */
    public synchronized Object clone () {
      try {
        IntVector v = (IntVector)super.clone();
        v.elementData = new int[elementCount];
        System.arraycopy(elementData, 0, v.elementData, 0, elementCount);
        return  v;
      } catch (CloneNotSupportedException e) {
        // this shouldn't happen, since we are Cloneable
        throw  new InternalError();
      }
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return  a string representation of this vector.
	 *
     */
    public final synchronized String toString () {
      int max = size() - 1;
      StringBuffer buf = new StringBuffer();
      Enumeration e = elements();
      buf.append("[");
      for (int i = 0; i <= max; i++) {
        String s = e.nextElement().toString();
        buf.append(s);
        if (i < max) {
          buf.append(", ");
        }
      }
      buf.append("]");
      return  buf.toString();
    }


  class VectorEnumerator
      implements Enumeration {
    IntVector vector;
    int count;

    /**
     * @param    IntVector v
     */
    VectorEnumerator (IntVector v) {
      vector = v;
      count = 0;
    }

    /**
     * @return
     */
    public boolean hasMoreElements () {
      return  count < vector.elementCount;
    }

    /**
     * @return
     */
    public Object nextElement () {
      synchronized (vector) {
        if (count < vector.elementCount) {
          return  new Integer(vector.elementData[count++]);
        }
      }
      throw  new NoSuchElementException("VectorEnumerator");
    }
    }
  /* ------------------------------------------------------------------------- *
   $Log: IntVector.java,v $
   Revision 1.2  2004/02/17 19:30:39  lro
   *** empty log message ***

   Revision 1.1  2004/02/06 21:41:49  lro
   *** empty log message ***

   Revision 1.1.1.1  2004/02/04 16:31:12  lro
   initial checkin

   Revision 1.1  2004/01/30 22:06:41  cvs
   *** empty log message ***

   Revision 1.1.1.1  2002/02/05 16:26:21  another
   no message

   Revision 1.1.1.1  2001/10/22 10:34:21  lro
   no message

   Revision 1.1.1.1  2001/09/13 14:17:28  cho
   no message

   Revision 1.1  2001/07/25 13:37:36  hwa
   moved here from fi-package

   Revision 1.11  2001/07/09 15:24:38  lro
   VectorEnumerator is inner class now

   Revision 1.10  2001/07/07 12:59:09  kle
   CVS Comments Added

   Revision 1.9  2001/07/05 13:04:42  kle
   no message

   Revision 1.8  2001/05/25 10:14:30  kle
   no message

   Revision 1.7  2001/05/11 09:01:24  kle
   no message

   Revision 1.6  2001/05/11 08:52:05  kle
    * @since   JDK1.0 deleted

   Revision 1.5  2001/05/10 18:23:54  lro
   fixed very bad BUGS!!!

   Revision 1.4  2001/05/10 08:55:16  hwa
   changed some comments

   Revision 1.2  2001/05/09 16:51:33  hwa
   added headers and footers for CVS
   ** ------------------------------------------------------------------------- */

}


