package com.wutianyi.study.vcard.Wrapper;

import java.util.Iterator;

import info.ineighborhood.cardme.vcard.features.NameFeature;

public class NameFeatureWrapper implements WrapperInterface
{
    NameFeature nameFeature;

    public NameFeatureWrapper(NameFeature nameFeature)
    {
        this.nameFeature = nameFeature;
    }

    @Override
    public String getValue()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(nameFeature.getFamilyName());
        builder.append(';');
        builder.append(nameFeature.getGivenName());
        builder.append(';');
        for (Iterator<String> itr = nameFeature.getAdditionalNames(); itr.hasNext();)
        {
            builder.append(itr.next());
        }
        builder.append(';');
        for (Iterator<String> itr = nameFeature.getHonorificPrefixes(); itr.hasNext();)
        {
            builder.append(itr.next());
        }
        builder.append(';');
        for (Iterator<String> itr = nameFeature.getHonorificSuffixes(); itr.hasNext();)
        {
            builder.append(itr.next());
        }
        return builder.toString();
    }

    @Override
    public String getType()
    {
        return null;
    }

    @Override
    public boolean containParameter(String[] parameter)
    {
        // TODO Auto-generated method stub
        return false;
    }

	@Override
	public int getParameterSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
