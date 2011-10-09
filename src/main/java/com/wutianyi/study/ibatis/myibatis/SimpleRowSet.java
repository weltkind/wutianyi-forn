package com.wutianyi.study.ibatis.myibatis;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * 本类实现CachedRowSet的功能子集
 * 
 * @author philgong
 * 
 */
public final class SimpleRowSet implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -2189590049008432489L;

    private static final String SQL_NULL = "null";

    private final String[][] data;

    private final HashMap<String, Integer> columnNameMap = new HashMap<String, Integer>();

    private int pos = -1;

    private Calendar fastDateCal;

    public SimpleRowSet()
    {
        fastDateCal = null;
        this.data = null;

    }

    public SimpleRowSet(String[] columnNames, String[][] data)
    {
        fastDateCal = null;
        this.data = data;
        for (int i = 0, n = columnNames.length; i < n; i++)
        {
            columnNameMap.put(columnNames[i], i + 1);
        }
    }

    public void afterLast()
    {
        if (data == null)
        {
            return;
        }
        pos = data.length;
    }

    public void beforeFirst()
    {
        pos = -1;
    }

    private synchronized Date fastDateCreate(Calendar cal, int year, int month, int day, int hour, int minute,
            int seconds)
    {
        if (cal == null)
        {
            if (fastDateCal == null)
            {
                fastDateCal = new GregorianCalendar();
                fastDateCal.setTimeZone(TimeZone.getDefault());
            }
            cal = fastDateCal;
        }
        cal.clear();
        cal.set(year + 1900, month, day, hour, minute, seconds);
        long dateAsMillis = 0L;
        try
        {
            dateAsMillis = cal.getTimeInMillis();
        }
        catch (IllegalAccessError iae)
        {
            dateAsMillis = cal.getTime().getTime();
        }
        return new Date(dateAsMillis);
    }

    public boolean first()
    {
        if (data == null || data.length == 0)
        {
            return false;
        }
        pos = 0;
        return true;
    }

    public boolean getBoolean(int columnIndex) throws SQLException
    {
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return false;
            }
            int c = Character.toLowerCase(row[columnIndex - 1].charAt(0));
            return c == 49 || c == 121 || c == 116;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
    }

    public boolean getBoolean(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getBoolean(columnIndex);
    }

    public Date getDate(int columnIndex) throws SQLException
    {
        return getDate(columnIndex, null);
    }

    public Date getDate(int columnIndex, Calendar cal) throws SQLException
    {
        String timestampValue = "";
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return null;
            }
            timestampValue = row[columnIndex - 1];
            try
            {
                int length = timestampValue.length();
                if (length > 0
                        && timestampValue.charAt(0) == '0'
                        && (timestampValue.equals("0000-00-00") || timestampValue.equals("0000-00-00 00:00:00")
                                || timestampValue.equals("00000000000000") || timestampValue.equals("0")))
                {
                    return null;
                }
                switch (length)
                {
                case 21:
                case 19: // '\023'
                {
                    int year = Integer.parseInt(timestampValue.substring(0, 4));
                    int month = Integer.parseInt(timestampValue.substring(5, 7));
                    int day = Integer.parseInt(timestampValue.substring(8, 10));
                    int hour = Integer.parseInt(timestampValue.substring(11, 13));
                    int minutes = Integer.parseInt(timestampValue.substring(14, 16));
                    int seconds = Integer.parseInt(timestampValue.substring(17, 19));
                    return fastDateCreate(null, year - 1900, month - 1, day, hour, minutes, seconds);
                }

                case 14: // '\016'
                {
                    int year = Integer.parseInt(timestampValue.substring(0, 4));
                    int month = Integer.parseInt(timestampValue.substring(4, 6));
                    int day = Integer.parseInt(timestampValue.substring(6, 8));
                    int hour = Integer.parseInt(timestampValue.substring(8, 10));
                    int minutes = Integer.parseInt(timestampValue.substring(10, 12));
                    int seconds = Integer.parseInt(timestampValue.substring(12, 14));
                    return fastDateCreate(null, year - 1900, month - 1, day, hour, minutes, seconds);
                }

                case 12: // '\f'
                {
                    int year = Integer.parseInt(timestampValue.substring(0, 2));
                    if (year <= 69)
                    {
                        year += 100;
                    }
                    int month = Integer.parseInt(timestampValue.substring(2, 4));
                    int day = Integer.parseInt(timestampValue.substring(4, 6));
                    int hour = Integer.parseInt(timestampValue.substring(6, 8));
                    int minutes = Integer.parseInt(timestampValue.substring(8, 10));
                    int seconds = Integer.parseInt(timestampValue.substring(10, 12));
                    return fastDateCreate(null, year, month - 1, day, hour, minutes, seconds);
                }

                case 10: // '\n'
                {
                    int year;
                    int month;
                    int day;
                    int hour;
                    int minutes;
                    if (timestampValue.indexOf("-") != -1)
                    {
                        year = Integer.parseInt(timestampValue.substring(0, 4)) - 1900;
                        month = Integer.parseInt(timestampValue.substring(5, 7));
                        day = Integer.parseInt(timestampValue.substring(8, 10));
                        hour = 0;
                        minutes = 0;
                    }
                    else
                    {
                        year = Integer.parseInt(timestampValue.substring(0, 2));
                        if (year <= 69)
                        {
                            year += 100;
                        }
                        month = Integer.parseInt(timestampValue.substring(2, 4));
                        day = Integer.parseInt(timestampValue.substring(4, 6));
                        hour = Integer.parseInt(timestampValue.substring(6, 8));
                        minutes = Integer.parseInt(timestampValue.substring(8, 10));
                    }
                    return fastDateCreate(null, year, month - 1, day, hour, minutes, 0);
                }

                case 8: // '\b'
                {
                    int year = Integer.parseInt(timestampValue.substring(0, 4));
                    int month = Integer.parseInt(timestampValue.substring(4, 6));
                    int day = Integer.parseInt(timestampValue.substring(6, 8));
                    return fastDateCreate(null, year - 1900, month - 1, day, 0, 0, 0);
                }

                case 6: // '\006'
                {
                    int year = Integer.parseInt(timestampValue.substring(0, 2));
                    if (year <= 69)
                    {
                        year += 100;
                    }
                    int month = Integer.parseInt(timestampValue.substring(2, 4));
                    int day = Integer.parseInt(timestampValue.substring(4, 6));
                    return fastDateCreate(null, year, month - 1, day, 0, 0, 0);
                }

                case 4: // '\004'
                {
                    int year = Integer.parseInt(timestampValue.substring(0, 2));
                    if (year <= 69)
                    {
                        year += 100;
                    }
                    int month = Integer.parseInt(timestampValue.substring(2, 4));
                    return fastDateCreate(null, year, month - 1, 1, 0, 0, 0);
                }

                case 2: // '\002'
                {
                    int year = Integer.parseInt(timestampValue.substring(0, 2));
                    if (year <= 69)
                    {
                        year += 100;
                    }
                    return fastDateCreate(null, year, 0, 1, 0, 0, 0);
                }
                case 3: // '\003'
                case 5: // '\005'
                case 7: // '\007'
                case 9: // '\t'
                case 11: // '\013'
                case 13: // '\r'
                case 15: // '\017'
                case 16: // '\020'
                case 17: // '\021'
                case 18: // '\022'
                default:
                    throw new SQLException("Bad format for Date '" + timestampValue + "' in column " + columnIndex
                            + "(" + timestampValue + ").", "S1009");
                }
            }
            catch (Exception e)
            {
                throw new SQLException("Cannot convert value '" + timestampValue + "' from column " + columnIndex + "("
                        + timestampValue + " ) to DATE.", "S1009");
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
    }

    public Date getDate(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getDate(columnIndex);
    }

    public Date getDate(String columnName, Calendar cal) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getDate(columnIndex, cal);
    }

    public double getDouble(int columnIndex) throws SQLException
    {
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return 0;
            }
            return Double.parseDouble(row[columnIndex - 1]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
        catch (NumberFormatException e)
        {
            throw new SQLException("getDouble Failed on row (" + pos + ") in column (" + columnIndex + "): "
                    + row[columnIndex]);
        }
    }

    public double getDouble(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getDouble(columnIndex);
    }

    public float getFloat(int columnIndex) throws SQLException
    {
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return 0;
            }
            return Float.parseFloat(row[columnIndex - 1]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
        catch (NumberFormatException e)
        {
            throw new SQLException("getFloat Failed on row (" + pos + ") in column (" + columnIndex + "): "
                    + row[columnIndex]);
        }
    }

    public float getFloat(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getFloat(columnIndex);
    }

    public int getInt(int columnIndex) throws SQLException
    {
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return 0;
            }
            return Integer.parseInt(row[columnIndex - 1]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
        catch (NumberFormatException e)
        {
            throw new SQLException("getInt Failed on row (" + pos + ") in column (" + columnIndex + "): "
                    + row[columnIndex]);
        }
    }

    public int getInt(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getInt(columnIndex);
    }

    public long getLong(int columnIndex) throws SQLException
    {
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return 0;
            }
            return Long.parseLong(row[columnIndex - 1]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
        catch (NumberFormatException e)
        {
            throw new SQLException("getLong Failed on row (" + pos + ") in column (" + columnIndex + "): "
                    + row[columnIndex]);
        }
    }

    public long getLong(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getLong(columnIndex);
    }

    public short getShort(int columnIndex) throws SQLException
    {
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return 0;
            }
            return Short.parseShort(row[columnIndex - 1]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
        catch (NumberFormatException e)
        {
            throw new SQLException("getShort Failed on row (" + pos + ") in column (" + columnIndex + "): "
                    + row[columnIndex]);
        }
    }

    public short getShort(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getShort(columnIndex);
    }

    public String getString(int columnIndex) throws SQLException
    {
        String[] row = null;
        try
        {
            row = data[pos];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid cursor position: " + pos);
        }
        try
        {
            if (row[columnIndex - 1].equals(SQL_NULL))
            {
                return null;
            }
            return row[columnIndex - 1];
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            throw new SQLException("Invalid column index: " + columnIndex);
        }
    }

    public String getString(String columnName) throws SQLException
    {
        Integer columnIndex = columnNameMap.get(columnName);
        if (columnIndex == null)
        {
            throw new SQLException("Invalid column name: " + columnName);
        }
        return getString(columnIndex);
    }

    public boolean isAfterLast()
    {
        if (data == null)
        {
            return false;
        }
        return pos >= data.length;
    }

    public boolean isBeforeFirst()
    {
        if (data == null || data.length == 0)
        {
            return false;
        }
        return pos < 0;
    }

    public boolean isFirst()
    {
        if (data == null || data.length == 0)
        {
            return false;
        }
        return pos == 0;
    }

    public boolean isLast()
    {
        if (data == null || data.length == 0)
        {
            return false;
        }
        return pos == data.length - 1;
    }

    public boolean last()
    {
        if (data == null || data.length == 0)
        {
            return false;
        }
        pos = data.length - 1;
        return true;
    }

    public boolean next()
    {
        if (data == null || data.length == 0)
        {
            return false;
        }
        pos++;
        return pos >= 0 && pos < data.length;
    }

    public boolean previous()
    {
        if (data == null || data.length == 0)
        {
            return false;
        }
        pos--;
        return pos >= 0 && pos < data.length;
    }

    public int size()
    {
        if (data == null)
        {
            return 0;
        }
        return data.length;
    }
}