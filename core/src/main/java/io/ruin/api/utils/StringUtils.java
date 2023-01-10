package io.ruin.api.utils;

import io.ruin.api.buffer.InBuffer;

public class StringUtils {

    private static final char[] VALID_CHARS = {'_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public static boolean vowelStart(String word) {
        if (word.isEmpty())
            return false;
        char c = Character.toLowerCase(word.charAt(0));
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }

    public static String withArticle(String word) {
        return (vowelStart(word) ? "an" : "a") + " " + word;
    }

    public static String capitalizeFirst(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    public static String fixCaps(String message) {
        char[] chars = message.toCharArray();
        boolean allowCap = true, forceCap = true;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (forceCap) {
                if (c != ' ') {
                    forceCap = false;
                    c = chars[i] = Character.toUpperCase(c);
                }
            } else if (!allowCap)
                c = chars[i] = Character.toLowerCase(c);

            if (c == '.' || c == '!' || c == '?') {
                forceCap = true;
            } else {
                allowCap = !Character.isLetter(c);//(c == ' ');
            }
        }
        return new String(chars);
    }

    public static long stringToLong(String string) {
        long l = 0L;
        int i_0_ = string.length();
        int i_1_ = 0;
        for (/**/; (~i_0_) < (~i_1_); i_1_++) {
            l *= 37L;
            int i_2_ = string.charAt(i_1_);
            if ((~i_2_) <= -66 && (~i_2_) >= -91)
                l += (long) (-64 + i_2_);
            else if (i_2_ >= 97 && i_2_ <= 122)
                l += (long) (-96 + i_2_);
            else if (i_2_ >= 48 && i_2_ <= 57)
                l += (long) (27 - (-i_2_ + 48));
            if ((~l) <= -177917621779460414L)
                break;
        }
        for (/**/; ((~l % 37L) == -1L && (~l) != -1L); l /= 37L) {
            /* empty */
        }
        return l;
    }

    public static String longToString(long l) {
        if (l <= 0L || l >= 0x5b5b57f8a98a5dd1L)
            return null;
        if (l % 37L == 0L)
            return null;
        int i = 0;
        char ac[] = new char[12];
        while (l != 0L) {
            long l1 = l;
            l /= 37L;
            ac[11 - i++] = VALID_CHARS[(int) (l1 - l * 37L)];
        }
        return new String(ac, 12 - i, i);
    }

    public static String getFormattedEnumName(Enum<?> e) {
        return fixCaps(e.name().replace('_', ' '));
    }

    public static String getFormattedEnumName(String name) {
        return fixCaps(name.replace('_', ' '));
    }


    /**
     * The modified set of 'extended ASCII' characters used by the client.
     */
    private static char CHARACTERS[] = {'\u20AC', '\0', '\u201A', '\u0192', '\u201E', '\u2026', '\u2020', '\u2021',
            '\u02C6', '\u2030', '\u0160', '\u2039', '\u0152', '\0', '\u017D', '\0', '\0', '\u2018', '\u2019', '\u201C',
            '\u201D', '\u2022', '\u2013', '\u2014', '\u02DC', '\u2122', '\u0161', '\u203A', '\u0153', '\0', '\u017E',
            '\u0178'};

    /**
     * Gets a char from the specified buffer, using a modified
     * ISO-8859-1 character set.
     *
     * @param buf The buffer.
     * @return The decoded string.
     */
    public static char getJagexChar(InBuffer buf) {
        StringBuilder bldr = new StringBuilder();
        int b = buf.readUnsignedByte() & 0xFF;//position?
        if (b >= 127 && b < 160) {
            char curChar = CHARACTERS[b - 128];
            if (curChar == 0) {
                curChar = 63;
            }

            b = curChar;
        }
        return (char) b;
    }

}
