package com.wutianyi.study.vcard.Wrapper;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;

import info.ineighborhood.cardme.vcard.features.OrganizationFeature;

public class OrganizationFeatureWrapper implements WrapperInterface
{
    OrganizationFeature organizationFeature;

    public OrganizationFeatureWrapper(OrganizationFeature organizationFeature)
    {
        this.organizationFeature = organizationFeature;
    }

    @Override
    public String getValue()
    {
        String value = "";

        for (Iterator<String> itr = organizationFeature.getOrganizations(); itr.hasNext();)
        {
            value += itr.next() + ";";
        }

        if (StringUtils.isBlank(value))
        {
            return ";";
        }

        return value.substring(0, value.length() - 1);
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
