package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView artistContent;
    private EditText artistInput;
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        artistContent = findViewById(R.id.artistContent);
        artistInput = findViewById(R.id.artistInput);
        apiManager = new ApiManager();
    }

    public void searchFunction(View view) {

        String artist = artistInput.getText().toString();
        List<String> artistNames = apiManager.searchSimilarArtists(artist);

        StringBuilder namesText = new StringBuilder("Names:\n");
        int maxNames = Math.min(artistNames.size(), 8);

        for (int i = 0; i < maxNames; i++) {
            namesText.append(artistNames.get(i)).append("\n");
        }

        artistContent.setText(namesText.toString());
    }

    public void clearText(View view) {
        artistContent.setText("");
        artistInput.setText("");
    }
}
