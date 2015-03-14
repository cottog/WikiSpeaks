package wizards.future.wikispeaks.wizards.future.wikispeaks.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.speech.tts.TextToSpeech;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import wizards.future.wikispeaks.R;

public class WikiReadActivity extends ActionBarActivity implements TextToSpeech.OnInitListener {
    private String mArticleTitle;
    private String EXTRA_MESSAGE = "wizards.future.wikispeaks";
    private TextToSpeech tts;
    TextView testText;
    ArrayList<String> mArticleList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki_read);
        tts = new TextToSpeech(this,this);
        Intent intent = getIntent();
        mArticleTitle = intent.getStringExtra(EXTRA_MESSAGE);
        testText = (TextView) findViewById(R.id.testText);

        mArticleList = new ArrayList<>();
        //testText.setText(mArticleTitle);

        //getNumSections();
        //pullFromNetwork(0);
        doSomethingDifferent();
    }

    public void doSomethingDifferent(){
        Thread wikiThread = new Thread(new Runnable(){
            public void run(){

                if(mArticleTitle.length() < 0){
                    //can't find an article with an empty title
                    return;
                }
                int secNum = 0;
                while(true) {
                    URL url;
                    BufferedReader reader = null;
                    StringBuilder builder = new StringBuilder();
                    try {
                        String urlString = "http://en.wikipedia.org/w/api.php?format=json&action=parse&prop=wikitext&redirects&page=" + mArticleTitle + "&section=" + secNum;
                        url = new URL(urlString);
                    } catch (MalformedURLException e) {
                        //bad URL format
                        e.printStackTrace();
                        return;
                    }
                    //reading the file
                    try {
                        reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (reader != null) {
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    postDebug("Section number " + Integer.toString(secNum) + " has been loaded.");

                    String jsonString = builder.toString();
                    String REGEX = ".*nosuchsection.*";
                    Pattern p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(jsonString); // get a matcher object
                    boolean noSectionBoolean = m.matches();
                    if(noSectionBoolean){
                        break;
                    }

                    REGEX = ".*==See Also==.*";
                    p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(jsonString);
                    boolean seeAlsoBoolean = m.matches();
                    if(seeAlsoBoolean){
                        break;
                    }

                    REGEX = ".*==References==.*";
                    p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(jsonString);
                    boolean referencesBoolean = m.matches();
                    if(referencesBoolean){
                        break;
                    }

                    REGEX = ".*==Bibliography==.*";
                    p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(jsonString);
                    boolean bibliographyBoolean = m.matches();
                    if(bibliographyBoolean){
                        break;
                    }

                    REGEX = ".*==External Links==.*";
                    p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(jsonString);
                    boolean externalLinksBoolean = m.matches();
                    if(externalLinksBoolean){
                        break;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        jsonString = jsonObject.getJSONObject("parse").getJSONObject("wikitext").getString("*");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    jsonString = jsonString.replaceAll("\\{\\{.*?\\}\\}"," ");
                    jsonString = jsonString.replaceAll("<.*>"," ");
                    jsonString = jsonString.replaceAll("\\[","");
                    jsonString = jsonString.replaceAll("\\]","");
                    jsonString = jsonString.replaceAll("=","");
                    jsonString = jsonString.replaceAll("|","");
                    if (tts!= null) {
                        if(secNum == 0){
                            tts.speak(jsonString,TextToSpeech.QUEUE_FLUSH,null);
                        }else{
                            tts.speak(jsonString,TextToSpeech.QUEUE_ADD,null);
                        }
                    }
                    secNum++;
                }

                //text to speech goes here

            }


        });
        wikiThread.start();
    }

    public void getNumSections(){
        Thread wikiThread = new Thread(new Runnable(){
            public void run(){

                /*runOnUiThread(new Runnable(){
                    public void run(){
                        String thisIsATest = "The thread works!!!";
                        Toast toast = Toast.makeText(getApplicationContext(), thisIsATest, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });*/

                //check to see if everything is well formatted
                if(mArticleTitle.length() > 0){
                    //can't find an article with an empty title
                    return;
                }
                URL url;
                BufferedReader reader = null;
                StringBuilder builder = new StringBuilder();
                int numSections = 0;
                try {
                    String urlString = "http://en.wikipedia.org/w/api.php?format=json&action=parse&prop=wikitext&redirects&page=" + mArticleTitle;
                    url = new URL(urlString);
                } catch (MalformedURLException e) {
                    //bad URL format
                    e.printStackTrace();
                    return;
                }
                //reading the file
                try {
                    reader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String REGEX = ".*nosuchsection.*";
                        Pattern p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(line); // get a matcher object
                        boolean noSectionBoolean = m.matches();
                        if(noSectionBoolean){
                            break;
                        }

                        REGEX = ".*==See Also==.*";
                        p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                        m = p.matcher(line);
                        boolean seeAlsoBoolean = m.matches();
                        if(seeAlsoBoolean){
                            break;
                        }

                        REGEX = ".*==References==.*";
                        p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                        m = p.matcher(line);
                        boolean referencesBoolean = m.matches();
                        if(referencesBoolean){
                            break;
                        }

                        REGEX = ".*==Bibliography==.*";
                        p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                        m = p.matcher(line);
                        boolean bibliographyBoolean = m.matches();
                        if(bibliographyBoolean){
                            break;
                        }

                        REGEX = ".*==External Links==.*";
                        p = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE);
                        m = p.matcher(line);
                        boolean externalLinksBoolean = m.matches();
                        if(externalLinksBoolean){
                            break;
                        }
                        numSections++;
                        builder.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                    //String JSON = builder.toString();

                    //begin matching


                /*final int numSectionsFinal = numSections;
                runOnUiThread(new Runnable(){
                    public void run(){
                        //doArrayStuff(numSectionsFinal);
                        mArticleList = new String[numSectionsFinal];
                    }
                });*/
            }
        });
        wikiThread.start();
    }

    public void pullFromNetwork(int sectionNum){
        final int numSection = sectionNum;

        Thread wikiThread = new Thread(new Runnable(){
            public void run(){

                runOnUiThread(new Runnable(){
                    public void run(){
                        String thisIsATest = "The thread works!!!";
                        Toast toast = Toast.makeText(getApplicationContext(), thisIsATest, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                //check to see if everything is well formatted
                if(mArticleTitle.length() > 0){
                    //can't find an article with an empty title
                    return;
                }
                URL url;
                BufferedReader reader = null;
                StringBuilder builder = new StringBuilder();
                try{
                    String urlString = "http://en.wikipedia.org/w/api.php?format=json&action=parse&prop=wikitext&redirects&page=" + mArticleTitle
                            + "&section=" + numSection;
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

                try{
                    JSONObject JSONObj = null;
                    JSONObj = new JSONObject(JSON);
                    JSON = JSONObj.getJSONObject("parse").getJSONObject("wikitext").getString("*");
                } catch (JSONException ex){
                    ex.printStackTrace();
                }
                final String finalJSON = JSON;
                /*runOnUiThread(new Runnable(){
                    public void run(){
                        mArticleList[numSection] = finalJSON;
                        test();
                    }
                });*/
            }
        });
        wikiThread.start();
    }

    /*public void test(){
        String thisIsATest = mArticleList.length + "; \n[0] = " + mArticleList[0];
        Toast toast = Toast.makeText(getApplicationContext(), thisIsATest, Toast.LENGTH_SHORT);
        toast.show();
    }*/

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

    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        tts.shutdown();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS ){
            tts.setLanguage(Locale.getDefault());
        } else {
            tts = null;
            Toast.makeText(this, "Failed to initialize", Toast.LENGTH_SHORT).show();
        }
    }

    public void postDebug(final String s){
        Runnable displayDebug = new Runnable() {
            @Override
            public void run() {
                testText.setText(s + "\n"+ testText.getText());
            }
        };
        testText.post(displayDebug);
    }
}
