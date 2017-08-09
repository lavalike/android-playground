package com.android.exercise.base.manager;

import android.media.MediaRecorder;
import android.os.Environment;
import android.text.TextUtils;

import com.android.exercise.listener.IAudioCallback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 音频管理类
 * Created by wangzhen on 2017/8/8.
 */
public class AudioManager implements IAudioCallback.IRecordCallback, IAudioCallback.IPlayCallback {

    private static AudioManager mInstance;
    private MediaRecorder mMediaRecorder;
    private AudioRecordStateListener mRecordStateListener;
    //录音保存目录
    private String mDir;
    //是否准备完毕
    private boolean isPrepared;
    //当前录音文件完整路径
    private String mCurrRecordFilePath;

    public void setRecordStateListener(AudioRecordStateListener listener) {
        this.mRecordStateListener = listener;
    }


    public static AudioManager get() {
        if (mInstance == null) {
            synchronized (AudioManager.class) {
                if (mInstance == null) {
                    mInstance = new AudioManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置录音存储路径
     *
     * @param dir 目录
     */
    public void setDir(String dir) {
        this.mDir = dir;
    }

    /**
     * 获取音频分贝大小
     * mMediaRecorder.getMaxAmplitude() 1-32767
     *
     * @param maxLevel 最大级别
     * @return 1-maxLevel
     */
    public int getVoiceLevel(int maxLevel) {
        if (isPrepared) {
            try {
                return maxLevel * mMediaRecorder.getMaxAmplitude() / 32768 + 1;
            } catch (IllegalStateException e) {
            }
        }
        return 1;
    }

    @Override
    public void startRecord() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        } else {
            mMediaRecorder.reset();
        }
        try {
            if (TextUtils.isEmpty(this.mDir))
                this.mDir = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_RINGTONES;
            File dir = new File(mDir);
            if (!dir.exists())
                dir.mkdirs();
            String fileName = generateFileName();
            File file = new File(dir, fileName);
            mCurrRecordFilePath = file.getAbsolutePath();
            //设置输出文件
            mMediaRecorder.setOutputFile(file.getAbsolutePath());
            //设置输入源
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置音频格式
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            //设置音频编码
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            isPrepared = true;
            if (mRecordStateListener != null) {
                mRecordStateListener.onPrepared();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成文件名
     *
     * @return 文件名
     */
    private String generateFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        Date date = new Date(System.currentTimeMillis());
        return format.format(date) + ".amr";
    }

    @Override
    public void cancelRecord() {
        deleteRecordFile();
        stopRecord();
    }

    /**
     * 删除生成的录音文件
     */
    private void deleteRecordFile() {
        File file = new File(mCurrRecordFilePath);
        if (file.exists())
            file.delete();
        mCurrRecordFilePath = "";
    }

    @Override
    public void stopRecord() {
        if (mMediaRecorder != null) {
            isPrepared = false;
            try {
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
                if (!TextUtils.isEmpty(mCurrRecordFilePath)) {
                    if (mRecordStateListener != null) {
                        mRecordStateListener.onComplete(mCurrRecordFilePath);
                    }
                }
            } catch (RuntimeException e) {
                deleteRecordFile();
                if (mRecordStateListener != null) {
                    mRecordStateListener.onError(e.getMessage());
                }
            }
        } else {
            if (mRecordStateListener != null) {
                mRecordStateListener.onError("mMediaRecorder为null");
            }
        }
    }

    @Override
    public void preparePlay() {

    }

    @Override
    public void startPlay() {

    }

    @Override
    public void pausePlay() {

    }

    @Override
    public void resumePlay() {

    }

    @Override
    public void stopPlay() {

    }

    public interface AudioRecordStateListener {
        /**
         * 准备完毕
         */
        void onPrepared();

        /**
         * 录音完毕
         */
        void onComplete(String path);

        /**
         * 发生错误
         */
        void onError(String error);
    }
}
