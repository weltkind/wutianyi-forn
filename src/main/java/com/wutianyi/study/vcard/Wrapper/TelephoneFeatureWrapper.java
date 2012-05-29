package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.TelephoneFeature;
import info.ineighborhood.cardme.vcard.types.parameters.TelephoneParameterType;

public class TelephoneFeatureWrapper implements WrapperInterface
{
    TelephoneFeature telephoneFeature;

    public TelephoneFeatureWrapper(TelephoneFeature telephoneFeature)
    {
        this.telephoneFeature = telephoneFeature;
    }

    @Override
    public String getValue()
    {
        return telephoneFeature.getTelephone();
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
                r &= telephoneFeature.containsTelephoneParameterType(TelephoneParameterType.valueOf(p));
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
		return telephoneFeature.getTelephoneParameterSize();
	}

}
