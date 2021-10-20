package com.example.apicalling.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.apicalling.pojo.Data;
import java.util.List;

public class UserDataViewModel extends AndroidViewModel {

    private Data userModel;

    private MutableLiveData<List<Data>> dataOfUsers = new MutableLiveData<>();
    private MutableLiveData<List<String>> requestState = new MutableLiveData<>();

    public UserDataViewModel(@NonNull Application application) {
        super(application);
      //  userDatabase = UserDatabase.getInstance(application);
    }



  /*  public MutableLiveData<List<Data>> fetchUserData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            dataOfUsers.postValue(userDatabase.userDao().getCatcheData());
        });
        return dataOfUsers;
    }*/
}
