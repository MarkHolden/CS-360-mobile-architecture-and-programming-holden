package com.holden.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.security.crypto.*;

import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences encryptedSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SetUpEncryptedSharedPreferences();

        if (!isAuthenticated()) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            EventListFragment fragment = new EventListFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
    }

    private boolean isAuthenticated() {
        String expirationString = encryptedSharedPreferences.getString(getString(R.string.expires_at), null);
        String accessToken = encryptedSharedPreferences.getString(getString(R.string.access_token), null);

        return accessToken != null
                && expirationString != null
                && LocalDateTime.now().isBefore(LocalDateTime.parse(expirationString, DateTimeFormatter.ofPattern(getString(R.string.date_format)))); // why do people even use Java...
    }

    private void SetUpEncryptedSharedPreferences() {
        try {
        String keys = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            encryptedSharedPreferences = EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    keys,
                    this,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

