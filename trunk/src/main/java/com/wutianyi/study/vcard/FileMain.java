package com.wutianyi.study.vcard;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;

public class FileMain
{
    public static void main(String[] args) throws IOException
    {
        byte[] b = FileUtils.readFileToByteArray(new File("2012-06-15-16-51-52-1500238000-preImport.csv"));
        PrintWriter pw = new PrintWriter(new File("test.csv"), "gbk");
        String[] lines = new String(b, "GBK").split("\r\n");
        System.out.println(lines.length);
        for (String line : lines)
        {
            System.out.println(line);
            pw.print(line);
            pw.print("\n");
        }
        pw.close();
    }
}
