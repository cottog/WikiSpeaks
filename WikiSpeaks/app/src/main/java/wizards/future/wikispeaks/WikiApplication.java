package wizards.future.wikispeaks;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Shane on 3/13/2015.
 */
public class WikiApplication extends Application {
    private Activity currentActivity = null;



    public void onCreate(){
        super.onCreate();

    }

    public synchronized void setCurrentActivity(Activity currentActivity){
        this.currentActivity = currentActivity;
    }


}
