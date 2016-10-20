package gogirl.apptite.com.apptite;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sendgrid.SendGrid;

import gogirl.apptite.com.apptite.Connectivity.InternetConnectivity;

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

public class SignupActivity extends Activity {

    String echo_data;
    private EditText inputName, inputEmail;
    private TextInputLayout inputLayoutName, inputLayoutEmail;
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);

        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);

        btnSignUp = (Button) findViewById(R.id.btn_signup);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(new InternetConnectivity().checkConnectivity(SignupActivity.this))
                {
                    if(validateName() && validateEmail())
                        sendDataToServer(inputName,inputEmail);
                    else
                        inputLayoutEmail.setError(getString(R.string.err_msg_email));

                }
                else
                {
                    Toast.makeText(SignupActivity.this,"Cant connect to internet",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /*
     * Sending data to server
     */
    private void sendDataToServer(EditText name,EditText email)
    {
        class Sendingdata extends AsyncTask<String,Void,Void>
        {

            @Override
            protected Void doInBackground(String... params)
            {
                try
                {
                    URL url = new URL(getString(R.string.SignUpURL));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    List<NameValuePair> param = new ArrayList<NameValuePair>();
                    param.add(new BasicNameValuePair("name", params[0]));
                    param.add(new BasicNameValuePair("email", params[1]));
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

                    switch (echo_data)
                    {
                        case "101":
                            new Handler(Looper.getMainLooper()).post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(SignupActivity.this, "No DB Connection", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "102":
                            new Handler(Looper.getMainLooper()).post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(SignupActivity.this, "No Parameters", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "103":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignupActivity.this,"User already exists! Please SignUp with a different address",Toast.LENGTH_SHORT).show();
                                }
                            });

                            break;
                        case "104":
                            new Handler(Looper.getMainLooper()).post(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(SignupActivity.this, "Sign Up Success...Please Login!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                            break;
                    }
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

        Sendingdata sd = new Sendingdata();
        sd.execute(name.getText().toString(),email.getText().toString());
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}