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
    MediaPlayer mp;
	public SirenFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_siren, container, false);
        toggleButton = (ToggleButton) rootView.findViewById(R.id.toggleButton);
        mp = MediaPlayer.create(getActivity(), R.raw.sirenpolice);


        if (toggleButton.isChecked())
            mp.start();
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!toggleButton.isChecked()) {
                    mp.pause();
                } else {
                    mp.start();
                    mp.isLooping();
                }
            }
        });
        return rootView;
    }
}
