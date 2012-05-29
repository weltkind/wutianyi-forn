package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.AddressFeature;
import info.ineighborhood.cardme.vcard.types.parameters.AddressParameterType;

public class AddressFeatureWrapper implements WrapperInterface
{

    AddressFeature addressFeature;

    public AddressFeatureWrapper(AddressFeature addressFeature)
    {
        this.addressFeature = addressFeature;
    }

    @Override
    public String getValue()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(addressFeature.getPostOfficeBox());
        builder.append(';');
        builder.append(addressFeature.getExtendedAddress());
        builder.append(';');
        builder.append(addressFeature.getStreetAddress());
        builder.append(';');
        builder.append(addressFeature.getLocality());
        builder.append(';');
        builder.append(addressFeature.getRegion());
        builder.append(';');
        builder.append(addressFeature.getPostalCode());
        builder.append(';');
        builder.append(addressFeature.getCountryName());
        return builder.toString();
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
                r &= addressFeature.containsAddressParameterType(AddressParameterType.valueOf(p));
            }
            return r;
        }
        catch (Exception e)
        {

        }

        return false;
    }

}
