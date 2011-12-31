package com.wutianyi.study.net.dataobject;

import com.wutianyi.study.net.bo.BusinessBO;

public class NetDTO
{
    private int[] wordIds;
    private int[] urlIds;
    private int[] hiddenIds;
    private float[] ai;
    private float[] ah;
    private float[] ao;

    private float[][] wi;
    private float[][] wo;

    public NetDTO(int[] wordIds, int[] urlIds)
    {
        BusinessBO.generatehiddennode(wordIds, urlIds);
        this.wordIds = wordIds;
        this.urlIds = urlIds;
        this.hiddenIds = BusinessBO.getHiddenIds(wordIds, urlIds);
        ai = new float[wordIds.length];
        ah = new float[hiddenIds.length];
        ao = new float[urlIds.length];
        wi = new float[wordIds.length][hiddenIds.length];
        wo = new float[hiddenIds.length][urlIds.length];
        for (int i = 0; i < wordIds.length; i++)
        {
            for (int j = 0; j < hiddenIds.length; j++)
            {
                wi[i][j] = BusinessBO.getStrenghtByWordId(wordIds[i], hiddenIds[j]);
            }
        }
        for (int i = 0; i < urlIds.length; i++)
        {
            for (int j = 0; j < hiddenIds.length; j++)
            {
                wo[j][i] = BusinessBO.getStrenghtByUrlId(urlIds[i], hiddenIds[j]);
            }
        }
    }

    public void backPropagate(float[] targets, int n)
    {
        float[] outputDeltas = new float[urlIds.length];
        for (int i = 0; i < urlIds.length; i++)
        {
            float error = targets[i] - ao[i];
            outputDeltas[i] = dtanh(ao[i]) * error;
        }
        float[] hiddenDeltas = new float[hiddenIds.length];
        for (int i = 0; i < hiddenIds.length; i++)
        {
            float error = 0.0f;
            for (int j = 0; j < urlIds.length; j++)
            {
                error = error + outputDeltas[j] * wo[i][j];
            }
            hiddenDeltas[i] = dtanh(ah[i]) * error;
        }
        for (int i = 0; i < hiddenIds.length; i++)
        {
            for (int j = 0; j < urlIds.length; j++)
            {
                float change = outputDeltas[j] * ah[i];
                wo[i][j] = wo[i][j] + n * change;
            }
        }
        for (int i = 0; i < wordIds.length; i++)
        {
            for (int j = 0; j < hiddenIds.length; j++)
            {
                float change = hiddenDeltas[j] * ai[i];
                wi[i][j] = wi[i][j] + n * change;
            }
        }
    }

    private float dtanh(float f)
    {
        return 1 - f * f;
    }

    public float[] feedforward()
    {
        for (int i = 0; i < wordIds.length; i++)
        {
            ai[i] = 1.0f;
        }
        for (int i = 0; i < hiddenIds.length; i++)
        {
            float sum = 0.0f;
            for (int j = 0; j < wordIds.length; j++)
            {
                sum += ai[j] * wi[j][i];
            }
            ah[i] = (float) Math.tanh(sum);
        }
        for (int i = 0; i < urlIds.length; i++)
        {
            float sum = 0.0f;
            for (int j = 0; j < hiddenIds.length; j++)
            {
                sum += ah[j] * wo[j][i];
            }
            ao[i] = (float) Math.tanh(sum);
        }
        return ao;
    }

    public int[] getWordIds()
    {
        return wordIds;
    }

    public void setWordIds(int[] wordIds)
    {
        this.wordIds = wordIds;
    }

    public int[] getUrlIds()
    {
        return urlIds;
    }

    public void setUrlIds(int[] urlIds)
    {
        this.urlIds = urlIds;
    }

    public int[] getHiddenIds()
    {
        return hiddenIds;
    }

    public void setHiddenIds(int[] hiddenIds)
    {
        this.hiddenIds = hiddenIds;
    }

    public float[] getAi()
    {
        return ai;
    }

    public void setAi(float[] ai)
    {
        this.ai = ai;
    }

    public float[] getAh()
    {
        return ah;
    }

    public void setAh(float[] ah)
    {
        this.ah = ah;
    }

    public float[] getAo()
    {
        return ao;
    }

    public void setAo(float[] ao)
    {
        this.ao = ao;
    }

    public float[][] getWi()
    {
        return wi;
    }

    public void setWi(float[][] wi)
    {
        this.wi = wi;
    }

    public float[][] getWo()
    {
        return wo;
    }

    public void setWo(float[][] wo)
    {
        this.wo = wo;
    }
}
