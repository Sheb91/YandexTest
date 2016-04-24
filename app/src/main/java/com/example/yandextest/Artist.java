package com.example.yandextest;

//import android.os.Parcel;
//import android.os.Parcelable;

/**
 * Created by Н on 24.04.2016.
 */
public class Artist //implements Parcelable
{
    private int id;
    private String name;
    private String desc;

    public  Artist()
    {
    }

    public  Artist (int id, String name, String desc)
    {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public  String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
