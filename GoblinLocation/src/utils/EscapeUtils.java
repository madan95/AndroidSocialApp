package utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;

public class EscapeUtils {

    public static final HashMap m = new HashMap();
    static {
        m.put(34, "&quot;"); // < - less-than
        m.put(60, "&lt;");   // < - less-than
        m.put(62, "&gt;");   // > - greater-than
    //User needs to map all html entities with their corresponding decimal values. 
        m.put(160, "&nbsp;");
        m.put(38	, "&amp;");
        m.put(162, "&cent;");
        
        m.put(163, "&pound;");
        m.put(165, "&yen;");
        
        m.put(8364, "&euro;");
        
        m.put(167, "&sect;");

    }
 
    public static String escapeHtml(String str) {
     //   String str = "<script>alert(\"abc\")</script>";
        try {
            StringWriter writer = new StringWriter((int) 
                           (str.length() * 1.5));
            escape(writer, str);
            System.out.println("encoded string is " + writer.toString() );
            return writer.toString();
           } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
                                                    }
                                                     }
 
    public static void escape(Writer writer, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            int ascii = (int) c;
            String entityName = (String) m.get(ascii);
            if (entityName == null) {
                if (c > 0x7F) {
                    writer.write("&#");
                    writer.write(Integer.toString(c, 10));
                    writer.write(';');
                } else {
                    writer.write(c);
                }
            } else {
                     writer.write(entityName);
            }
        }
    }
}