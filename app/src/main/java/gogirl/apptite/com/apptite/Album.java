package gogirl.apptite.com.apptite;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class Album extends AppCompatActivity {
    private String name;
    private int thumbnail;
    private int count;

    public Album() {
    }

    public Album(String name, int count, int thumbnail) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.count=count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return count;
    }

    public void setNumOfSongs(int count) {
        this.count = count;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void mymet(String s)
    {
        Fragment newFrag=null;
        if(s.equals("rone"))
        {
            newFrag = new MapsFragment();
        }
        else if(s.equals("rtwo"))
        {
            newFrag = new TipsFragment();
        }
        else if(s.equals("rthree"))
        {
            newFrag = new RecordFragmentu();
        }
        else if(s.equals("rfour"))
        {
            newFrag = new LearnFragment();
        }
        if (newFrag != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, newFrag).commit();



        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

}
