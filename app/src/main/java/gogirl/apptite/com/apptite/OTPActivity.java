package gogirl.apptite.com.apptite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

public class OTPActivity extends AppCompatActivity
{
    private EditText otp;
    Button confirm;
    CircularProgressView progressView;
    private PrefManager prefManager;
    private String name;
    private String OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otp = (EditText)findViewById(R.id.otp);
        confirm = (Button)findViewById(R.id.btn_confirm);
        progressView = (CircularProgressView)findViewById(R.id.progress_view);

        Intent intent = getIntent();
        OTP = intent.getStringExtra("OTP");
        name = intent.getStringExtra("name");

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressView.setVisibility(View.VISIBLE);

                if(OTP.equals("no_otp") || OTP.equals("runtime_error"))
                {
                    Toast.makeText(OTPActivity.this,"Please retry Loging In or contact admin",Toast.LENGTH_SHORT).show();
                    progressView.setVisibility(View.INVISIBLE);
                }
                else if(OTP.equals(otp.getText().toString().trim()))
                {
                    Toast.makeText(OTPActivity.this,"Login Success!",Toast.LENGTH_SHORT).show();
                    Log.v(name,"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
                    startActivity(new Intent(OTPActivity.this,Menu.class).putExtra("name",name));
                }
                else
                {
                    Toast.makeText(OTPActivity.this,"Incorrect OTP! Kindly recheck!",Toast.LENGTH_SHORT).show();
                    progressView.setVisibility(View.INVISIBLE);
                }
            }
        });

    }
}


