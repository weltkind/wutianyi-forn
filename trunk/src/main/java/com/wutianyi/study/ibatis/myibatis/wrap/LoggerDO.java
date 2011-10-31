package com.wutianyi.study.ibatis.myibatis.wrap;

import java.util.Date;

public class LoggerDO
{
    private int id;
    private Date gmt_create;
    private Date gmt_modified;
    private String old_value;
    private String new_value;
    private String operation_type;
    private String operator;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
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

    public String getOld_value()
    {
        return old_value;
    }

    public void setOld_value(String old_value)
    {
        this.old_value = old_value;
    }

    public String getNew_value()
    {
        return new_value;
    }

    public void setNew_value(String new_value)
    {
        this.new_value = new_value;
    }

    public String getOperation_type()
    {
        return operation_type;
    }

    public void setOperation_type(String operation_type)
    {
        this.operation_type = operation_type;
    }

    public String getOperator()
    {
        return operator;
    }

    public void setOperator(String operator)
    {
        this.operator = operator;
    }
}
