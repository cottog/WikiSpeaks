package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import wizards.future.wikispeaks.R;
import wizards.future.wikispeaks.WikiApplication;

public class WikiSearchActivity extends ActionBarActivity {
    private WikiApplication mApplication = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_search);

        mApplication = (WikiApplication) getApplication();
        mApplication.setCurrentActivity(this);
    }

    public void searchButtonHandler(View v){
        //Get info from text input and send to JSON object and parse with Thread7
    }

}
