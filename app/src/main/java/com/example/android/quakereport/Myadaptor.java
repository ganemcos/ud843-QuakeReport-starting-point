package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Acer on 24-12-2017.
 */

public class Myadaptor extends ArrayAdapter {


    public Myadaptor(Activity context, ArrayList<EarthQuakes> earthquakes) {
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listitemview = convertView;
        if (listitemview == null) {
            listitemview = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_view, parent, false);
        }

        EarthQuakes current = (EarthQuakes) getItem(position);

        TextView magtextview = (TextView) listitemview.findViewById(R.id.magid);
        magtextview.setText(String.valueOf(current.getmMag()));

        GradientDrawable magnitudecircle = (GradientDrawable) magtextview.getBackground();

        int magnitudecolor = getMagColor(current.getmMag());
        magnitudecircle.setColor(magnitudecolor);


        TextView locationdetailstextview = (TextView) listitemview.findViewById(R.id.locationdetails);
        locationdetailstextview.setText(current.getlecationdetail());

        TextView location = (TextView) listitemview.findViewById(R.id.location);
        location.setText(current.getLocation());

        TextView dateview = (TextView) listitemview.findViewById(R.id.date);

        Date date = new Date(current.getmTime());
        String formateddate = formateDate(date);
        dateview.setText(formateddate);

        TextView timeView = (TextView) listitemview.findViewById(R.id.time);
        String formatedtime = formateTime(date);
        timeView.setText(formatedtime);

        return listitemview;
    }

    private int getMagColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        Log.v("color",String.valueOf(magnitudeFloor));
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }


    private String formateDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL M, yyyy");
        return dateFormat.format(date);
    }



    private String formateTime(Date date) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(date);
    }




}
