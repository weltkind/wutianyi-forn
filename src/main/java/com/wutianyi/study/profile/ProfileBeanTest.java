package com.wutianyi.study.profile;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileBeanTest
{

    private ProfileBean profileBean;

    private ProfileBean pb;
    @Before
    public void setUp()
    {
        profileBean = new ProfileBean();
        pb = new ProfileBean(273);
    }

    @Test
    public void testName()
    {
        profileBean.setName();
        assertTrue(profileBean.showName());
        assertFalse(profileBean.showAddress());
        assertFalse(profileBean.showBday());
        assertFalse(profileBean.showEmail());
        assertFalse(profileBean.showNote());
        assertFalse(profileBean.showOrg());
        assertFalse(profileBean.showQQ());
        assertFalse(profileBean.showTagList());
        assertFalse(profileBean.showTel());
        assertFalse(profileBean.showUrl());
    }
    @Test
    public void compositTest() {
        profileBean.setName();
        profileBean.setEmail();
        profileBean.setUrl();
        profileBean.setValue(12, true);
        profileBean.setValue(20, false);
        profileBean.setValue(15, true);
        assertTrue(profileBean.showName());
        assertFalse(profileBean.showAddress());
        assertFalse(profileBean.showBday());
        assertTrue(profileBean.showEmail());
        assertFalse(profileBean.showNote());
        assertFalse(profileBean.showOrg());
        assertFalse(profileBean.showQQ());
        assertFalse(profileBean.showTagList());
        assertFalse(profileBean.showTel());
        assertTrue(profileBean.showUrl());
        assertTrue(profileBean.getValue(12));
        assertTrue(profileBean.getValue(15));
        assertFalse(profileBean.getValue(20));
        assertFalse(profileBean.getValue(31));
    }
    
    @Test
    public void initialTest() {
        assertTrue(pb.showName());
        assertFalse(pb.showAddress());
        assertFalse(pb.showBday());
        assertTrue(pb.showEmail());
        assertFalse(pb.showNote());
        assertFalse(pb.showOrg());
        assertFalse(pb.showQQ());
        assertFalse(pb.showTagList());
        assertFalse(pb.showTel());
        assertTrue(pb.showUrl());
    }
    
    @Test
    public void positionTest() {
    }
}
