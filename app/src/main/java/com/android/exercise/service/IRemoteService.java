package com.android.exercise.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.android.exercise.IMyAidl;
import com.android.exercise.domain.aidl.Person;

import java.util.List;


/**
 * IRemoteService.java
 * Created by wangzhen on 2017/4/9.
 */
public class IRemoteService extends Service {
    public IRemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    private IBinder iBinder = new IMyAidl.Stub() {

        @Override
        public int addNumber(int num1, int num2) throws RemoteException {
            Log.d("", "接收到了远程的请求，参数num1=" + num1 + ",num2=" + num2);
            return num1 + num2;
        }

        @Override
        public List<Person> addPerson(Person person) throws RemoteException {
            return null;
        }
    };
}
