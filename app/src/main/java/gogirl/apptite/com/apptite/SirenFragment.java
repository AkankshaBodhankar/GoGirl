package gogirl.apptite.com.apptite;

import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import gogirl.apptite.com.apptite.*;

public class SirenFragment extends Fragment {

    ToggleButton toggleButton;
	public SirenFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        Log.v("Inside Siren","################################");

        Intent i=new Intent(getActivity(),Siren.class);
        startActivity(i);

         
        return rootView;
    }
}
