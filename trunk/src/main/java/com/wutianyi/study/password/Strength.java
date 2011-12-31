package com.wutianyi.study.password;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class Strength
{
    private static Pattern pattern = Pattern.compile("^((\\w+)||(\\d+))$");
    private static char[] symbols = new char[]
    { '!', '@', '#', '$', '%', '^', '&', '*', '?', '_', '~' };

    public static int testStrength(String password, String username)
    {
        int score = 0;

        if (StringUtils.isBlank(password) || password.length() < 4 || StringUtils.equalsIgnoreCase(password, username))
        {
            return 0;
        }
        score += password.length() * 4;
        score += (checkRepetition(1, password).length() - password.length()) * 1;
        score += (checkRepetition(2, password).length() - password.length()) * 1;
        score += (checkRepetition(3, password).length() - password.length()) * 1;
        score += (checkRepetition(4, password).length() - password.length()) * 1;

        int len = password.length();
        int numCount = 0;
        int symbolCount = 0;
        int upperCount = 0;
        int lowerCount = 0;
        for (int i = 0; i < len; i++)
        {
            char c = password.charAt(i);
            if (isNum(c))
            {
                numCount++;
            }
            else if (isSymbol(c))
            {
                symbolCount++;
            }
            else if (isLower(c))
            {
                lowerCount++;
            }
            else if (isUpper(c))
            {
                upperCount++;
            }
        }
        if (numCount >= 3)
        {
            score += 5;
        }
        if (symbolCount >= 2)
        {
            score += 5;
        }
        if (upperCount != 0 && lowerCount != 0)
        {
            score += 10;
        }
        if (numCount != 0 && (upperCount != 0 || lowerCount != 0))
        {
            score += 15;
        }
        if (numCount != 0 && symbolCount != 0)
        {
            score += 15;
        }
        if ((upperCount != 0 || lowerCount != 0) && symbolCount != 0)
        {
            score += 15;
        }
        if (pattern.matcher(password).matches())
        {
            score -= 10;
        }
        return score;
    }

    private static boolean isNum(char c)
    {
        if (c >= '0' && c <= '9')
        {
            return true;
        }
        return false;
    }

    private static boolean isUpper(char c)
    {
        if (c >= 'A' && c <= 'Z')
        {
            return true;
        }
        return false;
    }

    private static boolean isLower(char c)
    {
        if (c >= 'a' && c <= 'z')
        {
            return true;
        }
        return false;
    }

    private static boolean isSymbol(char c)
    {
        for (char s : symbols)
        {
            if (s == c)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param pLen
     * @param password
     * @return
     */
    private static String checkRepetition(int pLen, String password)
    {
        String res = "";
        for (int i = 0; i < password.length(); i++)
        {
            boolean repeated = true;
            int j = 0;
            for (; j < pLen && (j + i + pLen) < password.length(); j++)
            {
                repeated = repeated && (password.charAt(j + i) == password.charAt(j + i + pLen));
            }
            if (j < pLen)
            {
                repeated = false;
            }
            if (repeated)
            {
                i += pLen - 1;
                repeated = false;
            }
            else
            {
                res += password.charAt(i);
            }

        }
        return res;
    }

    public static void main(String[] args) throws IOException
    {
//        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(
//                "E:\\download\\cracklib-words-20080507\\cracklib-words")));
//        String line = br.readLine();
//        StringBuilder builder = new StringBuilder();
//        int i = 0;
//        while (StringUtils.isNotBlank(line))
//        {
//            int score = testStrength(line, "");
//            if (score >= 60)
//            {
//                ++i;
//                // System.out.println(line);
//                builder.append(line + ",@");
//            }
//            line = br.readLine();
//        }
//        System.out.println(i);
//        System.out.println(builder.toString());
        // System.out.println(testStrength("1234567890", ""));
        System.out.println(testStrength("123dDfghij", ""));
    }
}
