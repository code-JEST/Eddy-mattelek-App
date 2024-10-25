package com.example.mappe1s374946;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Registrer lytteren når fragmentet er synlig
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Fjern lytteren når fragmentet ikke er synlig
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("language")) {
            // Hent det valgte språket
            String language = sharedPreferences.getString("language", "no"); // Default Norsk
            // Endre språk basert på brukerens valg
            setLocale(language);

            // Send signal til MainActivity om å restarte
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            // Restart SettingsFragment aktivitet
            getActivity().finish();
        }
    }

    // Funksjon for å endre språket
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        // Oppdater appens ressurser med det nye språket
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.setLocale(locale);
        getActivity().getResources().updateConfiguration(config, getActivity().getResources().getDisplayMetrics());

        // Restart aktivitet for å reflektere språkendringen
        getActivity().recreate();
    }
}

