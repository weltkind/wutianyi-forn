package com.wutianyi.study.profile;

import java.util.EnumSet;

public class ProfileMain
{
    public enum Style
    {
        BOLD("b"), ITALIC("i"), UNDERLINE("u"), STRIKETHROUGH("s");

        private String value;

        private Style(String value)
        {
            this.value = value;
        }

        public String toString()
        {
            return "bold";
        }
    };

    public static void main(String[] args)
    {
        System.out.println(Style.BOLD.equals(Style.BOLD));
    }
}
