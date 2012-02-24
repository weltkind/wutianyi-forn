package com.wutianyi.study.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class MultipleThreadMain
{
    public static void main(String[] args)
    {
        final ObjectMapper objectMapper = new ObjectMapper();
        
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        for(int i = 0; i < 100; i ++)
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("test_" + i + "_1", "t_" + i + "_1");
            map.put("test_" + i + "_2", "t_" + i + "_2");
            list.add(map);
        }
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(final Map<String, Object> map : list)
        {
            executorService.submit(new Runnable()
            {
                
                @Override
                public void run()
                {
                    try
                    {
                        System.out.println(objectMapper.writeValueAsString(map));
                    }
                    catch (JsonGenerationException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (JsonMappingException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.shutdown();
    }

}
