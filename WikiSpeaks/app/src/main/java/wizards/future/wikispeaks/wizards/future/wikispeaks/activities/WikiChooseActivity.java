package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import wizards.future.wikispeaks.R;
import wizards.future.wikispeaks.WikiApplication;

public class WikiChooseActivity extends ActionBarActivity {
    private WikiApplication mApplication = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_choose);

        mApplication = (WikiApplication) getApplication();
        mApplication.setCurrentActivity(this);
    }

    public void titleButtonHandler(View v){
        Intent intent = new Intent(this, WikiSearchActivity.class);
        startActivity(intent);
    }

    public void categoryButtonHandler(View v){
        //Intent intent = new Intent(this, WikiCategoryActivity.class);
        //startActionMode(intent);
    }
}
