package com.android.exercise.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.exercise.R;
import com.android.exercise.base.BaseActivity;
import com.android.exercise.jni.MyTest;

public class JniActivity extends BaseActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);

        // Example of a call to a native method
        ((TextView) findViewById(R.id.tv_1)).setText(stringFromJNI());
        ((TextView) findViewById(R.id.tv_2)).setText(MyTest.stringFromJni());
        ((TextView) findViewById(R.id.tv_3)).setText("4 + 5 = " + MyTest.add(4, 5));
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
