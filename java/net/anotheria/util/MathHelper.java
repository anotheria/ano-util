/* ------------------------------------------------------------------------- *
  $Source: /work/cvs/ano-util/java/net/anotheria/util/MathHelper.java,v $
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
package net.anotheria.util;

import  java.util.Vector;


public class MathHelper {

    /**
     * put your documentation comment here
     * @param values Double Vector
     * @param precision 1 - Integer.MaxValue
     * @return  rounded Double Vector with precision
     */
    public static Vector getFormattedStringVector (Vector values, int precision) {
        Vector result = new Vector();
        for (int i = 0; i < values.size(); i++) {
            result.addElement(getFormattedString((Double)values.elementAt(i), precision));
        }
        return  result;
    }

    /**
     * put your documentation comment here
     * @param value
     * @param precision
     * @return
     */
    public static String getFormattedString (Double value, int precision) {
        boolean negativ = false;
        String start = value.toString();

        if(value.doubleValue() < 0){
            start = (-1 * value.doubleValue()) + "";
            negativ = true;
        }

        String resultString = "";
        String right;
        String left;
        int zeros;
        int indexE = start.indexOf('E');

        if(indexE!=-1){
            zeros = new Integer(start.substring(indexE+1)).intValue();
            if(start.charAt(indexE - 1)=='-'){
                zeros *= -1;
            }
            start = start.substring(0,indexE);
            int indexOfPoint = start.indexOf('.');
            if(zeros < 0){
                zeros = -zeros;
                for(int i= 0; i < zeros - indexOfPoint;i++){
                    start =  "0" + start;
                }
                start = "$" + start;
            }
            else{
                for(int i=start.length() ; i <= zeros + indexOfPoint; i++){
                    start = start + "0";
                }
                start = start + "$";
            }
            start = start.substring(0,start.indexOf('.')) + start.substring(start.indexOf('.')+1);
            if(start.indexOf('$')==0){
                start = "0." + start.substring(1);
            }
            else{
                start = start.substring(0,start.length()-1) + ".0";
            }
        }


        left = start.substring(0,start.indexOf('.'));
        right = start.substring(start.indexOf('.')+1);

        while (left.length() > 3) {

            resultString = "." + left.substring(left.length()-3) + resultString;
            left = left.substring(0,left.length()-3);
        }
        while (right.length() < precision) {
            right = right + "0";
        }

        if (right.length() > precision && precision > 0){
            int round = (int)(0.5 + Double.parseDouble("0." + String.valueOf(right.charAt(precision))) +  Integer.parseInt(String.valueOf(right.charAt(precision-1))));
            right = right.substring(0,precision-1) + round;
        }

        if(precision > 0){
            resultString = left + resultString +  "," + right;
        }
        else{
            if(Integer.parseInt(String.valueOf(right.charAt(0))) < 5){
                resultString = left + resultString  ;
            }
            else{
                resultString = resultString + (Integer.parseInt(left) + 1);
            }
        }

        if(negativ){
            return "-" + resultString;
        }

        return resultString;
    }




    /*
     * -------------------------------------------------------------------------
     * $Log: MathHelper.java,v $
     * Revision 1.1  2004/02/06 21:41:49  lro
     * *** empty log message ***
     *
     * Revision 1.1.1.1  2004/02/04 16:31:13  lro
     * initial checkin
     *
     * Revision 1.1  2004/01/30 22:06:41  cvs
     * *** empty log message ***
     *
     * Revision 1.1.1.1  2002/02/05 16:26:21  another
     * no message
     *
     * Revision 1.1.1.1  2001/10/22 10:34:22  lro
     * no message
     *
     * Revision 1.1.1.1  2001/09/13 14:17:28  cho
     * no message
     *
     * Revision 1.8  2001/08/13 10:52:21  eku
     * no message
     *
     * Revision 1.7  2001/08/12 19:28:06  kle
     * no message
     *
     * Revision 1.6  2001/08/09 09:26:21  eku
     * no message
     *
     * Revision 1.5  2001/08/07 12:38:06  eku
     * no message
     *
     * Revision 1.4  2001/08/06 12:46:26  eku
     * problem with thousand points fixed
     *
     * Revision 1.3  2001/07/26 17:05:28  eku
     * no message
     *
     * Revision 1.2  2001/07/25 14:43:41  eku
     * no message
     *
     * Revision 1.3  2001/07/25 13:28:33  hwa
     * restructered packages fi.shared and fi.admin
     *
     * Revision 1.2  2001/07/17 14:12:30  hwa
     * no message
     *
     * -------------------------------------------------------------------------
     */


}



