package com.example.mappe1s374946;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        applySavedLanguage();

        // Hent det lagrede språket
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedLanguage = sharedPreferences.getString("language", "no"); // Default Norsk

        // Hent det nåværende språket
        String currentLanguage = Locale.getDefault().getLanguage();

        // Sjekk om språket er annerledes enn det lagrede språket
        if (!currentLanguage.equals(savedLanguage)) {
            // Sett appens språk til det lagrede språket
            setLocale(savedLanguage);
            // Gjenskap aktiviteten med riktig språk
            recreate();
        }

    }

    private void applySavedLanguage() {
        // Get the saved language
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String savedLanguage = sharedPreferences.getString("language", "no"); // Default to Norwegian

        // Set the locale based on the saved language
        Locale locale = new Locale(savedLanguage);
        Locale.setDefault(locale);

        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    // Funksjon for å endre språket
    private void setLocale(String languageCode) {

        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        Context context = this.createConfigurationContext(config);

        getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        this.recreate();
    }
}

