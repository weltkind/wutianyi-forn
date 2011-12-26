package com.wutianyi.study.net.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.wutianyi.study.net.dataobject.HiddenNode;
import com.wutianyi.study.net.dataobject.HiddenUrl;
import com.wutianyi.study.net.dataobject.WordHidden;

public interface NetMapper
{

    @Insert("INSERT INTO hiddennode(create_key) VALUES(#{createKey})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    public int insertHiddenNode(HiddenNode node);
    
    @Select("select id from hiddennode where create_key = #{createKey}")
    public Integer getHiddenNodeId(String createKey);
    
    @Insert("insert into wordhidden(fromid, toid, strength) values(#{fromId},#{toId},#{strength})")
    public int insertWordHidden(WordHidden hidden);
    
    @Insert("insert into hiddenurl(fromid, toid, strength) values(#{fromId},#{toId},#{strength})")
    public int insertHiddenUrl(HiddenUrl url);
}
