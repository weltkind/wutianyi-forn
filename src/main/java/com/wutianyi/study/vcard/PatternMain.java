package com.wutianyi.study.vcard;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class PatternMain
{
    private static Pattern pattern = Pattern.compile("\r\nN[:;].*");

    public static void main(String[] args) throws IOException
    {
        String vcard = new String(FileUtils.readFileToByteArray(new File("contacts.vcf")));
        toTrim(vcard);
    }

    public static String toTrim(String vcard)
    {

        Matcher matcher = pattern.matcher(vcard);
        StringBuilder builder = new StringBuilder();
        while (matcher.find())
        {
            builder.append(vcard.substring(0, matcher.start()));

            StringBuilder value = new StringBuilder(vcard.substring(matcher.start(), matcher.end()));

            int index = matcher.end();
            if (vcard.charAt(index) == ' ')
            {
                while (vcard.charAt(++index) == ' ' && index < vcard.length())
                {
                }

                boolean b = true;
                boolean enter = false;
                while (b)
                {
                    switch (vcard.charAt(index))
                    {
                    case '\r':
                    {
                        enter = true;
                        break;
                    }
                    case '\n':
                    {
                        if (enter)
                        {
                            b = false;
                        }
                        break;
                    }
                    default:
                    {
                        value.append(vcard.charAt(index));
                    }
                        ++index;
                    }
                }
            }
            
        }

        // while (matcher.matches())
        // {
        // int index = matcher.start();
        // System.out.println(index);
        // System.out.println(vcard.substring(matcher.start(), matcher.end()));
        // }

        return null;
    }
}
