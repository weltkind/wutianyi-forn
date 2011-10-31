package com.wutianyi.study.file;

import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        File file = new File(System.getProperty("user.dir"),"src/main/java/com/wutianyi/study/file/Main.java");
        System.out.println(file.lastModified());
    }
}
