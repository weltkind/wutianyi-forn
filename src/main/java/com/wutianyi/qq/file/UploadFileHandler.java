package com.wutianyi.qq.file;

import java.io.File;

import org.apache.commons.lang.StringUtils;

public class UploadFileHandler implements FileHandler
{

    private File baseFile;

    public UploadFileHandler(String _baseFile)
    {
        baseFile = new File(_baseFile);
        if (!baseFile.exists())
        {
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void handle(File file)
    {
        if (null == file || !file.exists() || !file.isFile())
        {
            return;
        }
        System.out.println("-------------------------------------------");
        System.out.println(file.getAbsolutePath());
        System.out.println(getFileDir(file));
        System.out.println(getFileName(file.getName()));
        System.out.println(getFileTypes(file.getName()));
        System.out.println("-------------------------------------------");
    }

    private String getFileDir(File file)
    {
        String basePath = baseFile.getAbsolutePath();
        String parentPath = file.getParent();
        String fileDir = StringUtils.EMPTY;

        if (parentPath.startsWith(basePath))
        {
            fileDir = parentPath.substring(basePath.length());
        }
        fileDir = baseFile.getName() + fileDir;
        return fileDir;
    }

    private String getFileTypes(String name)
    {
        if (StringUtils.isBlank(name))
        {
            return StringUtils.EMPTY;
        }
        int index = name.lastIndexOf('.');
        if (index == -1)
        {
            return StringUtils.EMPTY;
        }
        return name.substring(index + 1);
    }

    private String getFileName(String name)
    {
        if (StringUtils.isBlank(name))
        {
            return name;
        }
        int index = name.lastIndexOf('.');
        if (index == -1)
        {
            return name;
        }
        return name.substring(0, index);
    }
}
