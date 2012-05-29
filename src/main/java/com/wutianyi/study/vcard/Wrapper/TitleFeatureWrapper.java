package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.TitleFeature;

public class TitleFeatureWrapper implements WrapperInterface
{
    TitleFeature titleFeature;
    
    public TitleFeatureWrapper(TitleFeature titleFeature)
    {
        this.titleFeature = titleFeature;
    }

    @Override
    public String getValue()
    {
        return titleFeature.getTitle();
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

	@Override
	public int getParameterSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}
