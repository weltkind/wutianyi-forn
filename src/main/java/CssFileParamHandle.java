import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

public class CssFileParamHandle implements FileHandler
{

    private static Pattern pattern = Pattern.compile("\\$\\{([^}]*)\\}");

    @Override
    public String handle(String buffer, Map<String, String> params)
    {
        Matcher matcher = pattern.matcher(buffer);
        StringBuilder builder = new StringBuilder();
        int start = 0;
        int end = 0;
        while (matcher.find())
        {
            end = matcher.start();
            builder.append(buffer.substring(start, end));
            
            String value = params.get(matcher.group(1));
            
            if (StringUtils.isNotBlank(value))
            {
                builder.append(value);
            }

            start = matcher.end();
            System.out.println(matcher.group() + " position: " + matcher.start() + " : " + matcher.end());

        }
        builder.append(buffer.substring(start, buffer.length()));

        return builder.toString();
    }

    public static void main(String[] args) throws IOException
    {
        
        List<String>fileContents = FileUtils.readLines(new File("main.js"));
        
        StringBuilder builder = new StringBuilder();
        for(String fileContent : fileContents)
        {
            builder.append(fileContent);
        }
        
        
        CssFileParamHandle handle = new CssFileParamHandle();
        Map<String, String> params = new HashMap<String, String>();
        params.put("bg_logo_qqpinyin.png",
                "http://softfile.3g.qq.com/msoft/sec/webss/webapp_scan/images/bg_logo_qqpinyin.png");
        String v = handle.handle(builder.toString(), params);
//        System.out.println(css);
        System.out.println(v);
    }

}
