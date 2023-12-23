package com.example.myapplication;

import com.google.android.gms.maps.model.LatLng;

public class MarkerData {
    private LatLng position;
    private String title;

    public MarkerData(LatLng position, String title) {
        this.position = position;
        this.title = title;
    }

    public LatLng getPosition() {
        return position;
    }

    public String getTitle() {
        return title;
    }
}
