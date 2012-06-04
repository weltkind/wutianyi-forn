package com.wutianyi.study.vcard;

import org.apache.commons.lang.StringUtils;

public class VcardUtils
{
    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = CR + LF;
    public static final String SP = " ";
    private static final char[] NEED_ESCAPING = new char[]
    { ',', ';', '\\', '\n' };

    public static boolean needsEscaping(String text)
    {
        boolean needs = false;
        for (int i = 0; i < NEED_ESCAPING.length; i++)
        {
            if (text.indexOf(NEED_ESCAPING[i]) != -1)
            {
                needs = true;
                break;
            }
        }
        return needs;
    }

    public static String escapeString(String text)
    {
        if (!needsEscaping(text))
        {
            return text;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            switch (c)
            {
            case '\n':
            {
                builder.append('\\');
                builder.append('n');
                break;
            }
            case '\\':
            {
                builder.append('\\');
                builder.append('\\');
                break;
            }
            case ',':
            {
                builder.append('\\');
                builder.append(',');
                break;
            }
            case ';':
            {
                builder.append('\\');
                builder.append(';');
                break;
            }
            case ':':
            {
                builder.append('\\');
                builder.append(':');
                break;
            }
            default:
            {
                builder.append(c);
            }
            }
        }
        return builder.toString();
    }

    public static String foldLine(String line, String eolDelimeter, int maxChars, String indent)
    {
        if (StringUtils.isBlank(line) || maxChars < 0 || line.length() < maxChars)
        {
            return line;
        }
        boolean loop = true;
        boolean first = true;
        int crnt = 0;
        int prev = 0;

        StringBuilder builder = new StringBuilder();
        while (loop)
        {
            prev = crnt;
            crnt = crnt + maxChars;
            if (crnt > line.length())
            {
                if (prev < line.length())
                {
                    if (!first)
                    {
                        builder.append(indent);
                    }
                    builder.append(line.substring(prev).trim());
                    if (eolDelimeter != null)
                    {
                        builder.append(eolDelimeter);
                    }
                }

                loop = false;
            }
            else
            {
                if (!first)
                {
                    builder.append(indent);
                }
                else
                {
                    first = false;
                }
                builder.append(line.substring(prev, crnt).trim());
                if (eolDelimeter != null)
                {
                    builder.append(eolDelimeter);
                }
            }
        }
        return builder.toString().trim();
    }
}
