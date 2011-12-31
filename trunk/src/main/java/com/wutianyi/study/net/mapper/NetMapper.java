package com.wutianyi.study.net.mapper;

import java.util.List;

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
    
    @Select("select toid from wordhidden where fromid in (${key})")
    public List<Integer> getHiddenIdsByWord(String key);
    
    @Select("select fromid from hiddenurl where toid in (${key})")
    public List<Integer> getHiddenIdsByUrl(String key);
    
    @Select("select strenght from wordhidden where fromid=#{wordId} and toid=#{hiddenId}")
    public Float getStrenghtByWordId(int wordId, int hiddenId);
    
    @Select("select strenght from hiddenurl where fromid=#{hiddenId} and toid=#{urlId}")
    public Float getStrenghtByUrlId(int urlId, int hiddenId);
}
