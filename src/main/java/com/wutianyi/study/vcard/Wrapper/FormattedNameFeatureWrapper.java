package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.FormattedNameFeature;

public class FormattedNameFeatureWrapper implements WrapperInterface
{
    FormattedNameFeature formattedNameFeature;

    public FormattedNameFeatureWrapper(FormattedNameFeature formattedNameFeature)
    {
        this.formattedNameFeature = formattedNameFeature;
    }

    @Override
    public String getValue()
    {
        return formattedNameFeature.getFormattedName();
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
