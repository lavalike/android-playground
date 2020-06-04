package com.android.exercise.ui.activity.jetpack.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

/**
 * CustomViewModel
 * Created by wangzhen on 2020/6/4.
 */
public class CustomViewModel extends AndroidViewModel {
    private MutableLiveData<String> mMessageLiveData = new MutableLiveData<>();
    private LiveData<Integer> mMapLiveData = Transformations.map(mMessageLiveData, new Function<String, Integer>() {
        @Override
        public Integer apply(String input) {
            return input.length();
        }
    });
    private LiveData<Integer> mSwitchMapLiveData = Transformations.switchMap(mMessageLiveData, new Function<String, LiveData<Integer>>() {
        @Override
        public LiveData<Integer> apply(String input) {
            return new MutableLiveData<>(input.length());
        }
    });

    public CustomViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<String> getMessageLiveData() {
        return mMessageLiveData;
    }

    public LiveData<Integer> getMapLiveData() {
        return mMapLiveData;
    }

    public LiveData<Integer> getSwitchMapLiveData() {
        return mSwitchMapLiveData;
    }

    public void setMessage(String message) {
        mMessageLiveData.setValue(message);
    }
}
