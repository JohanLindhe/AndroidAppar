package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlPullParserException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


// API key	e0183c17926f9c9d548a032b23ad8ec1
//Shared secret	63ba643e436cec37b23ac09557a871ee
//Application name	xmlAPI
//Registered to	Johanapi

public class MainActivity extends AppCompatActivity {
    private TextView artistContent;
    private final String API_KEY = "e0183c17926f9c9d548a032b23ad8ec1";

    private EditText artistInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        artistContent = findViewById(R.id.artistContent);
        artistInput = findViewById(R.id.artistInput);
    }


    public void searchFunction(View view) {
        Toast.makeText(this, "Knappen tryckt", Toast.LENGTH_SHORT).show();

        String artist = artistInput.getText().toString();
        URL url;
        String apiString = "https://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" + artist + "&api_key=" + API_KEY + "&format=xml";

        // Hämtar från api
        try {
            url = new URL(apiString);
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            String tagName = null;
            List<String> artistNames = new ArrayList<>(); // Lägger alla namn i här

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                if (parserEvent == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    Log.d("XML", "Start tag: " + tagName);

                    if (tagName.contains("name")) {
                        String name = parser.nextText();
                        Log.d("XML", "Start tag: " + name);
                        artistNames.add(name); // Lägger in alla namn som skickas tillbaka.
                    }
                }

                parserEvent = parser.next();
            }


            StringBuilder namesText = new StringBuilder("Names:\n");
            int maxNames = Math.min(artistNames.size(), 8); // Så loopen bryter vid 8st

            for (int i = 0; i < maxNames; i++) {
                namesText.append(artistNames.get(i)).append("\n");
            }

            artistContent.setText(namesText.toString());

        } catch (Exception e) {

        }
    }

    // Rensar resultat och sökfält
    public void clearText(View view) {

        artistContent.setText("");
        artistInput.setText("");
    }
}

