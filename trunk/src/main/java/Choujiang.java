import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.lang.StringUtils;

public class Choujiang
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("E:\\shuju\\download.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        
        String line = reader.readLine();
        int i = 0;
        while(StringUtils.isNotBlank(line)) {
            if(line.contains("4") && line.contains("8")) {
                System.out.println(line);
                i ++;
            }
            if(i >= 100) {
                break;
            }
            line = reader.readLine();
        }
        reader.close();
    }
}
