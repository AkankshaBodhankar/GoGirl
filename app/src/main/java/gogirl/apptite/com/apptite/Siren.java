package gogirl.apptite.com.apptite;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ToggleButton;

/**
 * Created by User on 16-Sep-16.
 */
public class Siren extends AppCompatActivity {

    ToggleButton toggleButton;
    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_siren);



            toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
              mp = MediaPlayer.create(Siren.this, R.raw.sirenpolice);


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






    }


}
