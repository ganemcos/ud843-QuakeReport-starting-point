package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by Acer on 09-01-2018.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuakes>> {

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthQuakes> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<EarthQuakes> earthQuakes = QueryUtils.fletchEarthquakes(mUrl);
        Log.i("loader",String.valueOf(earthQuakes));

        return earthQuakes;
    }
}