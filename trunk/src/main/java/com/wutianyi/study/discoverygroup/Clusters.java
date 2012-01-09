package com.wutianyi.study.discoverygroup;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Clusters
{
    private String[] colnames;
    private String[] rownames;
    private float[][] datas;

    public void init(String fileName) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String line = reader.readLine();
        String[] words = line.split("\t");
        colnames = new String[words.length - 1];
        System.arraycopy(words, 1, colnames, 0, colnames.length);

        List<String> titles = new ArrayList<String>();
        List<float[]> fs = new ArrayList<float[]>();
        line = reader.readLine();
        while (StringUtils.isNotBlank(line))
        {
            String[] ls = line.split("\t");

            int len = ls.length;
            float[] f = new float[len - 1];
            for (int i = 1; i < len; i++)
            {
                f[i - 1] = Float.parseFloat(ls[i]);
            }
            titles.add(ls[0]);
            fs.add(f);
            line = reader.readLine();
        }
        rownames = titles.toArray(new String[]
        {});
        datas = new float[fs.size()][];
        int size = 0;
        for (float[] f : fs)
        {
            datas[size] = f;
            ++size;
        }
        reader.close();
    }

    public Bicluster hcluster()
    {
        if (null == colnames)
        {
            return null;
        }
        Bicluster[] clusters = getClusters();
        Map<String, Float> cache = new HashMap<String, Float>();
        while (clusters.length > 1)
        {
            int low1 = 0;
            int low2 = 1;
            float closest = DiscoveryGroupUtils.pearson(clusters[low1].getVec(), clusters[low2].getVec());
            int len = clusters.length;
            for (int i = 0; i < len; i++)
            {
                for (int j = i + 1; j < len; j++)
                {
                    String key = i + "_" + j;
                    Float distance = cache.get(key);
                    if (null == distance)
                    {
                        distance = DiscoveryGroupUtils.pearson(clusters[i].getVec(), clusters[j].getVec());
                        cache.put(key, distance);
                    }
                    if (distance < closest)
                    {
                        closest = distance;
                        low1 = i;
                        low2 = j;
                    }
                }
            }
            clusters = mergeCluster(low1, low2, clusters);
        }

        return clusters[0];
    }

    public void printClusters(Bicluster bicluster, int level)
    {
        String t = "";
        for (int i = 0; i < level; i++)
        {
            t += "\t";
        }
        if (null == bicluster)
        {
            return;
        }
        if (-1 == bicluster.getId())
        {
            System.out.println(t + -1);
        }
        else
        {
            System.out.println(t + rownames[bicluster.getId()]);
        }
        printClusters(bicluster.getLeft(), level + 1);
        printClusters(bicluster.getRight(), level + 1);
    }

    private Bicluster[] mergeCluster(int low1, int low2, Bicluster[] clusters)
    {
        int len = clusters[low1].getVec().length;
        float[] v1 = clusters[low1].getVec();
        float[] v2 = clusters[low2].getVec();
        float[] mergevec = new float[len];
        for (int i = 0; i < len; i++)
        {
            mergevec[i] = (v1[i] + v2[i]) / 2;
        }
        len = clusters.length;
        Bicluster[] newClusters = new Bicluster[len - 1];
        System.arraycopy(clusters, 0, newClusters, 0, low1);
        System.arraycopy(clusters, low1 + 1, newClusters, low1, low2 - low1 - 1);
        if(low2 < len - 1)
        {
            System.arraycopy(clusters, low2 + 1, newClusters, low2 - 1, len - low2 - 1);
        }
        newClusters[len - 2] = new Bicluster(mergevec, -1);
        newClusters[len - 2].setLeft(clusters[low1]);
        newClusters[len - 2].setRight(clusters[low2]);
        return newClusters;
    }

    private Bicluster[] getClusters()
    {
        int len = datas.length;
        Bicluster[] clusters = new Bicluster[len];
        for (int i = 0; i < len; i++)
        {
            Bicluster bicluster = new Bicluster(datas[i], i);
            clusters[i] = bicluster;
        }
        return clusters;
    }

    public static void main(String[] args) throws IOException
    {
        Clusters clusters = new Clusters();
        clusters.init("result.txt");
        Bicluster bicluster = clusters.hcluster();
        clusters.printClusters(bicluster, 1);
    }

    public String[] getColnames()
    {
        return colnames;
    }

    public void setColnames(String[] colnames)
    {
        this.colnames = colnames;
    }

    public String[] getRownames()
    {
        return rownames;
    }

    public void setRownames(String[] rownames)
    {
        this.rownames = rownames;
    }

    public float[][] getDatas()
    {
        return datas;
    }

    public void setDatas(float[][] datas)
    {
        this.datas = datas;
    }

}
