package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.LabelFeature;
import info.ineighborhood.cardme.vcard.types.parameters.LabelParameterType;

public class LabelFeatureWrapper implements WrapperInterface
{

    LabelFeature labelFeature;

    public LabelFeatureWrapper(LabelFeature labelFeature)
    {
        this.labelFeature = labelFeature;
    }

    @Override
    public String getValue()
    {
        return labelFeature.getLabel();
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
                r &= labelFeature.containsLabelParameterType(LabelParameterType.valueOf(p));
            }
            return r;
        }
        catch (Exception e)
        {

        }
        return false;
    }

	@Override
	public int getParameterSize() {
		return labelFeature.getExtendedLabelParameterSize();
	}

}
