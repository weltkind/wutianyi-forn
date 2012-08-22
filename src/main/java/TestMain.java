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

import org.apache.commons.io.FileUtils;

public class TestMain
{
    public static void main(String[] args) throws IOException, ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date date = dateFormat.parse("2012-08-01");
        Date date_2 = dateFormat.parse("2012-08-20");

        if (date.compareTo(date_2) > 0)
        {
            System.out.println("yes");
        }

        Calendar calendar = new GregorianCalendar(2012, 7, 1);
        Calendar calendar_2 = new GregorianCalendar(2012, 7, 20);
        System.out.println(Integer.parseInt("09"));
        
        while(calendar.compareTo(calendar_2) <= 0)
        {
            System.out.println(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
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
