/**
 *  Description
 *   @Name: Yakoob Hayat
 *   @StudentID: S1714096
 *   Description: Adapter to display the info windo in the allLocation.java activity
 */
package gcu.mpd.android_1;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class infoAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<rssItem> items;
    private rssItem currentRss;

    public infoAdapter(Context context, ArrayList<rssItem> allItems) {
        this.context = context;
        this.items = allItems;
    }

    public ArrayList<rssItem> getItems() {
        return items;
    }

    private void setCurrentRss(rssItem currentRss) {
        this.currentRss = currentRss;
    }

    public rssItem getCurrentRss() {
        return currentRss;
    }



    @Override
    public View getInfoWindow(Marker marker) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View infoWindow = inflater.inflate(R.layout.marker_info, null);

        TextView title = (TextView) infoWindow.findViewById(R.id.location);
        TextView desc = (TextView) infoWindow.findViewById(R.id.desc);

        for(int i=0; i<items.size(); i++)
        {

            if(marker.getTitle().equals(items.get(i).getItitle()))
            {
                setCurrentRss(items.get(i));

            }
        }

        title.setText(getCurrentRss().getLocation());
        desc.setText(getCurrentRss().getIpubDate() +"\n"+ getCurrentRss().getDepth()+"\nMagnitude:"+ getCurrentRss().getMagnitude());

        return infoWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {

        return null;
    }
}
