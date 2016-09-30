package gogirl.apptite.com.apptite;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

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

/**
 * Created by User on 16-Sep-16.
 */
public class Dashboard extends AppCompatActivity
{

    TextView modeOfTheDay;
    String echo_data;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        modeOfTheDay = (TextView)findViewById(R.id.move_of_the_day);

        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10000);
                        runOnUiThread(new Runnable() {
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

                    Log.v("Error Code ######",echo_data);
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
        modeOfTheDay.setText(echo_data);
    }
}
