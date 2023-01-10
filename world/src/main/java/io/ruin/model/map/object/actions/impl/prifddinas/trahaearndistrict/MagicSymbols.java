package io.ruin.model.map.object.actions.impl.prifddinas.trahaearndistrict;

public class MagicSymbols {
//
//    private static final int FIRE_SYMBOL = 36199;
//    private static final int HOLY_SYMBOL = 36200;
//
//    public static GameObject MAGIC_SYMBOL1 = GameObject.spawn(FIRE_SYMBOL, 3023, 6046, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL2 = GameObject.spawn(FIRE_SYMBOL, 3023, 6051, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL3 = GameObject.spawn(FIRE_SYMBOL, 3026, 6045, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL4 = GameObject.spawn(FIRE_SYMBOL, 3030, 6042, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL5 = GameObject.spawn(FIRE_SYMBOL, 3030, 6047, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL6 = GameObject.spawn(FIRE_SYMBOL, 3026, 6052, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL7 = GameObject.spawn(FIRE_SYMBOL, 3030, 6055, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL8 = GameObject.spawn(FIRE_SYMBOL, 3030, 6050, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL9 = GameObject.spawn(FIRE_SYMBOL, 3039, 6045, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL10 = GameObject.spawn(FIRE_SYMBOL, 3035, 6042, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL11 = GameObject.spawn(FIRE_SYMBOL, 3035, 6047, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL12 = GameObject.spawn(FIRE_SYMBOL, 3039, 6052, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL13 = GameObject.spawn(FIRE_SYMBOL, 3035, 6055, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL14 = GameObject.spawn(FIRE_SYMBOL, 3035, 6050, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL15 = GameObject.spawn(FIRE_SYMBOL, 3042, 6046, 0, 10, 0);
//    public static GameObject MAGIC_SYMBOL16 = GameObject.spawn(FIRE_SYMBOL, 3042, 6051, 0, 10, 0);
//
//    public static int check = 0;
//
//    public static void magicSymbols() {
//        if (Random.rollPercent(90) && check != 1) {
//            MAGIC_SYMBOL1.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL2.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL3.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL4.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL5.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL6.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL7.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL8.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL9.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL10.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL11.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL12.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL13.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL14.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL15.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL16.setId(FIRE_SYMBOL);
//            check = 1;
//        } else if (Random.rollPercent(90) && check != 2) {
//            MAGIC_SYMBOL1.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL2.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL3.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL4.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL5.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL6.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL7.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL8.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL9.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL10.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL11.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL12.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL13.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL14.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL15.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL16.setId(FIRE_SYMBOL);
//            check = 2;
//        } else if (Random.rollPercent(90) && check != 3) {
//            MAGIC_SYMBOL1.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL2.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL3.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL4.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL5.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL6.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL7.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL8.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL9.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL10.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL11.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL12.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL13.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL14.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL15.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL16.setId(HOLY_SYMBOL);
//            check = 3;
//        } else if (Random.rollPercent(90) && check != 4) {
//            MAGIC_SYMBOL1.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL2.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL3.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL4.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL5.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL6.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL7.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL8.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL9.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL10.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL11.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL12.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL13.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL14.setId(FIRE_SYMBOL);
//            MAGIC_SYMBOL15.setId(HOLY_SYMBOL);
//            MAGIC_SYMBOL16.setId(FIRE_SYMBOL);
//            check = 4;
//        } else {
//            magicSymbols();
//        }
//    }

}
