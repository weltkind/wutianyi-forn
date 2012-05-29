package com.wutianyi.study.vcard;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        List<String> contents = FileUtils.readLines(new File(Main.class.getResource("/com/wutianyi/study/vcard/resource.txt").getFile()));
        for(String content : contents)
        {
            System.out.println(content.split(",").length);
        }
    }
}
