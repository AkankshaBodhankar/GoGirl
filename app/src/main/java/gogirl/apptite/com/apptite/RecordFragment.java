package gogirl.apptite.com.apptite;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecordFragment extends Fragment {

	public RecordFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
         
        return rootView;
    }
}