package com.wutianyi.study.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        File file = new File("pim.2012-09-11");
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel fileChannel = fileInputStream.getChannel();

        BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
        

        String line = br.readLine();
        System.out.println(fileChannel.position());
        int i = 0;
        while (null != line)
        {
            fileChannel = fileInputStream.getChannel();
            System.out.println(fileChannel.position());
            line = br.readLine();
            i++;
            if (i > 1000)
            {
                break;
            }
        }

        fileInputStream.close();

    }
}
