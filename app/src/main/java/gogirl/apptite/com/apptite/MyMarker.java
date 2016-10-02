package gogirl.apptite.com.apptite;

/**
 * Created by akanksha bodhankar on 21-09-2016.
 */

public class MyMarker {

    private String mLabel;
    private Double mLatitude;
    private Double mLongitude;

    public MyMarker(String label, Double latitude, Double longitude) {
        this.mLabel = label;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    public String getmLabel()
    {
        return mLabel;
    }

    public void setmLabel(String mLabel) {this.mLabel = mLabel;}

    public Double getmLatitude()
    {
        return mLatitude;
    }

    public void setmLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getmLongitude()
    {
        return mLongitude;
    }

    public void setmLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}
