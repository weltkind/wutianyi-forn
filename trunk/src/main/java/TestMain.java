import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import scala.collection.mutable.StringBuilder;

public class TestMain
{
    public static void main(String[] args) throws IOException, ParseException
    {
        String p = "wuhanjie860728";
        String v = DigestUtils.md5Hex(p).toUpperCase();
        System.out.println(v);
        String v_c = "0000000024edc220";
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < v.length(); i = i+ 2)
        {
          char c=  (char) ((Character.digit(v.charAt(i), 16) << 4) | (Character.digit(v.charAt(i + 1), 16)));
          builder.append(c);
        }
        
        for(int i = 0; i < v_c.length(); i = i +2)
        {
            char c=  (char) ((Character.digit(v_c.charAt(i), 16) << 4) | (Character.digit(v_c.charAt(i + 1), 16)));
            System.out.println((int)c);
            builder.append(c);
        }
        
        System.out.println(builder.toString());
        
        String g = builder.toString();
        System.out.println(g.length());
        System.out.println(g);
        String h = DigestUtils.md5Hex(g).toUpperCase();
        System.out.println(h);
        System.out.println((char) ((Character.digit('A', 16) << 4) | (Character.digit('D', 16))));
        char[] cs = new char[]{' '};
        
        System.out.println(DigestUtils.md5Hex("860728"));
    }
    
    public static int downloadKingrootDatas(int type, String date, String fileName)
    {
        String condition = "' group by imei order by id asc";
        String sql = null;
        if (type == 2)
        {
            sql = "select * into outfile "
                    + fileName
                    + " FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\n' from king_root where (type=2 or (type=1 and flag=2)) and date= '"
                    + date + condition;
        }
        else
        {
            sql = "select * into outfile "
                    + fileName
                    + " FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\\n' from king_root where type= "
                    + type + " and date= '" + date + condition;
            System.out.println(sql);
        }
        return 0;
    }

}
