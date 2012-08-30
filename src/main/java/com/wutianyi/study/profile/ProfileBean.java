package com.wutianyi.study.profile;

public class ProfileBean
{
    private static final int NAME = 1;
    private static final int TEL = 1 << 1;
    private static final int QQ = 1 << 2;
    private static final int TAGLIST = 1 << 3;
    private static final int EMAIL = 1 << 4;
    private static final int ADDRESS = 1 << 5;
    private static final int BDAY = 1 << 6;
    private static final int ORG = 1 << 7;
    private static final int URL = 1 << 8;
    private static final int NOTE = 1 << 9;

    private static final int MAX_POSITION = 31;

    private int value;

    public ProfileBean(int value)
    {
        this.value = value;
    }

    public ProfileBean()
    {

    }

    public void setName()
    {
        value = value | NAME;
    }

    public boolean showName()
    {
        return ((value & NAME) != 0);
    }

    public void setTel()
    {
        value = value | TEL;
    }

    public boolean showTel()
    {
        return ((value & TEL) !=  0);
    }

    public void setQQ()
    {
        value = value | QQ;
    }

    public boolean showQQ()
    {
        return ((value & QQ) !=  0);
    }

    public void setTaglist()
    {
        value = value | TAGLIST;
    }

    public boolean showTagList()
    {
        return ((value & TAGLIST) !=  0);
    }

    public void setEmail()
    {
        value = value | EMAIL;
    }

    public boolean showEmail()
    {
        return ((value & EMAIL) !=  0);
    }

    public void setAddress()
    {
        value = value | ADDRESS;
    }

    public boolean showAddress()
    {
        return ((value & ADDRESS) !=  0);
    }

    public void setBday()
    {
        value = value | BDAY;
    }

    public boolean showBday()
    {
        return ((value & BDAY) !=  0);
    }

    public void setOrg()
    {
        value = value | ORG;
    }

    public boolean showOrg()
    {
        return ((value & ORG) !=  0);
    }

    public void setUrl()
    {
        value = value | URL;
    }

    public boolean showUrl()
    {
        return ((value & URL) !=  0);
    }

    public void setNote()
    {
        value = value | NOTE;
    }

    public boolean showNote()
    {
        return ((value & NOTE) !=  0);
    }

    public void setValue(int position, boolean v)
    {
        if (position > MAX_POSITION || position < 0)
        {
            throw new IllegalArgumentException(
                    "[ProfileBean invalid position] The max position is 31 and the min position is 0! But the current position is "
                            + position);
        }

        if (v)
        {
            value = value | (1 << position);
        }
        else
        {
            value = value & ~(1 << position);
        }
    }

    public boolean getValue(int position)
    {
        if (position > MAX_POSITION || position < 0)
        {
            throw new IllegalArgumentException(
                    "[ProfileBean invalid position] The max position is 31 and the min position is 0! But the current position is "
                            + position);
        }
        return ((value & (1 << position)) !=  0);
    }

    public int getValue()
    {
        return value;
    }
    
    public static void main(String[] args)
    {
        ProfileBean pb = new ProfileBean();
        pb.setName();
        pb.setTel();
        pb.setQQ();
        pb.setBday();
        System.out.println(Integer.toBinaryString(pb.getValue()));
    }
}
