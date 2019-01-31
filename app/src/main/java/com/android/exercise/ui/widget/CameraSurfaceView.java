package com.android.exercise.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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
    private static final int MSG_UPDATE_WHAT = 0x01;
    private static final long MSG_UPDATE_DELAY = 1000;
    //视频比特率
    private static final int VIDEO_ENCODING_BIT_RATE = 5 * 1024 * 1024;
    private Camera camera;
    private MediaRecorder mediaRecorder;
    //录像文件输出路径
    private String outputFile;
    //最大允许录制时长，0为不限制时长，单位：秒
    private int maxSeconds = 0;
    //已经运行的时长，单位：秒
    private long runningSeconds = 0;
    private Callback callback;
    //是否正在录像
    private boolean isRecording = false;
    //录像尺寸
    private int videoWidth = 640;
    private int videoHeight = 480;
    /**
     * CameraId，默认为后摄
     * 后摄{@link Camera.CameraInfo.CAMERA_FACING_BACK}
     * 前摄{@link Camera.CameraInfo.CAMERA_FACING_FRONT}
     */
    private int cameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

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
        }
        isRecording = false;
        camera = Camera.open(cameraId);
        Camera.Parameters parameters = camera.getParameters();
        Camera.Size size = parameters.getPreferredPreviewSizeForVideo();
        videoWidth = size.width;
        videoHeight = size.height;
        parameters.setPreviewSize(videoWidth, videoHeight);
        camera.setParameters(parameters);
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
        Activity activity = getRealActivity(getContext());
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
        //系统默认的录制是横向的画面，后置摄像头向右旋转90度
        if (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mediaRecorder.setOrientationHint(90);
        } else {
            //系统默认的录制是横向的画面，前置摄像头向右旋转90+180度
            mediaRecorder.setOrientationHint(270);
        }
        //设置视频尺寸
        mediaRecorder.setVideoSize(videoWidth, videoHeight);
        //设置视频比特率
        mediaRecorder.setVideoEncodingBitRate(VIDEO_ENCODING_BIT_RATE);
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
        runningTimeHandler.removeMessages(MSG_UPDATE_WHAT);
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
        runningTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_WHAT, MSG_UPDATE_DELAY);
    }

    /**
     * 计时Handler，计算录制时长
     */
    private Handler runningTimeHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_UPDATE_WHAT:
                    runningSeconds++;
                    if (callback != null) {
                        callback.onUpdate(runningSeconds);
                    }
                    if (maxSeconds > 0 && runningSeconds >= maxSeconds) {
                        stopRecord();
                    } else {
                        //延时1秒再次发送MSG_UPDATE_TIME
                        runningTimeHandler.sendEmptyMessageDelayed(MSG_UPDATE_WHAT, MSG_UPDATE_DELAY);
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
     * 设置设备支持的录像尺寸，否则FC
     *
     * @param width  width
     * @param height height
     */
    public void setVideoSize(int width, int height) {
        videoWidth = width;
        videoHeight = height;
    }

    /**
     * 通过Context获取真正的Activity
     *
     * @param ctx context
     * @return activity
     */
    private Activity getRealActivity(Context ctx) {
        while (ctx instanceof ContextWrapper) {
            if (ctx instanceof Activity) {
                return (Activity) ctx;
            }
            ctx = ((ContextWrapper) ctx).getBaseContext();
        }
        return null;
    }

    /**
     * 切换前后摄像头
     */
    public void switchCamera() {
        //查询摄像头个数
        int numberOfCameras = Camera.getNumberOfCameras();
        //非录像中且摄像头个数至少为2个
        if (!isRecording && numberOfCameras > 1) {
            cameraId = (cameraId == Camera.CameraInfo.CAMERA_FACING_BACK) ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
            initCamera();
            onSurfaceChange(getHolder());
        }
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
