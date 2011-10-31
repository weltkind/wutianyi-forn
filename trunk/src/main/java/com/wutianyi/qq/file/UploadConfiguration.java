package com.wutianyi.qq.file;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author hanjiewu
 * 
 */
public class UploadConfiguration
{
    private String dir = System.getProperty("user.dir");
    private Set<String> uploadSuffixs;
    private Set<String> unUploadFileNames;

    private static final String[] UPLOAD_SUFFIXS = new String[]
    { "ccs", "js", "jpg", "gif" };

    public UploadConfiguration()
    {
        this(System.getProperty("user.dir"), UPLOAD_SUFFIXS, null);
    }

    public UploadConfiguration(String _dir)
    {
        this(_dir, UPLOAD_SUFFIXS, null);
    }

    public UploadConfiguration(String _dir, String _uploadSuffixStr, String _unUploadFileNameStr)
    {
        String[] uploadSuffixs = null;
        if (StringUtils.isNotBlank(_uploadSuffixStr))
        {
            uploadSuffixs = _uploadSuffixStr.split(",");
        }
        else
        {
            uploadSuffixs = UPLOAD_SUFFIXS;
        }
        String[] unUploadFileNames = null;
        if (StringUtils.isNotBlank(_unUploadFileNameStr))
        {
            unUploadFileNames = _unUploadFileNameStr.split(",");
        }
        if (checkDirExists(_dir))
        {
            this.dir = _dir;
        }
        init(uploadSuffixs, unUploadFileNames);

    }

    public UploadConfiguration(String _dir, String[] _uploadSuffixs, String[] _unUploadFileNames)
    {
        if (checkDirExists(_dir))
        {
            this.dir = _dir;
        }
        init(_uploadSuffixs, _unUploadFileNames);
    }

    private boolean checkDirExists(String _dir)
    {
        if (StringUtils.isBlank(_dir))
        {
            return false;
        }
        File file = new File(_dir);
        if (!file.exists() || !file.isDirectory())
        {
            return false;
        }
        return true;
    }

    private void init(String[] _uploadSuffixs, String[] _unUploadFileNames)
    {
        this.uploadSuffixs = new HashSet<String>();
        this.unUploadFileNames = new HashSet<String>();

        if (null == _uploadSuffixs)
        {
            _uploadSuffixs = UPLOAD_SUFFIXS;
        }

        for (String uploadSuffix : _uploadSuffixs)
        {
            uploadSuffixs.add(uploadSuffix);
        }
        if (null != _unUploadFileNames)
        {
            for (String unUploadFileName : _unUploadFileNames)
            {
                this.unUploadFileNames.add(unUploadFileName);
            }
        }
    }

    public void handle(FileHandler fileHandler)
    {
        File root = new File(dir);
        if (root.exists() && root.isDirectory())
        {
            File[] children = root.listFiles();
            if (null != children)
            {
                for (File child : children)
                {
                    handleFile(fileHandler, child);
                }
            }
        }
    }

    private void handleFile(FileHandler fileHandler, File child)
    {
        if (child.isFile())
        {
            String name = child.getName();
            if (unUploadFileNames.contains(name))
            {
                return;
            }
            if (name.lastIndexOf(".") != -1)
            {
                String suffix = name.substring(name.lastIndexOf(".") + 1);
                if (uploadSuffixs.contains(suffix))
                {
                    fileHandler.handle(child);
                }
            }
        }
        if (child.isDirectory())
        {
            File[] files = child.listFiles();
            if (null != files)
            {
                for (File file : files)
                {
                    handleFile(fileHandler, file);
                }
            }
        }

    }
}
