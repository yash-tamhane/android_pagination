package com.example.apicalling.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.apicalling.MyApplication;


public class UserViewModelFactory implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserDataViewModel.class)) {
            return (T) new UserDataViewModel(MyApplication.getInstance());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
