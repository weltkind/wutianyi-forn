package com.wutianyi.study.jvm;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class MainTest
{

    @Test
    public void outputclassFile() throws IOException
    {
        byte[] bytes = FileUtils.readFileToByteArray(new File("F:\\workspace\\wutianyi\\target\\classes\\com\\wutianyi\\study\\jvm\\A.class"));
        for(byte b : bytes)
        {
            System.out.println(Integer.toHexString(b));
        }
    }
}
