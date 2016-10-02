package gogirl.apptite.com.apptite;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HelplineFragment extends Fragment {
    Button bt_helpline1;
    Button bt_helpline2;
    Button bt_helpline3;
    Button bt_helpline4;
    Button bt_helpline5;
    Button bt_helpline6;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;

    public HelplineFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_helpline, container, false);
        bt_helpline1 = (Button) rootView.findViewById(R.id.helpline1);
        bt_helpline1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                makeCall("tel:8885693793");//replace with 100
            }
        });

        bt_helpline2 = (Button) rootView.findViewById(R.id.helpline2);
        bt_helpline2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                makeCall("tel:8885693793");//replace with 1091
            }
        });
        bt_helpline3 = (Button) rootView.findViewById(R.id.helpline3);
        bt_helpline3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                makeCall("tel:8885693793");//replace with 011123219750
            }
        });
        bt_helpline4 = (Button) rootView.findViewById(R.id.helpline4);
        bt_helpline4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                makeCall("tel:8885693793");//replace with 1096
            }
        });
        bt_helpline5 = (Button) rootView.findViewById(R.id.helpline5);
        bt_helpline5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                makeCall("tel:8885693793");//replace with 102
            }
        });
        bt_helpline6 = (Button) rootView.findViewById(R.id.helpline6);
        bt_helpline6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                makeCall("tel:8885693793");//replace with 112
            }
        });
        return rootView;
    }

    private void makeCall(String phoneNumber) {
        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumber));
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(i);
    }
}
