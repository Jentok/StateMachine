package com.example.statemachine.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.statemachine.R;
import com.example.statemachine.data.model.Command;
import com.example.statemachine.engine.TransactionEngine;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MainViewModel mainViewModel;
    TransactionEngine engine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        engine = new TransactionEngine(this);

        LinearLayout buttonsContainer = findViewById(R.id.buttons_container);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getConfig().observe(this, config -> {
            for (Command command : config.getCommands()) {
                Button button = new Button(this);
                button.setText(command.getCommandName());
                button.setOnClickListener(this);
                buttonsContainer.addView(button);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        Command command = mainViewModel.getConfig().getValue().findCommand(button.getText().toString());
        engine.start(command);
    }
}