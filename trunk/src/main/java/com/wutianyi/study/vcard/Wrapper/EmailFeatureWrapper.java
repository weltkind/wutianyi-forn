package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.EmailFeature;
import info.ineighborhood.cardme.vcard.types.parameters.EmailParameterType;

public class EmailFeatureWrapper implements WrapperInterface
{

    EmailFeature emailFeature;

    public EmailFeatureWrapper(EmailFeature emailFeature)
    {
        this.emailFeature = emailFeature;
    }

    @Override
    public String getValue()
    {
        return emailFeature.getEmail();
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
        if (null == parameter)
        {
            return false;
        }

        try
        {
            boolean r = true;
            for (String p : parameter)
            {
                r &= emailFeature.containsEmailParameterType(EmailParameterType.valueOf(p));
            }
            return r;
        }
        catch (Exception e)
        {

        }

        return false;
    }

}
