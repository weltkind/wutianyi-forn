package com.wutianyi.study.vcard.Wrapper;

import info.ineighborhood.cardme.vcard.features.NoteFeature;

public class NoteFeatureWrapper implements WrapperInterface
{

    NoteFeature noteFeature;

    public NoteFeatureWrapper(NoteFeature noteFeature)
    {
        this.noteFeature = noteFeature;
    }

    @Override
    public String getValue()
    {
        return noteFeature.getNote();
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
