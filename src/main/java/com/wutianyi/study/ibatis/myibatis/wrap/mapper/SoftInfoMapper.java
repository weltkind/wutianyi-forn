package com.wutianyi.study.ibatis.myibatis.wrap.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.wutianyi.study.ibatis.myibatis.wrap.dataobject.SoftInfoDTO;

public interface SoftInfoMapper
{
    @Select("SELECT s.id,machine_name,platform,g_f,s_description,product_id,resource_id,s.gmt_create,s.gmt_modified,p_description,p.name,r_description FROM soft_info s LEFT JOIN product_info p ON s.product_id = p.id LEFT JOIN resource_info r ON s.resource_id = r.id")
    public List<SoftInfoDTO> listAllSoftInfo();

    @Insert("INSERT INTO product_info(NAME,p_description,gmt_create,gmt_modified) VALUES({0},{1},now(),now())")
    public int addProductInfo(Object[] args);

    @Insert("INSERT INTO resource_info(url,r_description,gmt_create,gmt_modified) VALUES({0},{1},now(),now())")
    public int addResourceInfo(Object[] args);

    @Insert("INSERT INTO soft_info(product_id,resource_id,machine_name,platform,g_f,s_description,gmt_create,gmt_modified) VALUES({0},{1},{2},{3},{4},{5},now(),now())")
    public int addSoftInfo(Object[] args);
}
