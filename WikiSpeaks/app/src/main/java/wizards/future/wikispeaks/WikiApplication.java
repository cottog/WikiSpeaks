package wizards.future.wikispeaks;

import android.app.Activity;
import android.app.Application;

/**
 * Created by Shane on 3/13/2015.
 */
public class WikiApplication extends Application {
    private Activity mCurrentActivity = null;
    private boolean mHandsFree = false;

    public void onCreate(){
        super.onCreate();

    }

    public synchronized void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    public boolean isHandsFree(){
        return mHandsFree;
    }

    public void setHandsFree(boolean mHandsFree){
        this.mHandsFree = mHandsFree;
    }


}
