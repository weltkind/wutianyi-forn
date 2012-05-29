package com.wutianyi.study.vcard.Wrapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.IteratorUtils;

import info.ineighborhood.cardme.vcard.VCardImpl;
import info.ineighborhood.cardme.vcard.VCardType;
import info.ineighborhood.cardme.vcard.features.AddressFeature;
import info.ineighborhood.cardme.vcard.features.EmailFeature;
import info.ineighborhood.cardme.vcard.features.ExtendedFeature;
import info.ineighborhood.cardme.vcard.features.NoteFeature;
import info.ineighborhood.cardme.vcard.features.TelephoneFeature;
import info.ineighborhood.cardme.vcard.features.URLFeature;

public class VcardWrapper
{
    VCardImpl vcard;

    Map<VCardType, WrapperInterface[]> vcardWrappers;

    public VcardWrapper(VCardImpl vcard)
    {
        this.vcard = vcard;
        vcardWrappers = new HashMap<VCardType, WrapperInterface[]>();

        vcardWrappers.put(VCardType.N, new WrapperInterface[]
        { new NameFeatureWrapper(vcard.getName()) });

        if (null != vcard.getFormattedName())
        {
            vcardWrappers.put(VCardType.FN, new WrapperInterface[]
            { new FormattedNameFeatureWrapper(vcard.getFormattedName()) });

        }

        if (vcard.hasAddresses())
        {
            Object[] afs = IteratorUtils.toArray(vcard.getAddresses());
            WrapperInterface[] wrappers = new WrapperInterface[afs.length];
            for (int i = 0; i < afs.length; i++)
            {
                if (afs[i] instanceof AddressFeature)
                {
                    wrappers[i] = new AddressFeatureWrapper((AddressFeature) afs[i]);
                }
            }
            vcardWrappers.put(VCardType.ADR, wrappers);
        }

        if (vcard.hasBirthday())
        {
            vcardWrappers.put(VCardType.BDAY, new WrapperInterface[]
            { new BirthdayFeatureWrapper(vcard.getBirthDay()) });
        }

        if (vcard.hasEmails())
        {
            Object[] afs = IteratorUtils.toArray(vcard.getEmails());
            WrapperInterface[] wrappers = new WrapperInterface[afs.length];
            for (int i = 0; i < afs.length; i++)
            {
                if (afs[i] instanceof EmailFeature)
                {
                    wrappers[i] = new EmailFeatureWrapper((EmailFeature) afs[i]);
                }
            }
            vcardWrappers.put(VCardType.EMAIL, wrappers);
        }

        if (vcard.hasExtendedTypes())
        {
            Object[] afs = IteratorUtils.toArray(vcard.getExtendedTypes());
            WrapperInterface[] wrappers = new WrapperInterface[afs.length];
            for (int i = 0; i < afs.length; i++)
            {
                if (afs[i] instanceof ExtendedFeatureWrapper)
                {
                    wrappers[i] = new ExtendedFeatureWrapper((ExtendedFeature) afs[i]);
                }
            }
            vcardWrappers.put(VCardType.XTENDED, wrappers);
        }

        if (vcard.hasNotes())
        {
            Object[] afs = IteratorUtils.toArray(vcard.getNotes());
            WrapperInterface[] wrappers = new WrapperInterface[afs.length];
            for (int i = 0; i < afs.length; i++)
            {
                wrappers[i] = new NoteFeatureWrapper((NoteFeature) afs[i]);
            }
            vcardWrappers.put(VCardType.NOTE, wrappers);
        }

        if (vcard.hasOrganizations())
        {
            vcardWrappers.put(VCardType.ORG, new WrapperInterface[]
            { new OrganizationFeatureWrapper(vcard.getOrganizations()) });
        }

        if (vcard.hasTelephoneNumbers())
        {
            Object[] afs = IteratorUtils.toArray(vcard.getTelephoneNumbers());
            WrapperInterface[] wrappers = new WrapperInterface[afs.length];
            for (int i = 0; i < afs.length; i++)
            {
                wrappers[i] = new TelephoneFeatureWrapper((TelephoneFeature) afs[i]);
            }
            vcardWrappers.put(VCardType.TEL, wrappers);
        }

        if (vcard.hasTitle())
        {
            vcardWrappers.put(VCardType.TITLE, new WrapperInterface[]
            { new TitleFeatureWrapper(vcard.getTitle()) });
        }

        if (vcard.hasURLs())
        {
            Object[] afs = IteratorUtils.toArray(vcard.getURLs());
            WrapperInterface[] wrappers = new WrapperInterface[afs.length];
            for (int i = 0; i < afs.length; i++)
            {
                wrappers[i] = new URLFeatureWrapper((URLFeature) afs[i]);
            }
            vcardWrappers.put(VCardType.URL, wrappers);
        }
    }

    public WrapperInterface[] getValues(VCardType vcardType)
    {
        return vcardWrappers.get(vcardType);
    }

    public WrapperInterface[] getValues(String property)
    {
        try
        {
            vcardWrappers.get(VCardType.valueOf(property));
        }
        catch (Exception e)
        {

        }
        return null;
    }
}
