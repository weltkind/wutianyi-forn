package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.URLFeature;

public class URLFeatureWrapper implements WrapperInterface
{
    URLFeature urlFeature;

    public URLFeatureWrapper(URLFeature urlFeature)
    {
        this.urlFeature = urlFeature;
    }

    @Override
    public String getValue()
    {
        return urlFeature.getURL().toExternalForm();
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
