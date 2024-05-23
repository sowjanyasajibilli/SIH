package com.codingstuff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.codingstuff.phoneauthkt.R;

public class scode extends AppCompatActivity {

    private boolean codeExecuted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scode);

        EditText editTextscode = findViewById(R.id.scode);
        Button verifyButton = findViewById(R.id.verifyscode);

        verifyButton.setOnClickListener(v -> {
            if (!codeExecuted) {
                String scode = editTextscode.getText().toString().trim();

                switch (scode) {
                    case "ES@2023A": {
                        startActivity(new Intent(scode.this, Main1.class));
                        codeExecuted = true;
                        break;
                    }
                    case "ES@2023P": {
                        startActivity(new Intent(scode.this, Main2.class));
                        codeExecuted = true;
                        break;
                    }
                    case "ES@2023F": {
                        startActivity(new Intent(scode.this, Main3.class));
                        codeExecuted = true;
                        break;
                    }
                    default:
                        Toast.makeText(scode.this, "PLEASE CHECK THE CODE AND ENTER AGAIN", Toast.LENGTH_SHORT).show();
                        break;
                }
            } else {
                Toast.makeText(scode.this, "Code already executed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
