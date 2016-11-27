package net.anotheria.util;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class for double objects formatting.
 *
 * @author another
 * @version $Id: $Id
 */
public class MathHelper {

    /**
     * <p>getFormattedStringVector.</p>
     *
     * @param values Double Vector
     * @param precision 1 - Integer.MaxValue
     * @return  rounded Double Vector with precision
     * @deprecated use {@link #getFormattedStrings(Iterable, int)} instead
     */
    @Deprecated
    public static List<String> getFormattedStringVector(Iterable<Double> values, int precision) {
        return  getFormattedStrings(values, precision);
    }

    /**
     * <p>getFormattedStrings.</p>
     *
     * @param values a {@link java.lang.Iterable} object.
     * @param precision a int.
     * @return a {@link java.util.List} object.
     */
    public static List<String> getFormattedStrings(Iterable<Double> values, int precision) {
        List<String> result = new ArrayList<>();
        for (Double value : values) {
            result.add(getFormattedString(value, precision));
        }
        return  result;
    }

    /**
     * put your documentation comment here
     *
     * @param value a {@link java.lang.Double} object.
     * @param precision a int.
     * @return a {@link java.lang.String} object.
     */
    public static String getFormattedString(Double value, int precision) {
        boolean negativ = false;
        String start = value.toString();

        if(value < 0){
            start = String.valueOf(-1 * value);
            negativ = true;
        }

        int indexE = start.indexOf('E');

        if(indexE!=-1){
            int zeros = Integer.parseInt(start.substring(indexE + 1));
            if(start.charAt(indexE - 1)=='-'){
                zeros *= -1;
            }
            start = start.substring(0,indexE);
            int indexOfPoint = start.indexOf('.');
            if(zeros < 0){
                zeros = -zeros;
                for(int i= 0; i < zeros - indexOfPoint;i++){
                    start = '0' + start;
                }
                start = '$' + start;
            } else {
                for(int i=start.length() ; i <= zeros + indexOfPoint; i++) {
                    start = start + '0';
                }
                start = start + '$';
            }
            start = start.substring(0,start.indexOf('.')) + start.substring(start.indexOf('.')+1);
            if(start.indexOf('$')==0) {
                start = "0." + start.substring(1);
            } else {
                start = start.substring(0,start.length()-1) + ".0";
            }
        }


        String left = start.substring(0, start.indexOf('.'));
        String right = start.substring(start.indexOf('.') + 1);

        String resultString = "";
        while (left.length() > 3) {

            resultString = '.' + left.substring(left.length()-3) + resultString;
            left = left.substring(0,left.length()-3);
        }
        while (right.length() < precision) {
            right = right + '0';
        }

        if (right.length() > precision && precision > 0){
            int round = (int)(0.5 + Double.parseDouble("0." + right.charAt(precision)) +  Integer.parseInt(String.valueOf(right.charAt(precision-1))));
            right = right.substring(0,precision-1) + round;
        }

        if(precision > 0) {
            resultString = left + resultString + ',' + right;
        } else {
            if(Integer.parseInt(String.valueOf(right.charAt(0))) < 5) {
                resultString = left + resultString  ;
            } else {
                resultString = resultString + (Integer.parseInt(left) + 1);
            }
        }

        if(negativ) {
            return '-' + resultString;
        }

        return resultString;
    }
}



