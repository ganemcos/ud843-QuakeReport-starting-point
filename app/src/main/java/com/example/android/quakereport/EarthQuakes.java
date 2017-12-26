package com.example.android.quakereport;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Acer on 24-12-2017.
 */

public class EarthQuakes {
    private String mLocation;
    private String locationdet;
    private String location;
    private long mTime;
    private double mMag;
    String[] parts;

    public EarthQuakes(double mag, String location, long time) {
        mMag = mag;
        mLocation = location;
        mTime = time;
        parts = mLocation.split(",");
        Log.v("msg", parts[0]);

    }


    /**
     * magnitude of earth Quake
     * @return
     */
    public double getmMag()
    {
        return mMag;
    }

    /**
     * Loaction of Earth Quake
     * @return
     */

    /**
     * Time of Earth Quake
     * @return
     */
    public  long getmTime()
    {
        Date date = new Date();
        return mTime;
    }

    public String getlecationdetail()
    {
        locationdet = parts[0];
        return locationdet;
    }

    public String getLocation()
    {
        try {
            location = parts[1];
            return location;
        }
        catch (NullPointerException e)
        {
            Log.e("EartQuake","location not found",e);
            return null;
        }

    }
}
