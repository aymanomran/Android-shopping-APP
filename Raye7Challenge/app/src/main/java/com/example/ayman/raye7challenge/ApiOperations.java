package com.example.ayman.raye7challenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ayman.raye7challenge.tasks.DownloadTask;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by AYMAN on 20/07/2018.
 */

public class ApiOperations {

    GoogleMap mMap;
    Context ctx;
    JSONObject Source,Destination;
    JSONArray UsersJsonArr;
    ArrayList<Users>UsersArr;
    DownloadTask downloadTask;
    Places sourceP,DestinationP;
    JSONObject coordinates;
    ArrayList<Users>myNewMarkers;
    LatLng _source;
    LatLng dest;
Map<String,String> UsersImagesDictionary;//key->UserName ,,,Value-->Image

    //Constructor
    public ApiOperations(GoogleMap mMap, Context ctx) {
        this.mMap = mMap;
        this.ctx = ctx;
       UsersArr=new ArrayList<>();
       Source=new JSONObject();
       Destination=new JSONObject();
       UsersJsonArr=new JSONArray();
       coordinates=new JSONObject();
       myNewMarkers=new ArrayList<>();
    UsersImagesDictionary=new HashMap<>();
    }
//Make Volley Request
    public void MyTask(GoogleMap googleMap) {
        mMap = googleMap;

        RequestQueue queue = Volley.newRequestQueue(ctx);
        String url = "https://staging.raye7.com/android_interns/index";
        final JSONObject[] _Response = {new JSONObject()};

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        DoMyTask(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("", "Error: " + error.getMessage());
            }
        });

        queue.add(jsonObjReq);
        queue.start();
    }
    //Return Users Images From URL
    public Bitmap getImage(String _url){
        Bitmap bmp=null;
        URL url = null;
        try {
            url = new URL(_url);

            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bmp=Bitmap.createScaledBitmap(bmp, 100, 100, false);
        return  bmp;
    }
   //Draw The Shortest Path From Source To Destination
    public  void DrawShortestPath(LatLng source, LatLng Dest, DownloadTask downloadTask){

        String url = getDirectionsUrl(source, Dest);
        downloadTask = new DownloadTask(mMap);
        // Start downloading json data from Google Directions API
        downloadTask.execute(url);

    }
    //get The Distance Between Two Points On The Map
    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }

    // Parsing Response JsonObject And Add Maekers On The Map
    public void DoMyTask(JSONObject response){
        try {
            try {
                Source = response.getJSONObject("source");
                Destination = response.getJSONObject("destinaton");
                UsersJsonArr = response.getJSONArray("users");
            }catch (Exception ex){}
            myNewMarkers=new ArrayList<>();

            for(int i=0;i<5;i++){

                try {
                    coordinates = UsersJsonArr.getJSONObject(i).getJSONObject("coordinates");
                    UsersArr.add(new Users(UsersJsonArr.getJSONObject(i).getString("name"), UsersJsonArr.getJSONObject(i).getString("image"), coordinates.getString("latitude"), coordinates.getString("longitude")));
                }catch (Exception ex){}
            }
            try {

                sourceP = new Places(Source.getString("name"), Source.getString("latitude"), Source.getString("longitude"));
                DestinationP = new Places(Destination.getString("name"), Destination.getString("latitude"), Destination.getString("longitude"));
            }catch (Exception ex){}
            for(int i=0;i<UsersArr.size();i++){
                if(CalculationByDistance(new LatLng(Double.valueOf(UsersArr.get(i).getLatitude()),Double.valueOf(UsersArr.get(i).getLongitude())),new LatLng(Double.valueOf(DestinationP.getLatitude()),Double.valueOf(DestinationP.getLongitude()))) <= CalculationByDistance(new LatLng(Double.valueOf(sourceP.getLatitude()),Double.valueOf(sourceP.getLongitude())),new LatLng(Double.valueOf(DestinationP.getLatitude()),Double.valueOf(DestinationP.getLongitude())))){
                    myNewMarkers.add(UsersArr.get(i));

                }
            }

            _source=new LatLng(Double.valueOf(sourceP.getLongitude()),Double.valueOf(sourceP.getLatitude()));
            dest=new LatLng(Double.valueOf(DestinationP.getLongitude()),Double.valueOf(DestinationP.getLatitude()));

            try {

                mMap.addMarker(new MarkerOptions().position(_source).title(sourceP.getName()));
                mMap.addMarker(new MarkerOptions().position(dest).title(DestinationP.getName()));
                for(int i=0;i<myNewMarkers.size();i++) {
                    LatLng marker = new LatLng(Double.valueOf(myNewMarkers.get(i).getLongitude()),Double.valueOf(myNewMarkers.get(i).getLatitude()));
                    mMap.addMarker(new MarkerOptions().position(marker).title(myNewMarkers.get(i).getName()).icon(BitmapDescriptorFactory.fromBitmap(getImage(myNewMarkers.get(i).getImage()))));
                    UsersImagesDictionary.put(myNewMarkers.get(i).getName(),myNewMarkers.get(i).getImage());
                }
                //draw The Shortest Path
                DrawShortestPath(_source,dest,downloadTask);
                CameraUpdate SourcePlacce=
                        CameraUpdateFactory.newLatLng(new LatLng(_source.latitude,_source.longitude));
                CameraUpdate zoom=CameraUpdateFactory.zoomTo(12);

                mMap.moveCamera(SourcePlacce);
                mMap.animateCamera(zoom);


            }catch (Exception ex){
                String s="";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
// Get Dirextions Usig Google API
    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

}
