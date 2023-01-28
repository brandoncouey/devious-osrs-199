package io.ruin.cache;

/**
 * Remember: If we add/remove/change a color, remember to also change it in the custom JournalInterface client class!
 */
public enum Color {

    RED("FF0000"),
    YELLOW("f4d03f"),
    YELLOW2("f4d03f"),
    GREEN("249b31"),
    BLACK("000000"),
    GREY("808080"),
    TEAL("008080"),

    ORANGE("FFA500"),
    ORANGE_RED("FF4500"),
    TOMATO("FF6347"),
    CRIMSON("DC143C"),
    MAROON("800000"),

    BLUE("1e55e8"),
    OPAL("c8ee9f"),
    JADE("adde78"),
    REDTOPAZ("db1e73"),
    MITHRIL("4c4c70"),
    ADAMANT("3f503f"),
    RUNE("516d78"),
    DRAGON("831a0d"),
    SUPPORT("c4d4e0"),
    MODERATOR("C0C0C0"),
    ADMINISTRATOR("0bff1e"),
    YOUTUBER("ff0b0b"),
    OWNER("b9f2ff"),

    COOL_BLUE("0040ff"),
    BABY_BLUE("1E90FF"),
    CYAN("00FFFF"),
    ONYX("78757A"),
    PURPLE("800080"),
    VIOLET("EE82EE"),
    PINK("FFC0CB"),

    WHITE("FFFFFF"),
    SHADED("cbcbcb"),
    WHEAT("F5DEB3"),
    SILVER("C0C0C0"),

    OLIVE("808000"),

    DARK_RED("6f0000"),
    DARK_GREEN("006600"),

    RAID_PURPLE("ef20ff");

    public final String html;

    Color(String html) {
        this.html = html;
    }

    public String tag() {
        return "<col=" + html + ">";
    }

    public String wrap(String s) {
        return tag() + s + "</col>";
    }

}