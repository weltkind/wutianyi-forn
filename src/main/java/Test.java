import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.LocaleUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class Test
{
    enum t
    {
        One
        {
            public void test()
            {
                System.out.println("out");
            }
        };
    }

    private static void deleteFile(File f)
    {
        File[] files = f.listFiles();

        for (File file : files)
        {
            if (file.getName().equals(".svn"))
            {
                try
                {
                    FileUtils.deleteDirectory(file);
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (file.isDirectory())
            {
                deleteFile(file);
            }
        }
    }

    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException
    {
        // System.out.println("yesy");
        // String test =
        // "\u6E29\u99A8\u63D0\u9192\uFF1A<br/>\u60A8\u5F53\u524D\u7684\u8054\u7F51\u65B9\u5F0F\u548C\u7EC8\u7AEF\u6682";
        // System.out.println(new String(test.getBytes("utf-8")));;
        //
        // long l = 192;
        // System.out.println(l >> 24 & 0xFF);
        // System.out.println((l & 0xFFFFFF) >> 16 & 0xFF);
        // System.out.println((l & 0xFFFF) >> 8 & 0xFF);
        // System.out.println(l & 0xFF);
        String str = "中国";
        String s = "aa\\;c;c[:]";

        System.out.println(str.length());
        System.out.println(s.length());
        System.out.println(s.replaceAll("\\\\", "@"));
        System.out.println(s.replaceAll("\\\\;", "[:]").replaceAll(";", "").replaceAll("\\[:\\]", ";"));

        System.out.println(DigestUtils.md5Hex("619561504".getBytes()));
        System.out.println("周碧华/独立观点 百姓传媒".replace("/", ""));

        for (int i = 0; i < 10; i++)
        {
            System.out.println(RandomStringUtils.randomAlphanumeric(10));
        }
        System.out.println(DigestUtils.md5Hex("619561504".getBytes()));

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        System.out.println(calendar.getTime());
        System.out.println(new Date());
        System.out.println((calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000 + 1);
        System.out.println(Locale.CHINA);
        System.out.println(Locale.TAIWAN);
        // System.out.println(LocaleUtils.toLocale("e_adf"));

        // System.out.println(LocaleUtils.toLocale("enaf").getLanguage());
        ResourceBundle resource = ResourceBundle.getBundle("table/t", Locale.US);
        System.out.println(resource.getString("commons.type"));

        ConcurrentHashMap<String, String> m = new ConcurrentHashMap<String, String>();
        m.put("t_1", "t_1");
        m.put("t_2", "t_2");
        for (Entry<String, String> entry : m.entrySet())
        {
            m.put(entry.getKey(), "tt");
        }
        System.out.println(m);
        String base = Base64.encodeBase64String("w|w|w|".getBytes());
        System.out.println(base);
        System.out.println(new String(Base64.decodeBase64("c2Rr")));

        System.out.println(Runtime.getRuntime().availableProcessors());

        System.out.println(Integer.toHexString(26697));
        System.out.println("http://3gimg.qq.com/misc/b0127.jpg,http://3gimg.qq.com/misc/b0127.jpg,,".split(",").length);
        String referer = "http://hanjiewu.cs0309.3g.qq.com/web_txl/main/index.jsp";
        System.out.println(referer.substring(7));
        System.out
                .println(URLDecoder
                        .decode("%3Cp+class%3D%22update-date%22%3E%0D%0A%09%E5%8F%91%E5%B8%83%E6%97%A5%E6%9C%9F%EF%BC%9A2012%E5%B9%B43%E6%9C"));

        ObjectMapper mapper = new ObjectMapper();
        ConcurrentHashMap<String, String> mMap = new ConcurrentHashMap<String, String>();
        mMap.put("test", "test");
        String mv = mapper.writeValueAsString(mMap);
        Map<String, String> nMap = mapper.readValue(mv, new TypeReference<ConcurrentHashMap<String, Object>>()
        {
        });
        System.out.println(nMap.getClass().getName());

        System.out.println(new Date(System.currentTimeMillis() + 1 * 356 * 24 * 60 * 60 * 1000l));
        Properties prop = new Properties();
        prop.setProperty("test", "test");
        System.out.println(Test.class.getResource("/").getFile());
        prop.store(new PrintWriter(new File(System.getProperty("user.dir"), "test1.properties")), "");

        int index = 0;
        int start = 0;
        int end = 0;
        String file = "/images/asfa";

        if (file.startsWith("/"))
        {
            start = 1;
        }
        for (int i = start; i < file.length(); i++)
        {
            if (file.charAt(i) == '/')
            {
                end = i;
            }
        }
        if (end == 0)
        {
            end = file.length();
        }
        System.out.println(file.substring(start, end));

        System.out.println(file.substring(0, start) + "images2.0" + file.substring(end, file.length()));

        System.out.println(new Date(System.currentTimeMillis() / 1000 * 1000));
        String queryString = "js/main.js;js/jquery.js";
        System.out.println("<script type='text/javascript' src='" + "webapp_scan/test/test.js?request=" + queryString
                + "&key=test'></script>");
        String[] qs = queryString.split(";");
        StringBuilder builder = new StringBuilder();
        for (String q : qs)
        {
            builder.append("<script type='text/javascript' src='webapp_scan/" + q + "'></script>");
        }
        System.out.println(builder.toString());
        List<String> fileContents = FileUtils.readLines(new File("globle.css"));
        StringBuilder builder_1 = new StringBuilder();
        PrintWriter pw = new PrintWriter(new File("globle_1.css"));
        String v = null;
        for(String fileContent : fileContents)
        {
            if(fileContent.trim().startsWith("\\\\"))
            {
                v = fileContent.trim();
            }
            builder_1.append(fileContent + "\n");
        }
        pw.append(builder_1.substring(0, builder_1.length() - 1));
        pw.close();
        System.out.println(builder_1.toString());
        String test = "\\\\eest";
        if(test.startsWith("\\"))
        {
            
        }
        System.out.println(test);
        System.out.println(v);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM");
        Calendar lastMonth = Calendar.getInstance();
        lastMonth.add(Calendar.MONTH, -1);
        String t = dateFormat.format(lastMonth.getTime());
        System.out.println(t);
        System.out.println(DigestUtils.md5Hex("king_roottest|test|test"));
        
        List<String> dates = new ArrayList<String>();
        dates.add("2012-04-12");
        dates.add("2012-03-12");
        Collections.sort(dates, new Comparator<String>()
        {

            @Override
            public int compare(String first, String second)
            {
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                try
                {
                    return dateformat.parse(first).compareTo(dateformat.parse(second));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        for(String d : dates)
        {
            System.out.println(d);
        }
        System.out.println(DigestUtils.md5Hex("123").length());
        
        String mobile = "+8615013152239";
//        if(StringUtils.isNotBlank(mobile))
//        {
//            mobile = mobile.replaceFirst("+", "");
//            if(mobile.startsWith("86"))
//            {
//                mobile = mobile.substring(2);
//            }
//        }
        System.out.println(mobile);
        SimpleDateFormat f = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss Z");
        System.out.println(f.format(new Date()));
        
        String t3="test;af";
        String[] ts3 = t3.split(";", 1);
        for(String ts4 : ts3)
        {
            System.out.println(ts4);
        }
    }

}
