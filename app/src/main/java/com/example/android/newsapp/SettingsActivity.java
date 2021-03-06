package com.example.android.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            // Get the news from heading of the news settings
            Preference newsOrigin = findPreference(getString(R.string.settings_news_type_by_key));
            // Set the value to display under the settings heading
            bindPreferenceSummaryToValue(newsOrigin);

            // Get the page size heading of the news settings
            Preference newsPagesize = findPreference(getString(R.string.settings_page_size_key));
            // Set the value to display under the settings heading
            bindPreferenceSummaryToValue(newsPagesize);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            // The code in this method takes care of updating the displayed preference summary after it has been changed
            String stringValue = value.toString();

            // Protect against incorrect input
            // Ensure maximum page size value is below 200
            if (preference.toString().contains(getString(R.string.settings_page_size_label))) {
                int pageSize;
                try {
                    pageSize = Integer.parseInt(stringValue);
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getActivity(), getString(R.string.page_length_not_int), Toast.LENGTH_LONG).show();
                    return false;
                }
                if (pageSize > 200) {
                    Toast.makeText(getActivity(), getString(R.string.page_length_too_long), Toast.LENGTH_LONG).show();
                    return false;
                }
            }

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}