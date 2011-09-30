package com.wutianyi.study.json;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.TreeMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.TypeReference;

public class User
{
    public enum Gender
    {
        MALE, FEMALE
    };

    public static class Name
    {

        private String _first, _last;

        public String getFirst()
        {
            return _first;
        }

        public String getLast()
        {
            return _last;
        }

        public void setFirst(String s)
        {
            this._first = s;
        }

        public void setLast(String s)
        {
            this._last = s;
        }
    }

    private Gender _gender;
    private Name _name;
    private boolean _isVerified;
    private byte[] _userImage;

    public Name getName()
    {
        return _name;
    }

    public boolean isVerified()
    {
        return _isVerified;
    }

    public Gender getGender()
    {
        return _gender;
    }

    public byte[] getUserImage()
    {
        return _userImage;
    }

    public void setName(Name n)
    {
        _name = n;
    }

    public void setVerified(boolean b)
    {
        _isVerified = b;
    }

    public void setGender(Gender g)
    {
        _gender = g;
    }

    public void setUserImage(byte[] b)
    {
        _userImage = b;
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        User user_1 = new User();
        Name name = new Name();
        name.setFirst("wu");
        name.setLast("hanjie");
        user_1.setName(name);
        user_1.setGender(Gender.MALE);
        user_1.setVerified(true);
        user_1.setUserImage("wutianyi".getBytes());
        mapper.writeValue(new File("user.json"), user_1);
        Map<Integer, User>map = new HashMap<Integer, User>();
        map.put(1, user_1);
        mapper.writeValue(new File("user-map.json"), map);
        
        User user = mapper.readValue(new File("user.json"), User.class);
        System.out.println(user.getName().getFirst());
        //对于容器类型的处理
        Map<Integer, User> result = mapper.readValue(new File("user-map.json"), new TypeReference<Map<Integer, User>>()
        {
        });
        System.out.println(result.get(1).getName().getFirst());
        //树的处理
        
        JsonNode rootNode = mapper.readValue(new File("user.json"), JsonNode.class);
        JsonNode nameNode = rootNode.path("name");
        String lastName = nameNode.path("last").getTextValue();
        System.out.println(lastName);
        ((ObjectNode)nameNode).put("last", "jsoner");
        mapper.writeValue(new File("user.json"), rootNode);
//        Map<String, User> result = mapper.readValue(new File("user.json"), new TypeReference<Map<String, User>>()
//        {
//        });
        
        TreeMapper treeMapper = new TreeMapper();
        ObjectNode userOb = treeMapper.objectNode();
        ObjectNode nameOb = userOb.putObject("name");
        nameOb.put("first","joe");
        nameOb.put("last", "Sixpack");
        userOb.put("gender", Gender.MALE.toString());
        userOb.put("verified", false);
        byte[] imageData = "wutianyi".getBytes();
        userOb.put("userImage", imageData);
        
       
    }
}
