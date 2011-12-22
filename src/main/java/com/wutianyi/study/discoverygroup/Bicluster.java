package com.wutianyi.study.discoverygroup;

public class Bicluster
{
    private Bicluster left;
    private Bicluster right;
    private float[] vec;
    private int id;
    private float distance;

    public Bicluster(float[] _vec, int id)
    {
        this.vec = _vec;
        this.id = id;
    }

    public Bicluster getLeft()
    {
        return left;
    }

    public void setLeft(Bicluster left)
    {
        this.left = left;
    }

    public Bicluster getRight()
    {
        return right;
    }

    public void setRight(Bicluster right)
    {
        this.right = right;
    }

    public float[] getVec()
    {
        return vec;
    }

    public void setVec(float[] vec)
    {
        this.vec = vec;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public float getDistance()
    {
        return distance;
    }

    public void setDistance(float distance)
    {
        this.distance = distance;
    }

}
