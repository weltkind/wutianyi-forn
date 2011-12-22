package com.wutianyi.study.discoverygroup;

public class DiscoveryGroupUtils
{
    /**
     * 利用皮尔逊计算相似度，通过计算两个人的喜好相对于一条直线的距离来确定相似度 1表示完全相同
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static float pearson(float[] v1, float[] v2)
    {
        int len1 = v1.length;
        int len2 = v2.length;
        int minLen = len1 > len2 ? len2 : len1;
        float sum1 = 0.0f, sum1Sq = 0.0f, pSum = 0.0f;
        float sum2 = 0.0f, sum2Sq = 0.0f;

        for (int i = 0; i < len1; i++)
        {
            sum1 += v1[i];
            sum1Sq += Math.pow(v1[i], 2);
        }
        for (int i = 0; i < len2; i++)
        {
            sum2 += v2[i];
            sum2Sq += Math.pow(v2[i], 2);
        }
        for (int i = 0; i < minLen; i++)
        {
            pSum = v1[i] * v2[i];
        }
        float num = pSum - (sum1 * sum2 / minLen);
        float den = (float) Math.sqrt((sum1Sq - Math.pow(sum1, 2) / minLen) * (sum2Sq - Math.pow(sum2, 2) / minLen));
        if (den == 0.0)
        {
            return 0;
        }
        //表示距离越短
        return 1.0f - num / den;
    }
}
