package io.ruin.utility;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Basic utility class for anything extra we need since the other one is in a separate dependency...
 */
public final class Utils {

    public static String getTimeFormatted(long stamp) {
        // Creating date format
        DateFormat simple = new SimpleDateFormat("dd MMM, hh:mm aa");

        // Creating date from milliseconds
        // using Date() constructor
        Date result = new Date(stamp);

        // Formatting Date according to the
        // given format
        return simple.format(result) + " CST";
    }

    public static String formatString(String toFormat) {
        if (toFormat == null)
            return "";
        toFormat = toFormat.replaceAll("_", " ");
        StringBuilder newName = new StringBuilder();
        boolean wasSpace = true;
        for (int i = 0; i < toFormat.length(); i++) {
            if (wasSpace) {
                newName.append(("" + toFormat.charAt(i)).toUpperCase());
                wasSpace = false;
            } else {
                newName.append(toFormat.charAt(i));
            }
            if (toFormat.charAt(i) == ' ') {
                wasSpace = true;
            }
        }
        return newName.toString();
    }

    public static int random(int maxValue) {
        if (maxValue <= 0) return 0;
        return new Random().nextInt(maxValue);
    }

    public static int random(int min, int max) {
        final int n = Math.abs(max - min);
        return Math.min(min, max) + (n == 0 ? 0 : random(n));
    }

    public static <T> T randomTypeOfList(List<T> list) {
        if (list == null || list.size() == 0)
            return null;
        return list.get(new SecureRandom().nextInt(list.size()));
    }

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }


    public static String formatMoneyString(int amount) {
        String rawString = String.format("%d", amount);
        int length = rawString.length();

        String result = rawString;
        if (length >= 13) {
            result = rawString.substring(0, rawString.length() - 12) + "," + rawString.substring(rawString.length() - 12, rawString.length() - 9) + "," + rawString.substring(rawString.length() - 9, rawString.length() - 6) + "," + rawString.substring(rawString.length() - 6, rawString.length() - 3) + "," + rawString.substring(rawString.length() - 3);
        } else if (length >= 10) {
            result = rawString.substring(0, rawString.length() - 9) + "," + rawString.substring(rawString.length() - 9, rawString.length() - 6) + "," + rawString.substring(rawString.length() - 6, rawString.length() - 3) + "," + rawString.substring(rawString.length() - 3);

        } else if (length >= 7) {
            result = rawString.substring(0, rawString.length() - 6) + "," + rawString.substring(rawString.length() - 6, rawString.length() - 3) + "," + rawString.substring(rawString.length() - 3);

        } else if (length >= 4) {
            result = rawString.substring(0, rawString.length() - 3) + "," + rawString.substring(rawString.length() - 3);
        }
        return result;
    }

    public static int largest(int[] arr) {
        int i;

        // Initialize maximum element
        int max = arr[0];

        // Traverse array elements from second and
        // compare every element with current max
        for (i = 1; i < arr.length; i++)
            if (arr[i] > max)
                max = arr[i];

        return max;
    }

}