package com.android.exercise.ui.activity.jetpack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

/**
 * CustomViewModel
 * Created by wangzhen on 2020/6/4.
 */
public class CustomViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMessageLiveData = new MutableLiveData<>();

    public CustomViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMessageLiveData() {
        return mMessageLiveData;
    }

    public void setMessage(String message) {
        mMessageLiveData.setValue(message);
    }
}
