package gogirl.apptite.com.apptite.contact_friends;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.github.paolorotolo.appintro.AppIntro;

import gogirl.apptite.com.apptite.contact_friends.slides.FirstSlide;
import gogirl.apptite.com.apptite.contact_friends.slides.FourthSlide;
import gogirl.apptite.com.apptite.contact_friends.slides.SecondSlide;
import gogirl.apptite.com.apptite.contact_friends.slides.ThirdSlide;

/*
 * Activity of loading Circle of Trusts' introductory views
 *
 * @author calistus
 * @since 2015-08-18
 */
public class ContactFriendsIntro extends AppIntro {
    public SharedPreferences settings;
    public boolean firstRun;

    @Override
    public void init(Bundle savedInstanceState) {

        settings = getSharedPreferences("prefs", 0);
        firstRun = settings.getBoolean("firstRun", true);
        if (firstRun) {
            addSlide(new FirstSlide(), getApplicationContext());
            addSlide(new SecondSlide(), getApplicationContext());
            addSlide(new ThirdSlide(), getApplicationContext());
            addSlide(new FourthSlide(), getApplicationContext());

            setFadeAnimation();

        } else{ loadMainActivity();}
    }

    /**
     * Loads the regular activity if the first run is skipped or finished.
     */
    private void loadMainActivity() {

        settings = getSharedPreferences("prefs", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("firstRun", false);
        editor.commit();
        Intent intent = new Intent();
        setResult(2, intent);
        finish();//finishing activity
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();

    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
