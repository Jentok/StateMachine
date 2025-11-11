package com.example.statemachine.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.statemachine.data.model.Config;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigRepository {
    private static ConfigRepository instance;
    private final MutableLiveData<Config> configLiveData = new MutableLiveData<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isLoaded = false;

    private ConfigRepository() {
    }

    public static synchronized ConfigRepository getInstance() {
        if (instance == null) {
            instance = new ConfigRepository();
        }
        return instance;
    }

    public void init(Context context) {
        if (isLoaded) return;

        executor.execute(() -> {
            try {
                InputStream inputStream = context.getAssets().open("config.json");
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Config config = new Gson().fromJson(reader, Config.class);
                reader.close();

                isLoaded = true;
                configLiveData.postValue(config);
            } catch (Exception e) {
                e.printStackTrace();
                configLiveData.postValue(null);
            }
        });
    }

    public LiveData<Config> getConfigLiveData() {
        return configLiveData;
    }
}
