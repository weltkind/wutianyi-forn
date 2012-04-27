package com.wutianyi.study.resources;

import java.util.Locale;

public class LocaleRes
{
    private String baseName;
    private Locale locale;

    public LocaleRes(String _baseName, Locale _locale)
    {
        this.baseName = _baseName;
        this.locale = _locale;
    }
    
    public static void main(String[] args)
    {
        System.out.println(Locale.CHINA.getCountry());
        System.out.println(Locale.US.getLanguage());
    }
}
