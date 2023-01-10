package io.ruin.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class XMLController {

    /**
     * The XStream Instance We don't have to implement any drivers as we use the
     * default set one, which is very fast
     * <p>
     * Before you cry, i'm not making the XStream instance constant as it looks
     * ugly
     */
    //  private static final XStream xstream = new XStream(new DomDriver());

    /**
     * @return the xstream
     */
//    public static XStream getXstream() {
//        return xstream;
//    }

    /**
     * All the xstream alias in here I am not importing it into an another xml
     * as if we need to refactor it will not edit it
     */
    static {

    }

    /**
     * Writes the XML file, using try and finally will allow the file output to
     * close if an exception is thrown (will stop memory leaks)
     *
     * @param object The object getting written
     * @param file   The file area and name
     * @throws IOException
     */
    public static void writeXML(Object object, File file) throws IOException {
        FileOutputStream out = new FileOutputStream(file);
        try {
            //   xstream.toXML(object, out);
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * Writes the XML file, using try and finally will allow the file output to
     * close if an exception is thrown (will stop memory leaks)
     *
     * @param object The object getting written
     * @return The XML
     */
    public static String writeXML(Object object) {

        return null/*xstream.toXML(object)*/;
    }

    /**
     * Reads an object from an XML file.
     *
     * @param file The file.
     * @return The object.
     * @throws IOException if an I/O error occurs. Edit Sir Sean: Now uses generic's
     * @author Graham Edgecombe
     */
    @SuppressWarnings("unchecked")
    public static <T> T readXML(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        try {
            return null/*xstream.fromXML(in)*/;
        } finally {
            in.close();
        }
    }

    /**
     * Reads an object from an XML string.
     *
     * @param s The XML.
     * @return The object. Edit Sir Sean: Now uses generic's
     * @author Graham Edgecombe
     */
    @SuppressWarnings("unchecked")
    public static <T> T readXML(String s) {

        return null;//xstream.fromXML(s);
    }

}
