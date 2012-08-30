package com.wutianyi.qq;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class SmallTableGYParseMain
{
    public static void main(String[] args) throws IOException
    {
        List<String> contents = FileUtils.readLines(new File("web_pim_profile_storage.log"));
        
        for(String content : contents)
        {
            
            String[] cs = content.split("\\s");
            if(cs.length != 4)
            {
                continue;
            }
            if(cs[0].startsWith("gy_"))
            {
                char c = cs[0].charAt(cs[0].length() - 1);
                if(!Character.isDigit(c))
                {
                    System.out.println(cs[0] + ": " + cs[2]);
                }
            }
        }
    }
}
