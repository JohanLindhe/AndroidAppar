package com.example.lastfm;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ApiManager {
    private final String API_KEY = "e0183c17926f9c9d548a032b23ad8ec1";

    public List<String> searchSimilarArtists(String artist) {
        List<String> artistNames = new ArrayList<>();

        try {
            String apiString = "https://ws.audioscrobbler.com/2.0/?method=artist.getsimilar&artist=" + artist + "&api_key=" + API_KEY + "&format=xml";
            URL url = new URL(apiString);
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            String tagName = null;

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                if (parserEvent == XmlPullParser.START_TAG) {
                    tagName = parser.getName();
                    if (tagName.contains("name")) {
                        String name = parser.nextText();
                        artistNames.add(name);
                    }
                }
                parserEvent = parser.next();
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

        return artistNames;
    }
}
