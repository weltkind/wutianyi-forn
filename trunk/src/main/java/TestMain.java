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
