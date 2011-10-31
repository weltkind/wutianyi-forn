package com.wutianyi.study.ibatis.myibatis.wrap.dataobject;

import java.util.Date;

/**
 * 软件下载信息实体对象 为了与dbclient进行整合所以属性与字段名称完全一致
 * 
 * @author hanjiewu
 * 
 */
public class SoftInfoDTO
{
    /**
     * machine_name,platform,g_f三个为唯一键，用于标示实体
     */
    private int id;
    /**
     * 
     */
    private int product_id;
    private int resource_id;
    private String machine_name;
    private String platform;
    private Date gmt_create;
    private Date gmt_modified;
    private String g_f;
    /**
     * 产品名称
     */
    private String name;
    private String p_description;
    private String r_description;
    private String s_description;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getProduct_id()
    {
        return product_id;
    }

    public void setProduct_id(int product_id)
    {
        this.product_id = product_id;
    }

    public int getResource_id()
    {
        return resource_id;
    }

    public void setResource_id(int resource_id)
    {
        this.resource_id = resource_id;
    }

    public String getMachine_name()
    {
        return machine_name;
    }

    public void setMachine_name(String machine_name)
    {
        this.machine_name = machine_name;
    }

    public String getPlatform()
    {
        return platform;
    }

    public void setPlatform(String platform)
    {
        this.platform = platform;
    }

    public Date getGmt_create()
    {
        return gmt_create;
    }

    public void setGmt_create(Date gmt_create)
    {
        this.gmt_create = gmt_create;
    }

    public Date getGmt_modified()
    {
        return gmt_modified;
    }

    public void setGmt_modified(Date gmt_modified)
    {
        this.gmt_modified = gmt_modified;
    }

    public String getG_f()
    {
        return g_f;
    }

    public void setG_f(String g_f)
    {
        this.g_f = g_f;
    }

    public String getP_description()
    {
        return p_description;
    }

    public void setP_description(String p_description)
    {
        this.p_description = p_description;
    }

    public String getR_description()
    {
        return r_description;
    }

    public void setR_description(String r_description)
    {
        this.r_description = r_description;
    }

    public String getS_description()
    {
        return s_description;
    }

    public void setS_description(String s_description)
    {
        this.s_description = s_description;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
