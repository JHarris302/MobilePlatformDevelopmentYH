/**
 *  Description
 *   @Name: Yakoob Hayat
 *   @StudentID: S1714096
 *   Description: class to capture the earthquake records
 */
package gcu.mpd.android_1;
import android.os.Parcelable;
import android.os.Parcel;
public class rssItem implements Parcelable {

    private String ititle, idescription, igeoLat, igeoLong, ipubDate, location, depth, magnitude;

    public rssItem(){
            ititle = "";
        idescription = "";
        igeoLat = "";
        igeoLong = "";
        ipubDate = "";
        location="";
        depth="";
        magnitude="";

    }
    public rssItem(String title, String description, String geoLat, String geoLong, String pubDate) {
        ititle = title;
        idescription = description;
        igeoLat = geoLat;
        igeoLong = geoLong;
        ipubDate = pubDate;
    }

    protected rssItem(Parcel in) {
        ititle = in.readString();
        idescription = in.readString();
        igeoLat = in.readString();
        igeoLong = in.readString();
        ipubDate = in.readString();
        location = in.readString();
    }

    public static final Creator<rssItem> CREATOR = new Creator<rssItem>() {
        @Override
        public rssItem createFromParcel(Parcel in) {
            return new rssItem(in);
        }

        @Override
        public rssItem[] newArray(int size) {
            return new rssItem[size];
        }
    };


    public String getItitle(){
        return ititle;
    }
    public String getIdescription() {
        return idescription;
    }

    public String getIgeoLat() {
        return igeoLat;
    }

    public String getIgeoLong() {
        return igeoLong;
    }

    public String getIpubDate() {
        return ipubDate;
    }

    public String getLocation(){
        String desc = getIdescription();
;
       int from = getFrom("Location: ");
       int to = getTo(";", from);


        return desc.substring(from+10,to);
    }

    public String getDepth() {

        int from = getFrom("Depth");
        int to = getTo(";", from);
        return idescription.substring(from, to);
    }

    public String getMagnitude() {

        int from = getFrom("Magnitude");
        return idescription.substring(from+10);

    }

    private int getFrom(String string)
    {
        int from = getIdescription().indexOf(string);
        return from;
    }
    private int getTo(String str, int from)
    {
        int to = getIdescription().indexOf(str, from);
        return to;
    }

    //set
    public void setIdescription(String idescription) {
        this.idescription = idescription;
    }

    public void setItitle(String ititle) {
        this.ititle = ititle;
    }

    public void setIgeoLat(String igeoLat) {
        this.igeoLat = igeoLat;
    }

    public void setIgeoLong(String igeoLong) {
        this.igeoLong = igeoLong;
    }

    public void setIpubDate(String ipubDate) {
        this.ipubDate = ipubDate;
    }

    public int getDepthInt()
    {
        String depth = getDepth().replaceAll("\\s+","");
        int depthInt = 0;
        return depthInt = Integer.parseInt(depth.substring(depth.indexOf(":") + 1, depth.indexOf("k")));

    }

    public String getTime()
    {
        String time = getIpubDate().substring(getIpubDate().indexOf(":")-2);

        return time;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ititle);
        dest.writeString(idescription);
        dest.writeString(igeoLat);
        dest.writeString(igeoLong);
        dest.writeString(ipubDate);
        dest.writeString(location);
    }
}

