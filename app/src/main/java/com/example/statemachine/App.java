package com.example.statemachine;

import android.app.Application;

import com.example.statemachine.data.repository.ConfigRepository;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ConfigRepository.getInstance().init(this);
    }

}
