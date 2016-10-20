package gogirl.apptite.com.apptite;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LearnFragment extends Fragment {


    TextView moveOfTheDay;
    String echo_data;
    Button learn1;
    Button learn2;

    public LearnFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_move, container, false);
        moveOfTheDay = (TextView)rootView.findViewById(R.id.move_of_the_day);

        learn1 = (Button)rootView.findViewById(R.id.button);
        learn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.wikihow.com/Teach-Yourself-the-Basics-of-Karate"));
                startActivity(intent);
            }
        });

        learn2 = (Button)rootView.findViewById(R.id.button1);
        learn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.youtube.com/watch?v=067C9Yk_1po"));
                startActivity(intent);
            }
        });


        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10000);
                        if(getActivity() == null)
                            return;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateMoveOfDay();

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
        return rootView;
    }

    void updateMoveOfDay()
    {
        class ServerConnect extends AsyncTask<Integer,Void,Void>
        {

            @Override
            protected Void doInBackground(Integer... params) {
                try
                {
                    URL url = new URL("http://virtusa.azurewebsites.net/dashboard.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(5000);
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    List<NameValuePair> param = new ArrayList<NameValuePair>();
                    param.add(new BasicNameValuePair("randomizer", params[0].toString()));


                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(getQuery(param));
                    writer.flush();
                    writer.close();
                    os.close();

                    conn.connect();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line);
                    }
                    echo_data = sb.toString();

                    Log.v("Error Code ######", echo_data);
                }
                catch (Exception ex)
                {
                    Log.v("Exception",ex.toString());
                }
                return null;
            }
            private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
            {
                StringBuilder result = new StringBuilder();
                boolean first = true;

                for (NameValuePair pair : params)
                {
                    if (first)
                        first = false;
                    else
                        result.append("&");

                    result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                    result.append("=");
                    result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
                }

                return result.toString();
            }
        }
        new ServerConnect().execute(new Integer(new Random().nextInt(11)+1));
        moveOfTheDay.setText(echo_data);
    }
}

