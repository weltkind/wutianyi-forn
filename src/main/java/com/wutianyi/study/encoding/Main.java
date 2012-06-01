package com.wutianyi.study.encoding;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        UniversalDetector detector = new UniversalDetector(null);
        //
        // File file = new
        // File(Main.class.getResource("/com/wutianyi/study/encoding").getFile());
        // File[] files = file.listFiles();
        // for(File f : files)
        // {
        // System.out.println(f.getName());
        // byte[] datas = FileUtils.readFileToByteArray(f);
        // detector.handleData(datas, 0, datas.length);
        // detector.dataEnd();
        // System.out.println(detector.getDetectedCharset());
        // detector.reset();
        // }
        byte[] data = FileUtils.readFileToByteArray(new File(Main.class.getResource(
                "/com/wutianyi/study/encoding/gbk.txt").getFile()));
        System.out.println(data.length);
        detector.handleData(data, 0, data.length);
        detector.dataEnd();
        System.out.println(detector.getDetectedCharset());
        detector.reset();
    }

}
