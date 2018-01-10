/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderCallbacks<List<EarthQuakes>>{

    private static final  int EARTHQUAKE_LOADER_ID = 1;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static Myadaptor madaptor;
    private TextView mEmptyStateTextView;

    private static final String Usgs_Request_Url =
    "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

//    private static class EarthQuakeAsynkTask extends AsyncTask<String,Void,List<EarthQuakes>> {
//
//        @Override
//        protected List<EarthQuakes> doInBackground(String... Urls) {
//
//            if (Urls.length < 1 || Urls == null) {
//                return null;
//            }
//            List<EarthQuakes> result = QueryUtils.fletchEarthquakes(Urls[0]);
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(List<EarthQuakes> data) {
//            madaptor.clear();
//
//            if (data!=null && !data.isEmpty())
//            {
//                madaptor.addAll(data);
//            }
//        }
//    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView)findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);
        madaptor = new Myadaptor(this,new ArrayList<EarthQuakes>());


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(madaptor);

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID,null,this);

//        EarthQuakeAsynkTask task = new EarthQuakeAsynkTask();
//        task.execute(Usgs_Request_Url);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                EarthQuakes currentEarthQuake = (EarthQuakes) madaptor.getItem(position);

                Uri earthquakeuri = Uri.parse(currentEarthQuake.geturl());

                Intent websiteintent = new Intent(Intent.ACTION_VIEW,earthquakeuri);

                startActivity(websiteintent);
            }
        });


    }

    @Override
    public Loader<List<EarthQuakes>> onCreateLoader(int id, Bundle bundle) {
        return new EarthquakeLoader(this,Usgs_Request_Url);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuakes>> loader, List<EarthQuakes> data) {
        mEmptyStateTextView.setText(R.string.no_earthquake);
        madaptor.clear();
        Log.i("data",String.valueOf(data));
        if (data!=null && !data.isEmpty())
        {
            madaptor.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuakes>> loader) {
        madaptor.clear();
    }
}
