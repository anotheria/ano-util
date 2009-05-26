package net.anotheria.util;

import  java.util.Vector;


public class MathHelper {

    /**
     * put your documentation comment here
     * @param values Double Vector
     * @param precision 1 - Integer.MaxValue
     * @return  rounded Double Vector with precision
     */
    public static Vector<String> getFormattedStringVector(Vector<Double> values, int precision) {
        Vector<String> result = new Vector<String>();
        for (int i = 0; i < values.size(); i++) {
            result.addElement(getFormattedString(values.elementAt(i), precision));
        }
        return  result;
    }

    /**
     * put your documentation comment here
     * @param value
     * @param precision
     * @return
     */
    public static String getFormattedString(Double value, int precision) {
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
            } else {
                for(int i=start.length() ; i <= zeros + indexOfPoint; i++) {
                    start = start + "0";
                }
                start = start + "$";
            }
            start = start.substring(0,start.indexOf('.')) + start.substring(start.indexOf('.')+1);
            if(start.indexOf('$')==0) {
                start = "0." + start.substring(1);
            } else {
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

        if(precision > 0) {
            resultString = left + resultString +  "," + right;
        } else {
            if(Integer.parseInt(String.valueOf(right.charAt(0))) < 5) {
                resultString = left + resultString  ;
            } else {
                resultString = resultString + (Integer.parseInt(left) + 1);
            }
        }

        if(negativ) {
            return "-" + resultString;
        }

        return resultString;
    }
}



