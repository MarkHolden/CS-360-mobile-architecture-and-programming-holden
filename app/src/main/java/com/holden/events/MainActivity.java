package com.holden.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.*;

import android.content.*;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {

    private TextView textGreeting;
    private EditText nameText;
    private SharedPreferences encryptedSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sayHello = findViewById(R.id.buttonSayHello);
        sayHello.setOnClickListener(sayHelloClickListener);

        textGreeting = findViewById(R.id.textGreeting);

        nameText = findViewById(R.id.nameText);

        SetUpEncryptedSharedPreferences();

        if (!isAuthenticated()) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }
    }

    private boolean isAuthenticated() {
        String expirationString = encryptedSharedPreferences.getString(getString(R.string.expires_at), null);
        String accessToken = encryptedSharedPreferences.getString(getString(R.string.access_token), null);

        return accessToken != null
                && expirationString != null
                && LocalDateTime.now().isBefore(LocalDateTime.parse(expirationString, DateTimeFormatter.ofPattern(getString(R.string.date_format))));
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

    private final View.OnClickListener sayHelloClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        }
    };
}

