package gogirl.apptite.com.apptite;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gogirl.apptite.com.apptite.R;

public class LearnFragment extends Fragment {

	public LearnFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        Intent i=new Intent(getActivity(),WelcomeActivity2.class);
        startActivity(i);

        return rootView;
    }
}
