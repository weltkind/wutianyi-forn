package com.wutianyi.study.ibatis.myibatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.wutianyi.study.ibatis.mapper.deep.LoggerDO;

public interface LoggerMapper
{
    @Select("select id, gmt_create as gmtCreate, gmt_Modified as gmtModified, old_value as oldValue, new_value as newValue, operation_type as operationType, operator from logger where id={0}")
    public List<LoggerDO> listAllLogger(int id);

    public LoggerDO fetchLogger(@Param("id") int id);
}
