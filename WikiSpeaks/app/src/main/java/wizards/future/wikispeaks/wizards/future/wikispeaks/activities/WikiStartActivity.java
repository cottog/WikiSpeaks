package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import wizards.future.wikispeaks.R;
import wizards.future.wikispeaks.WikiApplication;

public class WikiStartActivity extends ActionBarActivity {
    private static int SPLASH_TIME = 2900;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_wiki_start);

       ImageView logoImage = (ImageView)findViewById(R.id.logoView);
       logoImage.setBackgroundResource(R.drawable.logo_animation);

       AnimationDrawable frameAnimation = (AnimationDrawable) logoImage.getBackground();
       frameAnimation.start();

       new Handler().postDelayed(new Runnable(){
           //Show Splash Screen For 2.25 seconds
            @Override
            public void run() {
                Intent i = new Intent(WikiStartActivity.this,WikiSearchActivity.class);
                startActivity(i);

                finish();
            }

       }, SPLASH_TIME);
    }
}
