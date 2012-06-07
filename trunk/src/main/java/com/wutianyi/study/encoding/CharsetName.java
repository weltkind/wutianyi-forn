package com.wutianyi.study.encoding;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class CharsetName
{
    public static void main(String[] args) throws IOException
    {
        Map<String, Charset> charset = Charset.availableCharsets();
        for (Entry<String, Charset> entry : charset.entrySet())
        {
            // System.out.println(entry.getValue() +
            // entry.getValue().newDecoder());
        }

        byte[] datas = FileUtils.readFileToByteArray(new File(CharsetName.class.getResource(
                "/com/wutianyi/study/encoding/jquery.easing.1.3.js").getFile()));
        System.out.println(CharsetServices.getEncoding(datas));
    }
}
