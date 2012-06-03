package com.wutianyi.study.encoding;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

public class CharsetName
{
    public static void main(String[] args)
    {
        Map<String, Charset>charset = Charset.availableCharsets();
        for(Entry<String, Charset> entry : charset.entrySet())
        {
//            System.out.println(entry.getValue() + entry.getValue().newDecoder());
        }
    }
}
