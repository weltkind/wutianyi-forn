import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class MyTest
{
    private static MessageFormat recordSQLTemplate = new MessageFormat(
            "insert into draw.draw_record(uin, status, gmt_create, gmt_modified) values({0}, {1}, now(), now())");
    private static MessageFormat recordFetchSqlTemplate = new MessageFormat(
            "select type from draw.draw_record where uin={0}");
    private static MessageFormat recordUpdateSqlTemplate = new MessageFormat(
            "update draw.draw_record set type = {0} where uin={1}");

    private static Pattern imgPattern = Pattern.compile("(.*)\\[img\\](.*)\\[\\/img\\](.*)");
    private static Pattern tuyaPattern = Pattern.compile("\\[tuya\\](.*)\\[\\/tuya\\]");

    @Test
    public void messageFormat()
    {
        MessageFormat recordSQLTemplate = new MessageFormat("insert into draw.draw_record(uid) values({0})");
        System.out.println(recordSQLTemplate.format(new Object[]
        { 11 }));
    }

    @Test
    public void insertFormat()
    {
        System.out.println(recordSQLTemplate.format(new Object[]
        { "" + 1, 2 }));
        System.out.println(recordFetchSqlTemplate.format(new Object[]
        { 222 }));
        System.out.println(recordUpdateSqlTemplate.format(new Object[]
        { 2, 222 }));
    }

    @Test
    public void decode() throws UnsupportedEncodingException
    {
        System.out.println(URLDecoder.decode("\"%09style=\"background:url(javascript:alert(/XSS/))\"%09\"", "utf-8"));
    }

    @Test
    public void date()
    {
        Date date = new Date();
        System.out.println(date.getDate());
        String str = "/120";
        System.out.println(str.replace("/120", "/320"));
        String[] str1 = new String[]
        {};

    }

    @Test
    public void subStr() throws UnsupportedEncodingException
    {
        String str = "QQ_H_12121";
        System.out.println(str.substring(5));
        System.out.println(URLDecoder.decode("%2Fg%2Fs%3Fsid%3DAa8RoszUnuxQ-x0jG_snUk5z%26amp%3Baid%3Dtdraw", "utf-8"));
    }

    @Test
    public void time()
    {
        System.out.println(System.currentTimeMillis());
        Calendar c2 = Calendar.getInstance();
        Calendar calendar = new GregorianCalendar(2011, 8, 1, 0, 0, 0);
        Calendar c1 = new GregorianCalendar(2011, 8, 14, 0, 0, 0);
        System.out.println(c1.get(Calendar.HOUR_OF_DAY));

        System.out.println(c2.getTimeInMillis());
        System.out.println(calendar.getTimeInMillis());
        System.out.println(c1.getTimeInMillis());

        Date date = new Date(1315843200000L);
        System.out.println(date);
    }

    @Test
    public void createString() throws IOException
    {
        File file = new File("E:\\shuju\\黄钻中奖名单.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        StringBuilder builder = new StringBuilder();

        String line = reader.readLine();
        int i = 0;
        while (StringUtils.isNotBlank(line))
        {
            i++;
            if (i != 10)
            {

                builder.append("\"" + line.trim() + "\",");
            }
            else
            {
                builder.append("\"" + line.trim() + "\"");
                System.out.println(builder.toString());
                builder.delete(0, builder.length());
                i = 0;
            }

            line = reader.readLine();
        }
        // System.out.println(builder.toString());
        reader.close();
    }

    @Test
    public void testJson() throws JsonGenerationException, JsonMappingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("num", 1);
        map.put("integer", 1);
        map.put("decimal", 2);
        System.out.println(mapper.writeValueAsString(map));
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, map);
        System.out.println(writer);
    }

    @Test
    public void testCapacity()
    {
        Set<Integer> s = new HashSet<Integer>(3, 2);
        s.add(1);
        s.add(2);
        s.add(3);
        System.out.println(s.size());

        for (int i = 0; i < 3; i++)
        {
            int r = (int) (Math.random() * s.size());
            System.out.println(r);
        }
    }

    @Test
    public void testPattern()
    {
        String str = "[tuya]http://117.135.148.34:8080/mchatad/0302025d0ead2c28bdb17a467d421243982d67bfc7ac9ccefc0bff47bcd8972b5aa3[/tuya]";
        Matcher matcher = tuyaPattern.matcher(str);
        if (matcher.find())
        {
            System.out.println(matcher.group(1));
        }
    }
}
