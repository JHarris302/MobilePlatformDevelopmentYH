/**
 *  Description
 *   @Name: Yakoob Hayat
 *   @StudentID: S1714096
 *   Description: Activity to display the details of teh selected record
 */
package gcu.mpd.android_1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class Details extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapView mapView;
    TextView titleView, description;
    TextView date , location, magnitude, depth, time;
//    mapView = (MapView) findViewById(R.id.mapView);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


        time = (TextView) findViewById(R.id.time);

        rssItem earthquake = getIntent().getParcelableExtra("item");


        date  =  (TextView) findViewById(R.id.date);
        location = (TextView) findViewById(R.id.location);
        magnitude = (TextView) findViewById(R.id.magnitude);
        depth = (TextView) findViewById(R.id.depth);



        date.setText(earthquake.getIpubDate().substring(0, earthquake.getIpubDate().indexOf(":")-2));
        location.setText(earthquake.getLocation());
        magnitude.setText(earthquake.getMagnitude());
        depth.setText((earthquake.getDepth()));
//        time.setText(earthquake.getTime());


        //location, date, mag, depth,  +"\n"+i.getMagnitude()




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

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        rssItem i = getIntent().getParcelableExtra("item");
        String lat = i.getIgeoLat(), geolong = i.getIgeoLong();
        Double newLat = Double.parseDouble(lat);
        Double newLong = Double.parseDouble(geolong);


        // Add a marker in Sydney and move the camera

        LatLng loc = new LatLng(newLat, newLong );
        mMap.addMarker(new MarkerOptions().position(loc).title(i.getLocation()));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 7));


    }

    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
}
