package com.wutianyi.study.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class MapMain
{
    public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
        map.put("int", 1);
        map.put("string", "string");
        map.put("boolean", true);
        String value = objectMapper.writeValueAsString(map);
        System.out.println(value);
        ConcurrentHashMap<String, Object> r = objectMapper.readValue(value, new TypeReference<ConcurrentHashMap<String, Object>>()
        {
        });
        for(Entry<String, Object> o : r.entrySet())
        {
            System.out.println(o.getValue().getClass());
        }
    }
}
