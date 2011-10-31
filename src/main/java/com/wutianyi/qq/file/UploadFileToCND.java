package com.wutianyi.qq.file;

import java.io.File;

public class UploadFileToCND
{
    public static void main(String[] args)
    {
        UploadConfiguration conf = new UploadConfiguration();
        FileHandler fileHandler = new UploadFileHandler(System.getProperty("user.dir"));
        conf.handle(fileHandler);
    }
}
