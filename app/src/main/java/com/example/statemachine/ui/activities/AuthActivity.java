package com.example.statemachine.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.statemachine.R;

public class AuthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        TextView title = findViewById(R.id.title);
        title.setText("Authorization");

        Button ok = findViewById(R.id.button_ok);
        Button fail = findViewById(R.id.button_fail);
        Button cancel = findViewById(R.id.button_cancel);

        ok.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("result", "success");
            setResult(RESULT_OK, data);
            finish();
        });

        fail.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("result", "fail");
            setResult(RESULT_OK, data);
            finish();
        });

        cancel.setOnClickListener(v -> {
            Intent data = new Intent();
            data.putExtra("result", "cancel");
            setResult(RESULT_OK, data);
            finish();
        });
    }

}