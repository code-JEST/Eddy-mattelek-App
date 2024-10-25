package com.example.mappe1s374946;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Spillskjerm extends BaseActivity implements MinDialog.MittInterface {


    private TextView mathQuestion;
    private EditText answerField;
    private int[] mathAnswers;
    private String[] mathQuestions;
    private int currentIndex = 0;
    int numTasks;

    private List<String> riktig_mld;
    private List<String> feil_mld;


    //avslutte spillet med en dialog
    @Override
    public void onYesClick() {
        finish();
    }
    @Override
    public void onNoClick() {
        return;
    }
    public void visDialog(View v)
    {
        DialogFragment dialog = new MinDialog();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spillskjerm);

        // Hent arrays fra strings.xml
        riktig_mld = Arrays.asList(getResources().getStringArray(R.array.riktig_mld));
        feil_mld = Arrays.asList(getResources().getStringArray(R.array.feil_mld));

        mathQuestion = findViewById(R.id.mathQuestion);
        answerField = findViewById(R.id.answerField);

        // gjenoppretter fra en tidligere tilstand
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("QuestionIndex");
            numTasks = savedInstanceState.getInt("NumTasks");
            mathQuestions = savedInstanceState.getStringArray("MathQuestions");
            mathAnswers = savedInstanceState.getIntArray("MathAnswers");
        }
        else {
            // Hvis dette er et nytt oppstart, hent spørsmål og svar
            mathQuestions = getResources().getStringArray(R.array.math_questions);
            mathAnswers = getResources().getIntArray(R.array.math_answers);

            // Konverter arrayer til en liste
            List<Pair<String, Integer>> questionAnswerPairs = new ArrayList<>();
            for (int i = 0; i < mathQuestions.length; i++) {
                questionAnswerPairs.add(new Pair<>(mathQuestions[i], mathAnswers[i]));
            }

            // Blande listen
            Collections.shuffle(questionAnswerPairs);

            // Hent antall spørsmål fra innstillingene
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String numTasksString = sharedPreferences.getString("tasks", "5");
            numTasks = Integer.parseInt(numTasksString);

            // Splitte listen tilbake til to separate lister
            mathQuestions = new String[numTasks];
            mathAnswers = new int[numTasks];
            for (int i = 0; i < numTasks; i++) {
                mathQuestions[i] = questionAnswerPairs.get(i).first;
                mathAnswers[i] = questionAnswerPairs.get(i).second;
            }
        }



        // Vis første matteoppgave
        showMathQuestion();

        ImageButton tilbakeKnapp = findViewById(R.id.tilbakeKnapp);
        tilbakeKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visDialog(view);
            }
        });


        //knapper og innskriving
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button button0 = findViewById(R.id.button0);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String buttonText = b.getText().toString();
                String existingText = answerField.getText().toString();
                answerField.setText(existingText + buttonText);
            }
        };

        button1.setOnClickListener(buttonClickListener);
        button2.setOnClickListener(buttonClickListener);
        button3.setOnClickListener(buttonClickListener);
        button4.setOnClickListener(buttonClickListener);
        button5.setOnClickListener(buttonClickListener);
        button6.setOnClickListener(buttonClickListener);
        button7.setOnClickListener(buttonClickListener);
        button8.setOnClickListener(buttonClickListener);
        button9.setOnClickListener(buttonClickListener);
        button0.setOnClickListener(buttonClickListener);


        // Sjekk svar-knappen
        Button checkAnswerButton = findViewById(R.id.checkAnswerButton);
        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Lagre nåværende oppgaveindeks
        savedInstanceState.putInt("QuestionIndex", currentIndex);
        savedInstanceState.putInt("NumTasks", numTasks);
        // Lagre spørsmål og svar
        savedInstanceState.putStringArray("MathQuestions", mathQuestions);
        savedInstanceState.putIntArray("MathAnswers", mathAnswers);
    }

    // Funksjon for å vise matteoppgave
    private void showMathQuestion() {
        if (currentIndex < numTasks) {
            mathQuestion.setText(mathQuestions[currentIndex]);
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.alleSvart), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -400);
            toast.show();
        }
    }


    // Funksjon for å sjekke brukerens svar
    private void checkAnswer() {
        String userInput = answerField.getText().toString();
        if (userInput.isEmpty()) {
            Toast toast = Toast.makeText(this, getString(R.string.ikkeGydligSvar), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -400);
            toast.show();
            return;
        }

        int userAnswer = Integer.parseInt(userInput);
        if (userAnswer == mathAnswers[currentIndex]) {
            String message = riktig_mld.get(new Random().nextInt(riktig_mld.size()));
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -400);
            toast.show();

            // Gå til neste matteoppgave
            currentIndex++;
        } else {
            String message = feil_mld.get(new Random().nextInt(feil_mld.size()));
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -400);
            toast.show();
        }


        if (currentIndex < numTasks) {
            showMathQuestion();
            answerField.getText().clear(); // Tøm inputfeltet
        } else {
            Toast toast = Toast.makeText(this, getString(R.string.fullførtAlle), Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, -400);
            toast.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(Spillskjerm.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000); // Forsinkelse på 3 sekunder før man går tilbake til hovedskjerm
        }
    }
}