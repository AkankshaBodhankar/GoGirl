package gogirl.apptite.com.apptite.contact_friends.slides;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gogirl.apptite.com.apptite.R;

/*
 * First Slide of circle of trust introduction
 *
 * @author calistus
 * @since 2015-08-18
 */
public class FirstSlide extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.intro, container, false);
        return v;
    }
}
