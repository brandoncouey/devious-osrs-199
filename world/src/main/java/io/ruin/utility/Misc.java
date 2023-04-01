package io.ruin.utility;

import io.ruin.cache.Color;
import io.ruin.model.entity.Entity;
import io.ruin.model.map.Position;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Misc {

    private static long lastUpdateTime = 0;
    private static long timeCorrection = 0;


    public static String stateOf(boolean b) {
        return stateOf(b, false);
    }

    public static String stateOf(boolean b, boolean color) {
        if (color) {
            return b ? Color.GREEN.wrap("Enabled") : Color.RED.wrap("Disabled");
        }
        return "Enabled";
    }

    public static String formatTime(long milis) {
        DateFormat simple = new SimpleDateFormat("dd MMM, hh:mm aa");
        return simple.format(new Date(milis)) + " CST";
    }

    public static String formatStringFormal(String str) {
        var str1 = Misc.ucFirst(str);
        str1.replace("_", " ");
        return str1;
    }

    public static String ucFirst(String str) {
        str = str.toLowerCase();
        if (str.length() > 1) {
            str = str.substring(0, 1).toUpperCase() + str.substring(1);
        } else {
            return str.toUpperCase();
        }
        return str.replaceAll("_", " ");
    }

    public static String currency(final long quantity) {
        if (quantity >= 1000 && quantity < 1_000_000) {
            return quantity / 1000 + "K";
        } else if (quantity >= 1_000_000 && quantity <= 9999999999L) {
            return quantity / 1000000 + "M";
        } else if (quantity >= 10000000000L && quantity <= 9999999999999L) {
            return quantity / 1000000000L + "B";
        } else if (quantity >= 10000000000000L && quantity <= Long.MAX_VALUE) {
            return quantity / 10000000000000L + "T";
        }

        return String.valueOf(quantity);
    }

    public static String currency2(final long quantity) {
        if (quantity >= 100000 && quantity < 9999999999L) {
            return quantity / 1000000 + "M";
        }

        return String.valueOf(quantity);
    }


    public static synchronized long currentTimeCorrectedMillis() {
        long current = System.currentTimeMillis();
        if (current < lastUpdateTime)
            timeCorrection += lastUpdateTime - current;
        lastUpdateTime = current;
        return current + timeCorrection;
    }

    public static String capitalize(String s) {
        if (s == null)
            return "";
        for (int i = 0; i < s.length(); i++) {
            if (i == 0) {
                s = String.format("%s%s", Character.toUpperCase(s.charAt(0)), s.substring(1));
            }
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                if (i + 1 < s.length()) {
                    s = String.format("%s%s%s", s.subSequence(0, i + 1), Character.toUpperCase(s.charAt(i + 1)), s.substring(i + 2));
                }
            }
        }
        return s;
    }

    public static int getMinutesPassed(long t) {
        int seconds = (int) ((t / 1000) % 60);
        int minutes = (int) (((t - seconds) / 1000) / 60);
        return minutes;
    }

    public static String insertCommasToNumber(String number) {
        return number.length() < 4 ? number : insertCommasToNumber(number
                .substring(0, number.length() - 3))
                + ","
                + number.substring(number.length() - 3);
    }

    public static int random(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static double randomDouble(double min, double max) {
        return (Math.random() * (max - min) + min);
    }

    public static int random2(int range) {
        return (int) ((java.lang.Math.random() * range) + 1);
    }

    public static int random3(int range) {
        return (int) (java.lang.Math.random() * (range));
    }

    public static int random(int range) {
        return (int) (Math.random() * (range + 1));
    }

    public static int getDistance(Position src, Position dest) {
        return getDistance(src.getX(), src.getY(), dest.getX(), dest.getY());
    }

    public static int getDistance(Position src, int destX, int destY) {
        return getDistance(src.getX(), src.getY(), destX, destY);
    }

    public static int getDistance(int x1, int y1, int x2, int y2) {
        int diffX = Math.abs(x1 - x2);
        int diffY = Math.abs(y1 - y2);
        return Math.max(diffX, diffY);
    }

    public static int getClosestX(Entity src, Entity target) {
        return getClosestX(src, target.getPosition());
    }

    public static int getClosestX(Entity src, Position target) {
        if (src.getSize() == 1)
            return src.getAbsX();
        if (target.getX() < src.getAbsX())
            return src.getAbsX();
        else if (target.getX() >= src.getAbsX() && target.getX() <= src.getAbsX() + src.getSize() - 1)
            return target.getX();
        else
            return src.getAbsX() + src.getSize() - 1;
    }

    public static int getClosestY(Entity src, Entity target) {
        return getClosestY(src, target.getPosition());
    }

    public static int getClosestY(Entity src, Position target) {
        if (src.getSize() == 1)
            return src.getAbsY();
        if (target.getY() < src.getAbsY())
            return src.getAbsY();
        else if (target.getY() >= src.getAbsY() && target.getY() <= src.getAbsY() + src.getSize() - 1)
            return target.getY();
        else
            return src.getAbsY() + src.getSize() - 1;
    }

    public static Position getClosestPosition(Entity src, Entity target) {
        return new Position(getClosestX(src, target), getClosestY(src, target), src.getHeight());
    }

    public static Position getClosestPosition(Entity src, Position target) {
        return new Position(getClosestX(src, target), getClosestY(src, target), src.getHeight());
    }

    public static int getEffectiveDistance(Entity src, Entity target) {
        Position pos = getClosestPosition(src, target);
        Position pos2 = getClosestPosition(target, src);
        return getDistance(pos, pos2);
    }

    public static <T> T randomTypeOfList(List<T> list) {
        if (list == null || list.size() == 0)
            return null;
        return list.get(new SecureRandom().nextInt(list.size()));
    }

}
