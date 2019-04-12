/**
 *  Description
 *   @Name: Yakoob Hayat
 *   @StudentID: S1714096
 *   Description: Custom Adapter for the List view
 */

package gcu.mpd.android_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class rssItemAdapter extends ArrayAdapter<rssItem> {
    private Context mContext;
    int mResource;

    public rssItemAdapter(Context context, int resource, ArrayList<rssItem> objects ) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }



    @Override
    public Filter getFilter() {
        return super.getFilter();
    }


    @Override
    public rssItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        String title = getItem(p).getItitle();
        String desc = getItem(p).getIdescription();
        String geolat = getItem(p).getIgeoLat();
        String geolong = getItem(p).getIgeoLong();
        String pubdate = getItem(p).getIpubDate();
        String Depth = getItem(p).getDepth();


//        String title, String description, String geoLat, String geoLong, String pubDate

         rssItem item = new rssItem(title,desc,geolat,geolong,pubdate);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txtView = (TextView) convertView.findViewById(R.id.loc);
        TextView txtDate = (TextView) convertView.findViewById(R.id.date);
        TextView txtTime = (TextView) convertView.findViewById(R.id.time);
        TextView txtDepth = (TextView) convertView.findViewById((R.id.magnitude));

        txtView.setText("Location: \n"+getItem(p).getLocation());
//        txtDate.setText(pubdate);
        txtDate.setText("Day: "+pubdate.substring(0, pubdate.indexOf(":")-2));
        txtTime.setText("Time: "+getItem(p).getTime());
        txtDepth.setText(getItem(p).getDepth());

        return convertView;
    }
}
