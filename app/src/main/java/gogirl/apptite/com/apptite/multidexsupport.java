package gogirl.apptite.com.apptite;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by akanksha bodhankar on 27-09-2016.
 */
public class multidexsupport extends Application{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
    }

}
