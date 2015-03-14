package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import wizards.future.wikispeaks.R;
import wizards.future.wikispeaks.WikiApplication;

public class WikiStartActivity extends ActionBarActivity {
    private WikiApplication mApplication = null;
    private ImageButton mHandsFreeButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_start);

        mApplication = (WikiApplication) getApplication();
        mApplication.setCurrentActivity(this);

        /*
        mHandsFreeButton = (ImageButton)findViewById(R.id.handsButton);
        if(!mApplication.isHandsFree()){
            mHandsFreeButton.setBackgroundResource(R.drawable.wikispeakshandsfree);
        }*/
    }

    public void startButtonHandler(View v){
        Intent intent = new Intent(this,WikiChooseActivity.class);
        startActivity(intent);
    }

    public void handsFreeButtonHandler(View v){
        if(!mApplication.isHandsFree()){
            mHandsFreeButton.setBackgroundResource(R.drawable.wikispeakshandsfreeon);
            mApplication.setHandsFree(true);
        }else{
            mHandsFreeButton.setBackgroundResource(R.drawable.wikispeakshandsfree);
            mApplication.setHandsFree(false);
        }
    }
}
