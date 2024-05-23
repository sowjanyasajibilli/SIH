package com.codingstuff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codingstuff.phoneauthkt.R;

import java.util.concurrent.atomic.AtomicBoolean;

public class Authendication extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefs";
    private static final String SELECTED_OPTION_KEY = "selected_option";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int selectedOption = prefs.getInt(SELECTED_OPTION_KEY, 0);

        if (selectedOption != 0) {
            navigateToSelectedActivity(selectedOption);
            return;
        }

        setContentView(R.layout.authendication);

        RadioGroup radioGroup = findViewById(R.id.rg);
        Button button = findViewById(R.id.button);

        AtomicBoolean actionAlreadyPerformed = new AtomicBoolean(false);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.RadioButton1) {
                Toast.makeText(this, "You selected user", Toast.LENGTH_SHORT).show();
                button.setOnClickListener(v -> {
                    performAction();

                    actionAlreadyPerformed.set(true);

                    int selectedOptionValue = 1;
                    saveSelectedOption(selectedOptionValue);

                    navigateToSelectedActivity(selectedOptionValue);
                });
            } else if (checkedId == R.id.RadioButton2) {
                Toast.makeText(this, "You selected APF user", Toast.LENGTH_SHORT).show();
                button.setOnClickListener(v -> {
                    performAction();

                    actionAlreadyPerformed.set(true);

                    int selectedOptionValue = 2;
                    saveSelectedOption(selectedOptionValue);

                    navigateToSelectedActivity(selectedOptionValue);
                });
            }
        });
    }

    private void performAction() {
    }

    private void saveSelectedOption(int selectedOption) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(SELECTED_OPTION_KEY, selectedOption);
        editor.apply();
    }

    private void navigateToSelectedActivity(int selectedOption) {
        Intent intent;
        if (selectedOption == 1) {
            intent = new Intent(Authendication.this, PhoneActivity.class);
        } else {
            intent = new Intent(Authendication.this, Phone.class);
        }

        startActivity(intent);
        finish();
    }
}
