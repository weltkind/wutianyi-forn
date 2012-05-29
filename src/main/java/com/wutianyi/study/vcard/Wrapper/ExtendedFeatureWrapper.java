package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.ExtendedFeature;

public class ExtendedFeatureWrapper implements WrapperInterface
{
    ExtendedFeature extendedFeature;

    public ExtendedFeatureWrapper(ExtendedFeature extendedFeature)
    {
        this.extendedFeature = extendedFeature;
    }

    @Override
    public String getValue()
    {
        return extendedFeature.getExtensionData();
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
