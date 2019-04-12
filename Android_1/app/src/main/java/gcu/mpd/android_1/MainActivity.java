/**
 *  Description
 *   @Name: Yakoob Hayat
 *   @StudentID: S1714096
 *   Description: main activity
 */

package gcu.mpd.android_1;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.content.Intent;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity  {

    ListView lvRss;
    ArrayList<String> titles,
                        sortedTitles,
                        geoLat,
                        locationsList,
                        dateList,
                        geoLong;
    ArrayList<rssItem> items;
    rssItem item;
    EditText searchBarr;
    rssItemAdapter adapter, DeepestSort, Deepest, magSort, highMag;
    Boolean sort;
    Spinner spinner;
    TextView locateAll;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        lvRss = (ListView) findViewById(R.id.lvRss);
        searchBarr = (EditText) findViewById(R.id.searchBar);
        titles = new ArrayList<String>();


        geoLat = new ArrayList<String>();
        geoLong = new ArrayList<String>();
        items = new ArrayList<rssItem>();
        spinner = (Spinner) findViewById(R.id.spinner1);
        locateAll = (TextView) findViewById(R.id.locateAll);


        locateAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,allLocations.class);
                intent.putExtra("allItems", MainActivity.this.items);
                startActivity(intent);



            }
        });

//        item = new rssItem();
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        String item = parent.getItemAtPosition(pos).toString();
                        //Get deppest
                        if(item.equalsIgnoreCase("Deepest")) {
                            int deepest = 0;
                            sort=false;
                            String titleDeepest = "an Error";
                            ArrayList<rssItem> itm = new ArrayList<>();
                            for(int i=0; i<items.size();i++)
                            {
                                String dpt = items.get(i).getDepth().replaceAll("\\s+","");
                                int newdpt = Integer.parseInt(dpt.substring(dpt.indexOf(":") + 1, dpt.indexOf("k")));
                                if (newdpt > deepest)
                                {
                                    deepest = newdpt;
                                    if(itm.size()>=1)
                                    {
                                        itm.set(0,items.get(i));
                                    }else{
                                        itm.add(items.get(i));
                                    }

                                }
//                                Toast.makeText(MainActivity.this, newdpt, Toast.LENGTH_SHORT).show();
                            }

                            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, itm);
                            lvRss.setAdapter(adapter);

                        }
                        //sort by deepest
                        else if(item.equalsIgnoreCase("Sort Deepest")) {
//                            Toast.makeText(MainActivity.this, "lol", Toast.LENGTH_SHORT).show();
                           sort=true;
                            rssItem temp ;
                            int depthI, depthX;
                            boolean sorted = false;
                            while(sorted == false)
                            {
                                sorted = true;
                                for(int i=0; i<items.size()-1; i++)
                                {
                                    depthI = items.get(i).getDepthInt();
                                    depthX = items.get(i+1).getDepthInt();
                                    if(depthI<depthX)
                                    {
                                        sorted = false;
                                        temp = items.get(i);
                                        items.set(i, items.get(i+1));
                                        items.set(i+1,temp);
                                    }

                                }
                            }
                            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, items);
                            lvRss.setAdapter(adapter);
                        }
                        //get highest magnitude
                        else if(item.equalsIgnoreCase("Highest Magnitude"))
                        {
                            sort=false;
                            double strongest = 0;
                            String titleStrongest = "an Error";
                            ArrayList<rssItem> itm = new ArrayList<>();
                            for(int i=0; i<items.size();i++)
                            {
                                String mag = items.get(i).getMagnitude().replaceAll("\\s+","");
                                double newMag = Double.parseDouble(mag);
                                if ( newMag> strongest)
                                {
                                    strongest = newMag;

                                    if(itm.size()>=1)
                                    {
                                        itm.set(0,items.get(i));
                                    }else{
                                        itm.add(items.get(i));
                                    }


                                }
//                                Toast.makeText(MainActivity.this, newdpt, Toast.LENGTH_SHORT).show();
                            }


                             adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, itm);
                            lvRss.setAdapter(adapter);
                        }//sort highest Magnitude
                        else if(item.equalsIgnoreCase("Sort Highest Magnitude"))
                        {
                            sort=true;
                            sortedTitles = new ArrayList<String>();
                            rssItem temp ;
                            double magI, magX;
                            boolean sorted = false;
                            while(sorted==false)
                            {
                                sorted=true;
                                for(int i=0; i<items.size()-1; i++)
                                {
                                    magI = Double.parseDouble(items.get(i).getMagnitude());
                                    magX = Double.parseDouble(items.get(i+1).getMagnitude());

                                    if(magI<magX)
                                    {
                                        sorted=false;
                                        temp = items.get(i);
                                        items.set(i, items.get(i+1));
                                        items.set(i+1,temp);
                                    }
                                }
                            }
                            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, items);

                            lvRss.setAdapter(adapter);
                        }else if(item.equalsIgnoreCase("most East"))
                        {
                                double mostEast= 0.0;
                                ArrayList<rssItem> east = new ArrayList<rssItem>();
                                for(int i=0; i<items.size(); i++)
                                {
                                    if(mostEast < Double.parseDouble((items.get(i).getIgeoLong())))
                                    {
                                        mostEast = Double.parseDouble((items.get(i).getIgeoLong()));
                                        if(east.size()>=1)
                                        {
                                            east.set(0,items.get(i));
                                        }else{
                                            east.add(items.get(i));
                                        }
                                    }


                                }
                            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, east);

                            lvRss.setAdapter(adapter);
                        }else if(item.equalsIgnoreCase("most west"))
                        {
                            double mostWest= Double.parseDouble(items.get(0).getIgeoLong());
                            ArrayList<rssItem> west = new ArrayList<rssItem>();
                            for(int i=0; i<items.size(); i++)
                            {
                                if(mostWest > Double.parseDouble((items.get(i).getIgeoLong())))
                                {
                                    mostWest = Double.parseDouble((items.get(i).getIgeoLong()));
                                    if(west.size()>=1)
                                    {
                                        west.set(0,items.get(i));
                                    }else{
                                        west.add(items.get(i));
                                    }
                                }


                            }
                            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, west);

                            lvRss.setAdapter(adapter);
                        }else  if(item.equalsIgnoreCase("most south"))
                        {
                            double mostNorth= Double.parseDouble(items.get(0).getIgeoLat());
                            ArrayList<rssItem> north = new ArrayList<rssItem>();
                            for(int i=0; i<items.size(); i++)
                            {
                                if(mostNorth > Double.parseDouble((items.get(i).getIgeoLat())))
                                {
                                    mostNorth = Double.parseDouble((items.get(i).getIgeoLat()));
                                    if(north.size()>=1)
                                    {
                                        north.set(0,items.get(i));
                                    }else{
                                        north.add(items.get(i));
                                    }
                                }


                            }
                            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, north);

                            lvRss.setAdapter(adapter);
                        }else  if(item.equalsIgnoreCase("most north"))
                        {
                            double mostNorth= 0.0;
                            ArrayList<rssItem> north = new ArrayList<rssItem>();
                            for(int i=0; i<items.size(); i++)
                            {
                                if(mostNorth < Double.parseDouble((items.get(i).getIgeoLat())))
                                {
                                    mostNorth = Double.parseDouble((items.get(i).getIgeoLat()));
                                    if(north.size()>=1)
                                    {
                                        north.set(0,items.get(i));
                                    }else{
                                        north.add(items.get(i));
                                    }
                                }


                            }
                            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, north);

                            lvRss.setAdapter(adapter);
                        }

                            if(item.equalsIgnoreCase("No Sort"))
                        {
                             adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, items);

                            lvRss.setAdapter(adapter);
                        }

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });





        searchBarr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<rssItem> temp = new ArrayList<rssItem>();
                for(int i=0; i<MainActivity.this.items.size(); i++ )
                {
                    if(MainActivity.this.items.get(i).getItitle().toLowerCase().contains(s.toString().toLowerCase()))
                    {
                        temp.add(MainActivity.this.items.get(i));

                    }
                    adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, temp);
                    lvRss.setAdapter(adapter);
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvRss.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(MainActivity.this,Details.class);
//                Object item = parent.getItemAtPosition(position);
                if(sort=true)
                {
                    String itemName = MainActivity.this.adapter.getItem(position).getItitle();
                    Toast.makeText(MainActivity.this, itemName, Toast.LENGTH_LONG).show();

                    for(int i =0; i <items.size(); i++)
                    {
                        if(itemName==items.get(i).getItitle())
                        {
                            intent.putExtra("item", items.get(i));
                        }
                    }
                    startActivity(intent);
                }

            }
        });


        new ProcessInBackGround().execute();
    }// onCreate



    public InputStream getInputStream(URL url)
    {
        try {
            return url.openConnection().getInputStream();
        }
        catch (IOException e) {
            return null;
        }
    }//InputStream


    public class ProcessInBackGround extends AsyncTask<Integer, Void, Exception>{

        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();

            progressDialog.setMessage("Loading RSS Feed...");
            progressDialog.show();


        }

        @Override
        protected Exception doInBackground(Integer... integers) {

            try{
                URL url = new URL("http://quakes.bgs.ac.uk/feeds/MhSeismology.xml");
                XmlPullParserFactory factory =XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();
                String title="title", desc="desc", lat="lat", geolong="long", date="date1";
                   rssItem item= new rssItem();
                while(eventType != XmlPullParser.END_DOCUMENT){

                    if(eventType == XmlPullParser.START_TAG){

                        if(xpp.getName().equalsIgnoreCase("item")){
                            insideItem = true;
                        }
                        if (insideItem){
                            if(xpp.getName().equalsIgnoreCase("title")){
                                    title = xpp.nextText();
                                    item.setItitle(title);
                                    titles.add(title);
                            }
                            if(xpp.getName().equalsIgnoreCase("pubDate")) {
                                date = xpp.nextText();
                                item.setIpubDate(date);
                            }
                            if(xpp.getName().equalsIgnoreCase("geo:lat")) {
                                lat = xpp.nextText();
                                item.setIgeoLat(lat);
                            }
                            if(xpp.getName().equalsIgnoreCase("geo:long")) {
                                geolong = xpp.nextText();
                                item.setIgeoLong(geolong);
                            }
                            if(xpp.getName().equalsIgnoreCase("description")) {
                                desc = xpp.nextText();
                                item.setIdescription(desc);
                            }

                        }
                    }//check start tag
                    if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")){
                        insideItem = false;
                        if(item!=null){
                            items.add(item);
                        }
                        item = new rssItem();
                    }




                    eventType = xpp.next();
                }//while

            }catch(MalformedURLException e){
                exception = e;
            }catch(XmlPullParserException e) {
                exception = e;
            }catch(IOException e){
                exception = e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            adapter = new rssItemAdapter(MainActivity.this, R.layout.listlayout, items);
            lvRss.setAdapter(adapter);
            progressDialog.dismiss();
        }


    }
}
