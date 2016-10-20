package gogirl.apptite.com.apptite;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private Toolbar toolbar;
    String echo_data;
    private EditText inputEmail;
    private TextInputLayout inputLayoutEmail;
    private Button btnLogin;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);


        inputEmail = (EditText) findViewById(R.id.input_email);

        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_Login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEmail())
                    submitForm();
            }
        });
    }

    /*
     * Sending data to server
     */
    private void sendDataToServer(EditText email)
    {
        class Sendingdata extends AsyncTask<String,Void,Void>
        {

            @Override
            protected Void doInBackground(String... params) {
                try
                {
                    URL url = new URL("http://virtusa.azurewebsites.net/login.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    List<NameValuePair> param = new ArrayList<NameValuePair>();

                    param.add(new BasicNameValuePair("email", params[0]));

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

                    switch (echo_data.split(",")[0])
                    {
                        case "101":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "No DB conn", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "102":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "No Parameters", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "103":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this, "No such User! Please SignUp!", Toast.LENGTH_SHORT).show();
                                }
                            });
                            break;
                        case "104":
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"Success!",Toast.LENGTH_SHORT).show();
                                }
                            });
                            startActivity(new Intent(LoginActivity.this,OTPActivity.class).putExtra("OTP",sendAndGetOTP(params[0])).putExtra("name",echo_data.split(",")[1]));
                            break;
                        case "105":
                            new Handler((Looper.getMainLooper())).post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginActivity.this,"No such user!Please SignUp!",Toast.LENGTH_SHORT).show();
                                }
                            });
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

            private String sendAndGetOTP(String toAddress)
            {
                String mMsgResponse="";

                int otp = 100000 + new Random().nextInt(900000);
                try
                {
                    SendGrid sendgrid = new SendGrid("virtusa_apptite", "sandiv64");

                    SendGrid.Email email = new SendGrid.Email();

                    email.addTo(toAddress);
                    email.setFrom("virtusa@apptite.com");
                    email.setSubject("OTP for GoGirl");
                    email.setText("Thank You for using GoGirl"+"\n\n\n"+"Your unique OTP is :"+"\t"+otp+"\n\n"+"Please DO NOT DELETE this email because this OTP is required for you to login");

                    SendGrid.Response response = sendgrid.send(email);
                    mMsgResponse = response.getMessage();

                    Log.d("SendAppExample########", mMsgResponse);
                    if(mMsgResponse.contains("success"))
                    {
                        return new Integer(otp).toString();
                    }
                    else
                    {
                        return "no_otp";
                    }

                }
                catch (Exception e)
                {
                    return "runtime_error";
                }


                //return mMsgResponse;
            }
        }
        Sendingdata sd = new Sendingdata();
        sd.execute(email.getText().toString().trim());
    }

    /*
     * Validating form
     */
    private void submitForm() {
        if(new InternetConnectivity().checkConnectivity(LoginActivity.this))
        {
            sendDataToServer(inputEmail);
        }
        else
        {
            Toast.makeText(LoginActivity.this,"Cant connect to internet",Toast.LENGTH_LONG).show();
        }
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(LoginActivity.this, Menu.class));
        finish();
    }


    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        }
        else
        {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}