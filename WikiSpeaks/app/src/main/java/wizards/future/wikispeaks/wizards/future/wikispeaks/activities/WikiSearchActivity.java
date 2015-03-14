package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import wizards.future.wikispeaks.R;
import wizards.future.wikispeaks.WikiApplication;

public class WikiSearchActivity extends ActionBarActivity {
    private WikiApplication mApplication = null;
    private EditText mEditText;
    private String EXTRA_MESSAGE= "wizards.future.wikispeaks";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_search);
        mEditText = (EditText) findViewById(R.id.editText);
        mApplication = (WikiApplication) getApplication();
        mApplication.setCurrentActivity(this);
    }

    public void searchButtonHandler(View v){
        //Get info from text input and send to JSON object and parse with Thread7
        final String articleTitle = mEditText.getText().toString();
        final String articleTitleFormatted = articleTitle.replaceAll(" ","_");


        Thread checkerThread = new Thread(new Runnable(){

            public void run(){

                /*runOnUiThread(new Runnable(){
                    public void run(){
                        String thisIsATest = "The button works!!!\n" + articleTitle;
                        Toast toast = Toast.makeText(getApplicationContext(), thisIsATest, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });*/

                //check to see if everything is well formatted
                if(articleTitle.length() < 0){
                    //can't find an article with an empty title
                    return;
                }
                URL url;
                BufferedReader reader = null;
                StringBuilder builder = new StringBuilder();
                try{
                    String urlString = "http://en.wikipedia.org/w/api.php?format=json&action=parse&prop=wikitext&redirects&page=" + articleTitleFormatted;
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

                //begin matching
                String REGEX = ".*missingtitle.*";
                Pattern p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(JSON); // get a matcher object
                boolean missingTitle = m.matches();

                REGEX = ".*may refer to:.*";
                p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                m = p.matcher(JSON);
                boolean disambiguation = m.matches();

                if(missingTitle){
                    //make a toast
                    runOnUiThread(new Runnable(){
                        public void run(){
                            String missingTitleError = getString(R.string.missing_title) + " \"" + articleTitle + "\"";
                            Toast toast = Toast.makeText(getApplicationContext(), missingTitleError, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                }
                else if(disambiguation){
                    runOnUiThread(new Runnable(){
                        public void run(){
                            String disambiguationError = getString(R.string.disambiguation_pt1) + " \"" + articleTitle + "\" " + getString(R.string.disambiguation_pt2);
                            Toast toast = Toast.makeText(getApplicationContext(), disambiguationError, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable(){
                        public void run(){
                            goToArticle(articleTitleFormatted);
                        }
                    });

                }
            }
        });
        checkerThread.start();
    }
    private void goToArticle(String articleTitle){
        Intent intent = new Intent(this, WikiReadActivity.class);
        intent.putExtra(EXTRA_MESSAGE, articleTitle);

        startActivity(intent);
    }


    public void randomButtonHandler(View v){
        Thread checkerThread = new Thread(new Runnable(){

            public void run(){

                /*runOnUiThread(new Runnable(){
                    public void run(){
                        String thisIsATest = "The button works!!!\n" + articleTitle;
                        Toast toast = Toast.makeText(getApplicationContext(), thisIsATest, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });*/

                //check to see if everything is well formatted
                URL url;
                BufferedReader reader = null;
                StringBuilder builder = new StringBuilder();
                try{
                    String urlString = "https://en.wikipedia.org/w/api.php?action=query&list=random&rnlimit=1&format=json&continue&rnnamespace=0";
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
        checkerThread.start();
    }
}
