/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/sorter/IComparable.java,v $
  $Author: lro $
  $Date: 2004/02/06 21:41:49 $
  $Revision: 1.1 $


  Copyright 2002 by BeagleSoft GmbH, Berlin, Germany
  All rights reserved.

  This software is the confidential and proprietary information
  of BeagleSoft GmbH. ("Confidential Information").  You
  shall not disclose such Confidential Information and shall use
  it only in accordance with the terms of the license agreement
  you entered into with BeagleSoft GmbH.
  See www.beaglesoft.biz for details.
** ------------------------------------------------------------------------- */
package net.anotheria.util.sorter;

/**
 * This interface should be implemented by all classes which wish to be sorted
 * by the standart sorter.
 * @see com.eqs.fi.shared.common.util.Sorter.html
 */
public interface IComparable {
   public int compareTo(IComparable anotherObject, int method);
}

