package com.wutianyi.study.vcard.Wrapper;

import java.text.SimpleDateFormat;

import info.ineighborhood.cardme.vcard.features.BirthdayFeature;

public class BirthdayFeatureWrapper implements WrapperInterface
{

    BirthdayFeature birthdayFeature;

    public BirthdayFeatureWrapper(BirthdayFeature birthdayFeature)
    {
        this.birthdayFeature = birthdayFeature;
    }

    @Override
    public String getValue()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(birthdayFeature.getBirthday());
    }

    @Override
    public String getType()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean containParameter(String[] parameter)
    {
        // TODO Auto-generated method stub
        return false;
    }

}
