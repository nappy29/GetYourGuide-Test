package com.example.gygapp.adapter;

import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomBindingAdapter {

    @BindingAdapter("dateText")
    public static void setDateText(TextView view, String dateString) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");

        Date date = null;
        try {
            date = dateFormat.parse(dateString);
            view.setText(formatter.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @BindingAdapter("reviewPreview")
    public static void setPreviewText(TextView view, String message){

        String[] arr = message.split(" ");

        int half_lenght = arr.length / 2;

        // show preview for comments that have over 20 words. Show the first 10 words
        if(half_lenght > 10) {
            String preview = message.substring(0, message.length()/2) + " ...";
            view.setText(preview);
        } else
            view.setText(message);
    }
}
