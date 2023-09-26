package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import java.util.Random;
import android.view.inputmethod.InputMethodManager;
import android.content.Context;
public class MainActivity extends AppCompatActivity {

    //Deklarerar
    private String[] guessWords = {"karlstad", "hello", "football", "car", "school", "lion", "apple", "orange"};
    private String selectGuessWord;
    private StringBuilder showWord;
    private int lives;
    private TextView wordTextView, livesTextView, Won, Lost;
    private EditText Guesses;
    private Button guessBtn;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_layout);

        //Hämtar in allt

        wordTextView = findViewById(R.id.textView2);
        livesTextView = findViewById(R.id.textView3);
        Guesses = findViewById(R.id.inputGuess);
        guessBtn = findViewById(R.id.button1);
        restartButton = findViewById(R.id.button2);
        Won = findViewById(R.id.Win);
        Lost = findViewById(R.id.Lose);

        startGame(); // När spelet startas
    }

    private void startGame() {
        Random random = new Random(); // Skapar en slump
        selectGuessWord = guessWords[random.nextInt(guessWords.length)]; // Hämtar ett slumpord från vektorn
        showWord = new StringBuilder();

        //
        for (int i = 0; i < selectGuessWord.length(); i++) {
            showWord.append("_ ");
        }

        Won.setVisibility(View.GONE);
        Lost.setVisibility(View.GONE);

        lives = 7;
        setLives();

    }
    private void setLives(){
        wordTextView.setText(showWord.toString());
        livesTextView.setText("Guesses Left: " + lives);
    }

    public void makeGuess(View view) {

        String guess = Guesses.getText().toString().toLowerCase();
        Guesses.setText("");

        // Om man gissar mer än en bokstav
        if (guess.length() != 1) {
            Toast.makeText(this, "Just one letter", Toast.LENGTH_SHORT).show();
            return;
        }

        char letGuessed = guess.charAt(0);
        boolean bolGuess = false;

        // Testar om bokstaven finns med i ordet, om ja visar det annars tas ett liv bort
        for (int i = 0; i < selectGuessWord.length(); i++)
            if (selectGuessWord.charAt(i) == letGuessed) {
                showWord.setCharAt(i * 2, letGuessed);
                bolGuess = true;

            }


        if (!bolGuess) {
            lives--;

        }

        // Stänger tangentbord när man gissar på en bokstav.
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        // Minskar ens liv
        setLives();

        // Kontrollera om man vinner eller förlorar
        if (showWord.toString().replace(" ", "").equals(selectGuessWord)) {

            Won.setVisibility(View.VISIBLE);
        } else if (lives == 0) {
            Lost.setText("You Lost! The word was: " + selectGuessWord);
            Lost.setVisibility(View.VISIBLE);
        } else if (lives <= 0) { // Om man fortsätter gissa trots förlust
            startGame();
        }
    }

    public void restartGame(View view){
        startGame();
        Toast.makeText(this, "New Game!", Toast.LENGTH_LONG).show();
    }

}