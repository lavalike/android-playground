package com.android.exercise.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.android.exercise.base.manager.AppManager;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * CameraSurfaceView 录像SurfaceView
 * Created by wangzhen on 2019/1/30.
 */
public class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private static final int MSG_UPDATE_TIME = 0x01;
    private static final long MSG_UPDATE_DELAY = 1000;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    private String outputFile;//录像文件输出路径
    private int maxSeconds = 0;//最大允许录制时长，0为不限制时长，单位：秒
    private long runningSeconds = 0;//已经运行的时长，单位：秒
    private Callback callback;
    private boolean isRecording = false;//是否正在录像

    public CameraSurfaceView(Context context) {
        this(context, null);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CameraSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurfaceView();
    }

    /**
     * 初始化Camera及MediaRecorder
     */
    private void initCamera() {
        if (camera != null) {
            camera.release();
        }
        if (mediaRecorder != null) {
            mediaRecorder.release();
            mediaRecorder = null;
        }
        isRecording = false;
        camera = Camera.open();
        mediaRecorder = new MediaRecorder();
    }

    /**
     * 初始化SurfaceView
     */
    private void initSurfaceView() {
        setKeepScreenOn(true);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        initCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        onSurfaceChange(holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            if (camera != null) {
                camera.setPreviewDisplay(null);
                camera.release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 当界面发生变化的时候，进行surfaceView和camera的绑定，并预览
     *
     * @param holder holder
     */
    private void onSurfaceChange(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(getDegree());
            camera.startPreview();
        } catch (IOException e) {
            if (callback != null) {
                callback.onError(e.getMessage());
            }
        }
    }

    /**
     * 获取surfaceView旋转的角度，系统默认的录制是横向的画面
     *
     * @return 旋转角度
     */
    public int getDegree() {
        int degree = 0;
        Activity activity = AppManager.get().getActivity();
        if (activity == null || activity.isDestroyed()) {
            return degree;
        }
        //获取当前屏幕旋转的角度
        int rotating = activity.getWindowManager().getDefaultDisplay().getRotation();
        //根据手机旋转的角度，来设置surfaceView的显示的角度
        switch (rotating) {
            case Surface.ROTATION_0:
                degree = 90;
                break;
            case Surface.ROTATION_90:
                degree = 0;
                break;
            case Surface.ROTATION_180:
                degree = 270;
                break;
            case Surface.ROTATION_270:
                degree = 180;
                break;
        }
        return degree;
    }

    /**
     * 设置输出路径
     *
     * @param outputFile 路径
     */
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    /**
     * 设置最大录制时长
     *
     * @param maxSeconds 最大录制时长
     */
    public void setMaxSeconds(int maxSeconds) {
        this.maxSeconds = maxSeconds;
    }

    /**
     * 开始录像
     */
    public void startRecord() {
        if (isRecording) return;
        if (TextUtils.isEmpty(outputFile)) {
            outputFile = getDefaultPath();
        }
        //释放被占用的camera
        camera.unlock();
        //将camera设置为MediaRecorder所用的camera
        mediaRecorder.setCamera(camera);
        mediaRecorder.setPreviewDisplay(getHolder().getSurface());
        //设置音频来源：麦克风
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置视频来源：相机
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //设置视频输出格式：mp4
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //设置视频中声音编码格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        //设置视频编码格式
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        //设置视频输出路径
        mediaRecorder.setOutputFile(outputFile);
        try {
            //开始录制
            mediaRecorder.prepare();
            mediaRecorder.start();
            //开始计时
            startTimeRunning();
            isRecording = true;
        } catch (IOException e) {
            if (callback != null) {
                callback.onError(e.getMessage());
            }
        }
    }

    /**
     * 停止录像
     */
    public void stopRecord() {
        initCamera();
        onSurfaceChange(getHolder());
        runningTimeHandler.removeMessages(MSG_UPDATE_TIME);
        if (callback != null) {
            callback.onSuccess(outputFile);
        }
    }

    /**
     * 获取默认存储路径
     *
     * @return 存储路径
     */
    private String getDefaultPath() {
        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return Environment.getExternalStorageDirectory() + File.separator + format.format(time) + ".mp4";
    }

    /**
     * 是否正在录像
     *
     * @return isRecording
     */
    public boolean isRecording() {
        return isRecording;
    }

    /**
     * 开始计时
     */
    private void startTimeRunning() {
        runningSeconds = 0;
        runningTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, MSG_UPDATE_DELAY);
    }

    /**
     * 计时Handler，计算录制时长
     */
    private Handler runningTimeHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_TIME:
                    runningSeconds++;
                    if (callback != null) {
                        callback.onUpdate(runningSeconds);
                    }
                    if (maxSeconds > 0 && runningSeconds >= maxSeconds) {
                        stopRecord();
                    } else {
                        //延时1秒再次发送MSG_UPDATE_TIME
                        runningTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_TIME, MSG_UPDATE_DELAY);
                    }
                    break;
            }
        }
    };

    /**
     * 设置监听回调
     *
     * @param callback callback
     */
    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    /**
     * 视频录制监听接口
     * Created by wangzhen on 2019/1/30.
     */
    public interface Callback {
        /**
         * 录制时长更新
         *
         * @param seconds 已录制时长
         */
        void onUpdate(long seconds);

        /**
         * 录制完成
         *
         * @param videoPath videoPath
         */
        void onSuccess(String videoPath);

        /**
         * 录制错误
         *
         * @param error error
         */
        void onError(String error);
    }
}