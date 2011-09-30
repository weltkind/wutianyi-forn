package com.wutianyi.study.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyPattern
{
    public static void main(String[] args)
    {
        Pattern imgPattern = Pattern.compile("(.*)\\[img\\](.*)\\[\\/img\\](.*)");
        
        Matcher matcher = imgPattern.matcher("abc[img]def[/img]ghi");
        if(matcher.find()) {
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
        }
    }

}
