package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import wizards.future.wikispeaks.R;

public class WikiStartActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_start);
    }

    public void startButtonHandler(View v){
        Intent intent = new Intent(this,WikiChooseActivity.class);
        startActivity(intent);
    }
}
