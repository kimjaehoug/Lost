package com.example.myapplication.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.Camera;
import com.example.myapplication.locationalarm;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.GPSListener;
import com.example.myapplication.MarkerData;
import com.example.myapplication.NotificationModel;
import com.example.myapplication.Post;
import com.example.myapplication.R;
import com.example.myapplication.Reportview;
import com.example.myapplication.locationalarm;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.util.ArrayList;
import java.util.List;

public class mapsfragment extends Fragment implements AutoPermissionsListener {
    Button button_report;
    SupportMapFragment mapFragment;
    GoogleMap map;
    LocationManager manager;
    GPSListener gpsListener;
    Marker myMarker;
    MarkerOptions myLocationMarker;
    double latitude;
    double longitude;
    Double latitude2;
    Double longitude2;
    Intent intent;
    Intent alarm;
    List<Post> markerDataList = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Intent locationalarm;
    int truefalse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button_report = view.findViewById(R.id.button_report);
        intent = new Intent(getActivity(), Reportview.class);

        button_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //gpsListener 실행.
        gpsListener = new GPSListener();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override //mapReady 
            public void onMapReady(GoogleMap googleMap) {
                Log.i("MyLocTest", "지도 준비됨");
                map = googleMap;
                map.setMyLocationEnabled(true);
                startLocationService();
            }
        });
    }

    private void startLocationService() {
        try {
            Location location = null;
            long minTime = 1000;
            float minDistance = 1000;
            locationalarm fragment = new locationalarm();
            Bundle args = new Bundle();
            args.putInt("go", 1);
            fragment.setArguments(args);
            db.collection("posts")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // 각 게시글의 정보에서 위도와 경도 가져오기
                                    latitude2 = document.getDouble("latit");
                                    longitude2 = document.getDouble("long");
                                    String Title = document.getString("title");

                                    // 마커를 추가하는 함수 호출
                                    if (latitude2 != null && longitude2 != null) {
                                        // 마커를 추가하는 함수 호출
                                        double latm = Math.abs(latitude - latitude2);
                                        double longm = Math.abs(longitude - longitude2);
                                        //위도 경도 10 차이 내에서 마커 발생시 알림 보내기
                                        if(longm <= 10.0 && latm <= 10.0){
                                            truefalse = 0;
                                            args.putInt("go", truefalse);
                                            fragment.setArguments(args);
                                        }
                                        addMarkerToMap(Title,new LatLng(latitude2, longitude2));
                                    } else {
                                        truefalse = 1;
                                        Log.e("Firestore", "latitude2 or longitude2 is null for document: " + document.getId());
                                    }
                                }
                            } else {
                            }
                        }
                    });
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    showCurrentLocation(latitude, longitude);

                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "접근권한이 없어.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, gpsListener);
            } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, gpsListener);
            }

            if (map != null) {
                map.setMyLocationEnabled(true);
            }
            Log.i("MyLocTest", "onResume에서 requestLocationUpdates() 되었습니다.");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        manager.removeUpdates(gpsListener);
        if (map != null) {
            map.setMyLocationEnabled(false);
        }
        Log.i("My remove", "remove");
    }

    private void showMyLocationMarker(LatLng curPoint) {
        myLocationMarker = new MarkerOptions();
        myLocationMarker.position(curPoint);
        myLocationMarker.title("다녀간 위치 \n");
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        myMarker = map.addMarker(myLocationMarker);
    }

    private void showCurrentLocation(double latitude, double longitude) {
        LatLng curPoint = new LatLng(latitude, longitude);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 17));
        showMyLocationMarker(curPoint);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions((Activity) getActivity(), requestCode, permissions, (AutoPermissionsListener) this);

        Toast.makeText(getActivity(), "requestCode : " + requestCode + "  permissions : " + permissions + "  grantResults :" + grantResults, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(getActivity(), "permissions denied : " + permissions.length, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(getActivity(), "permissions granted : " + permissions.length, Toast.LENGTH_SHORT).show();
    }
    private void addMarkerToMap(String name,LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().position(position).title(name);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
        map.addMarker(markerOptions);
    }
}
