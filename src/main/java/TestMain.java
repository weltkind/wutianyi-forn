import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class TestMain
{
    public static void main(String[] args) throws IOException
    {
        String[] files = new String[]
        { "jquery-1.4.2.min.js", "jquery.easing.1.3.js" };
        StringBuilder builder = new StringBuilder();
        for (String file : files)
        {
            List<String> contents = FileUtils.readLines(new File(file), "utf8");

            for (String content : contents)
            {
                builder.append(content.trim());
                builder.append('\n');
            }
        }

        System.out.println(builder.substring(0, builder.length() - 1));

    }
}
