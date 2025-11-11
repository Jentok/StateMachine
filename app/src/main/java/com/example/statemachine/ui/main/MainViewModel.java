package com.example.statemachine.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.statemachine.data.model.Config;
import com.example.statemachine.data.repository.ConfigRepository;

public class MainViewModel extends AndroidViewModel {
    ConfigRepository configRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        configRepository = ConfigRepository.getInstance();
    }

    public LiveData<Config> getConfig() {
        return configRepository.getConfigLiveData();
    }
}
