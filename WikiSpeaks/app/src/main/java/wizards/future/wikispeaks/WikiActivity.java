package wizards.future.wikispeaks;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public abstract class WikiActivity extends ActionBarActivity {

    @Override
    protected abstract void onCreate(Bundle savedInstanceState);

    @Override
    protected abstract void onPause();

    @Override
    protected abstract void onResume();

    @Override
    protected abstract void onStop();

    @Override
    protected abstract void onStart();

}
