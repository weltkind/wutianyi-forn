import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import scala.collection.mutable.StringBuilder;

public class TestMain
{
    public static void main(String[] args) throws IOException, ParseException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(
                "access.log.20121224_07"))));
        String line = br.readLine();
        
        String[] ls = line.split(" ");
        for(String s : ls) {
            System.out.println(s);
        }
        
        br.close();
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
