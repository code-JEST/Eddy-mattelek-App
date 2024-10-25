package com.example.mappe1s374946;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.ImageButton;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;


public class MainActivity extends BaseActivity {
    Button KnappStartSpillet;
    Button KnappOmSpillet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //start spill knapp
        KnappStartSpillet = (Button) findViewById(R.id.KnappStartSpill);
        KnappStartSpillet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Spillskjerm.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });


        KnappOmSpillet = (Button) findViewById(R.id.OmSpillet);
        KnappOmSpillet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OmSpillet.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });


        //preferanse knapp
        ImageButton setting = findViewById(R.id.setting);
        Intent intent=new Intent(this,SettingsActivity.class);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        // Force recreation in case of language change
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedLanguage = sharedPreferences.getString("language", "no"); // Default Norsk
        String currentLanguage = Locale.getDefault().getLanguage();

        if (!currentLanguage.equals(savedLanguage)) {
            recreate();
        }
    }
}