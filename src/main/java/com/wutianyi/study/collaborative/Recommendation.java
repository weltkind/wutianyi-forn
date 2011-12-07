package com.wutianyi.study.collaborative;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.wutianyi.utils.Pair;

public class Recommendation
{

    private static Map<String, Map<String, Float>> critics = new HashMap<String, Map<String, Float>>();

    static
    {
        critics = getCritics();
    }

    private static Map<String, Map<String, Float>> getCritics()
    {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, HashMap<String, Float>>> t = new TypeReference<HashMap<String, HashMap<String, Float>>>()
        {
        };
        Map<String, Map<String, Float>> rs = null;
        try
        {
            rs = mapper.readValue(new File("dictionary"), t);
        }
        catch (JsonParseException e)
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
        return rs;
    }

    /**
     * 欧几里得距离评价 越接近1表示越相似
     * 
     * @return
     */
    public static float euclideanDistanceScore(String person1, String person2)
    {
        Map<String, Float> map1 = critics.get(person1);
        Map<String, Float> map2 = critics.get(person2);
        float sum = 0.0f;
        for (Entry<String, Float> entry : map1.entrySet())
        {
            if (null != map2.get(entry.getKey()))
            {
                sum += Math.pow(entry.getValue() - map2.get(entry.getKey()), 2);
            }
        }
        sum = (float) Math.sqrt(sum);
        return 1.0f / (1.0f + sum);
    }

    /**
     * 皮尔逊相关度评价 返回的数值介于-1与1之间 1表示两者有一致的评价
     * 
     * @return
     */
    public static float pearsonCorretationScore(String person1, String person2)
    {
        float sum1 = 0f;
        float sum2 = 0f;
        float sum1Sq = 0f;
        float sum2Sq = 0f;
        float pSum = 0f;
        int intersect = 0;
        Map<String, Float> map1 = critics.get(person1);
        Map<String, Float> map2 = critics.get(person2);

        for (Entry<String, Float> entry : map1.entrySet())
        {
            if (null != map2.get(entry.getKey()))
            {
                ++intersect;
                sum1 += entry.getValue();
                sum2 += map2.get(entry.getKey());
                sum1Sq += Math.pow(entry.getValue(), 2);
                sum2Sq += Math.pow(map2.get(entry.getKey()), 2);
                pSum += entry.getValue() * map2.get(entry.getKey());
            }
        }
        if (intersect == 0)
        {
            return 1f;
        }
        float num = pSum - (sum1 * sum2 / intersect);
        float den = (float) Math.sqrt((sum1Sq - Math.pow(sum1, 2) / intersect)
                * (sum2Sq - Math.pow(sum2, 2) / intersect));
        if (0f == den)
        {
            return 0;
        }
        return num / den;
    }

    public static List<Pair<Float, String>> topMatches(String person, int n)
    {
        List<Pair<Float, String>> results = new ArrayList<Pair<Float, String>>();
        int i = 0;
        for (String p : critics.keySet())
        {
            if (!StringUtils.equals(p, person))
            {
                if (i > n)
                {
                    break;
                }
                results.add(new Pair<Float, String>(euclideanDistanceScore(p, person), p));
                i++;
            }
        }
        Collections.sort(results, new Comparator<Pair<Float, String>>()
        {

            @Override
            public int compare(Pair<Float, String> o1, Pair<Float, String> o2)
            {
                if (o1.getFirst() < o2.getFirst())
                {
                    return 1;
                }
                else if (o1.getFirst() > o2.getFirst())
                {
                    return -1;
                }
                return 0;
            }
        });
        return results;
    }

    /**
     * 推荐商品
     * 
     * @param person
     * @return
     */
    public static List<Pair<Float, String>> getRecommendations(String person)
    {
        Map<String, Float> map = critics.get(person);
        Map<String, Float> totals = new HashMap<String, Float>();
        Map<String, Float> simSums = new HashMap<String, Float>();
        for (Entry<String, Map<String, Float>> entry : critics.entrySet())
        {
            if (StringUtils.equals(person, entry.getKey()))
            {
                continue;
            }
            float sim = euclideanDistanceScore(person, entry.getKey());
            if (sim <= 0)
            {
                continue;
            }
            for (Entry<String, Float> e : entry.getValue().entrySet())
            {
                if (null == map.get(e.getKey()))
                {
                    if (null == totals.get(e.getKey()))
                    {
                        totals.put(e.getKey(), 0f);
                    }
                    totals.put(e.getKey(), totals.get(e.getKey()) + e.getValue() * sim);
                    if (null == simSums.get(e.getKey()))
                    {
                        simSums.put(e.getKey(), 0f);
                    }
                    simSums.put(e.getKey(), simSums.get(e.getKey()) + sim);
                }
            }
        }
        List<Pair<Float, String>> results = new ArrayList<Pair<Float, String>>();
        for (Entry<String, Float> entry : totals.entrySet())
        {
            results.add(new Pair<Float, String>(entry.getValue() / simSums.get(entry.getKey()), entry.getKey()));
        }
        Collections.sort(results, new Comparator<Pair<Float, String>>()
        {

            @Override
            public int compare(Pair<Float, String> o1, Pair<Float, String> o2)
            {
                if (o1.getFirst() < o2.getFirst())
                {
                    return 1;
                }
                else if (o1.getFirst() > o2.getFirst())
                {
                    return -1;
                }
                return 0;
            }
        });
        return results;
    }

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
    {
        System.out.println(euclideanDistanceScore("Lisa Rose", "Toby"));
        System.out.println(pearsonCorretationScore("Lisa Rose", "Toby"));
        List<Pair<Float, String>> results = topMatches("Toby", 3);
        for (Pair<Float, String> r : results)
        {
            System.out.println("(" + r.getFirst() + " : " + r.getSecond() + ")");

        }
        System.out.println("---------------------------------------------");
        List<Pair<Float, String>> r1 = getRecommendations("Toby");
        for(Pair<Float, String> r : r1)
        {
            System.out.println("(" + r.getFirst() + " : " + r.getSecond() + ")");
        }
    }
}
