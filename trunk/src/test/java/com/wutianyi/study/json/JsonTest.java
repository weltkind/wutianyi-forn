package com.wutianyi.study.json;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JsonTest
{
    private JsonGenerator jsonGenerator = null;
    private ObjectMapper objectMapper = null;
    private AccountBean bean = null;

    @Before
    public void init()
    {
        bean = new AccountBean();
        bean.setAddress("china-Guangzhou");
        bean.setEmail("whj86728@126.com");
        bean.setId(1);
        bean.setName("tianyi");
        objectMapper = new ObjectMapper();
        try
        {
            jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(System.out, JsonEncoding.UTF8);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void writeEntityJson()
    {
        System.out.println("jsonGenerator");
        try
        {
            jsonGenerator.writeObject(bean);
            System.out.println();
            System.out.println("ObjectMapper");
            objectMapper.writeValue(System.out, bean);
        }
        catch (JsonProcessingException e)
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

    @Test
    public void readListObject() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("result.txt")));
        String line = reader.readLine();
        ImageHelpBean[] beans = objectMapper.readValue(line, ImageHelpBean[].class);
        System.out.println(beans.length);
        Map<Integer, ImageHelpBean[]> paginations = new HashMap<Integer, ImageHelpBean[]>();
        int remainder = beans.length % 2;

        int page = beans.length / 2;
        page = (remainder != 0) ? page + 1 : page;

        for (int i = 0; i < page; i++)
        {
            int position = i * 2;
            int len = (beans.length - position) >= 2 ? 2 : (beans.length - position);
            System.out.println("Position: " + position + " len: " + len);
            ImageHelpBean[] dest = new ImageHelpBean[len];
            System.arraycopy(beans, position, dest, 0, len);
            paginations.put(i + 1, dest);
        }
        System.out.println(paginations.size());
        for (Entry<Integer, ImageHelpBean[]> entry : paginations.entrySet())
        {
            System.out.println("key: " + entry.getKey() + " valueSize: " + entry.getValue().length);
        }

        reader.close();
    }

    @After
    public void destory()
    {

        try
        {
            if (null != jsonGenerator)
            {
                jsonGenerator.flush();
            }
            if (!jsonGenerator.isClosed())
            {
                jsonGenerator.close();
            }
            jsonGenerator = null;
            objectMapper = null;
            bean = null;
            System.gc();
        }
        catch (IOException e)
        {

        }
    }

}
