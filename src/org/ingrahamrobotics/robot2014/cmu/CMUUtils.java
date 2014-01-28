/*
 * Copyright (C) 2014 Ingraham Robotics Team 4030
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ingrahamrobotics.robot2014.cmu;

import java.io.UnsupportedEncodingException;
import java.util.Vector;
import org.ingrahamrobotics.robot2014.util.LinkedList;
import org.ingrahamrobotics.robot2014.util.ListIterator;

public class CMUUtils {

    public static final String CHARSET = "ASCII";

    public static byte[] toBytes(String str) {
        try {
            return str.getBytes(CHARSET);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return new byte[0];
        }
    }

    public static String toString(byte[] bytes) {
        try {
            return new String(bytes, CHARSET);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static int average(LinkedList ints) {
        int sum = 0;
        for (ListIterator i = ints.listIterator(0); i.hasNext();) {
            sum += ((Integer) i.next()).intValue();
        }
        return sum / ints.size();
    }

    /**
     * Returns the array of substrings obtained by dividing the given input
     * string at each occurrence of the given delimiter.
     *
     * @param input The input string
     * @param delimiter The thing to split by
     * @return the split
     */ 
    public static String[] split(String input, String delimiter) {
        Vector node = new Vector();
        int index = input.indexOf(delimiter);
        while (index >= 0) {
            node.addElement(input.substring(0, index));
            input = input.substring(index + delimiter.length());
            index = input.indexOf(delimiter);
        }
        node.addElement(input);

        String[] retString = new String[node.size()];
        for (int i = 0; i < node.size(); ++i) {
            retString[i] = (String) node.elementAt(i);
        }

        return retString;
    }

    /**
     * Copies the specified array, truncating or padding with zeros (if
     * necessary) so the copy has the specified length. For all indices that are
     * valid in both the original array and the copy, the two arrays will
     * contain identical values. For any indices that are valid in the copy but
     * not the original, the copy will contain <tt>(byte)0</tt>. Such indices
     * will exist if and only if the specified length is greater than that of
     * the original array.
     *
     * @param original the array to be copied
     * @param newLength the length of the copy to be returned
     * @return a copy of the original array, truncated or padded with zeros to
     * obtain the specified length
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     * @since 1.6
     */
    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0,
                Math.min(original.length, newLength));
        return copy;
    }
}
