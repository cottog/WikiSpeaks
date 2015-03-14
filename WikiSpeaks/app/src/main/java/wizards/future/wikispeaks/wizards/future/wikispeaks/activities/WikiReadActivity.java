package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import wizards.future.wikispeaks.R;

public class WikiReadActivity extends ActionBarActivity {
    private String mArticleTitle;
    private String EXTRA_MESSAGE = "wizards.future.wikispeaks";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_read);
        Intent intent = getIntent();
        mArticleTitle = intent.getStringExtra(EXTRA_MESSAGE);
        pullFromNetwork();
    }

    private void pullFromNetwork(){


        Thread wikiThread = new Thread(new Runnable(){
            public void run(){
                //check to see if everything is well formatted
                if(mArticleTitle.length() > 0){
                    //can't find an article with an empty title
                    return;
                }
                URL url;
                BufferedReader reader = null;
                StringBuilder builder = new StringBuilder();
                try{
                    String urlString = "http://en.wikipedia.org/w/api.php?format=json&action=parse&prop=wikitext&redirects&page=" + mArticleTitle;
                    url = new URL(urlString);
                }
                catch(MalformedURLException e){
                    //bad URL format
                    e.printStackTrace();
                    return;
                }
                //reading the file
                try{
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    while((line = reader.readLine()) != null){
                        builder.append(line);
                    }
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                finally{
                    if(reader != null){
                        try{
                            reader.close();
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }

                String JSON = builder.toString();
            }
        });
        wikiThread.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wiki_read, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
