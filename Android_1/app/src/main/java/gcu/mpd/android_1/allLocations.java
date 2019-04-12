/**
 *  Description
 *   @Name: Yakoob Hayat
 *   @StudentID: S1714096
 *   Description: activity to display the full map with all markers
 */

package gcu.mpd.android_1;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class allLocations extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_locations);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        ArrayList<rssItem> allItems = getIntent().getParcelableArrayListExtra("allItems");
        mMap = googleMap;
        for(int i=0; i<allItems.size(); i++)
        {
            LatLng location = new LatLng(Double.parseDouble(allItems.get(i).getIgeoLat()), Double.parseDouble((allItems.get(i).getIgeoLong())));
            mMap.addMarker(new MarkerOptions().position(location).title(allItems.get(i).getItitle()));


        }
        LatLng uk = new LatLng(54.3781, -2.4000);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(uk, 5));
        final infoAdapter info = new infoAdapter(this, allItems);
        mMap.setInfoWindowAdapter(info);
//



    }
}
