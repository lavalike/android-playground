package com.android.exercise.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Toast;

import com.android.exercise.base.manager.AudioManager;
import com.android.exercise.base.manager.DialogManager;

/**
 * 录音按钮
 * Created by wangzhen on 2017/8/8.
 */
public class RecorderButton extends AppCompatButton {

    //正常状态
    private static final int STATE_AUDIO_NORMAL = 0x0;
    //录音音量变化
    private static final int STATE_VOICE_LEVEL_CHANGED = 0x1;
    //准备完毕
    private static final int STATE_AUDIO_PREPARED = 0x2;
    //正在录音
    private static final int STATE_AUDIO_RECORDING = 0x3;
    //录音完成
    private static final int STATE_AUDIO_COMPLETE = 0x4;
    //录音时间变化
    private static final int STATE_AUDIO_TIME_CHANGED = 0x5;
    //超出范围状态
    private static final int STATE_OUT_OF_RANGE = 0x6;
    //取消录音
    private static final int STATE_AUDIO_CANCEL = 0x7;
    //停止录音
    private static final int STATE_AUDIO_STOP = 0x8;
    //录音出错
    private static final int STATE_AUDIO_ERROR = 0x9;
    //最短录音时间 单位：毫秒
    private static final long RECORD_MIN_DURATION = 1000;
    //获取音量大小时间间隔
    private static final long DELAY_GET_VOICE_LEVEL = 100;
    //录音最大长度 单位：毫秒
    private static final long AUDIO_MAX_LENGTH = 5000;
    //录音总时间 单位：毫秒
    private long mRecordTotalTime;
    //当前触摸坐标
    private float currX;
    private float currY;
    //是否正在录音
    private boolean isRecording;
    private AudioManager mAudioManager;
    private String mFilePath;
    private IRecorderListener mRecorderListener;
    private DialogManager mDialogManager;

    public RecorderButton(Context context) {
        this(context, null);

    }

    public RecorderButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecorderButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mAudioManager = AudioManager.get();
        mDialogManager = new DialogManager(context);
        mHandler.sendEmptyMessage(STATE_AUDIO_NORMAL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                currX = 0L;
                currY = 0L;
                initAudioRecorder();
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    currX = event.getX();
                    currY = event.getY();
                    if (isAboutToCancel(currX, currY)) {
                        mHandler.sendEmptyMessage(STATE_OUT_OF_RANGE);
                    } else {
                        mHandler.sendEmptyMessage(STATE_AUDIO_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                mHandler.sendEmptyMessage(STATE_AUDIO_NORMAL);
                if (isRecording) {
                    isRecording = false;
                    if (isAboutToCancel(currX, currY)) {
                        mHandler.sendEmptyMessage(STATE_AUDIO_CANCEL);
                    } else {
                        mHandler.sendEmptyMessage(STATE_AUDIO_STOP);
                    }
                } else {
                    mHandler.sendEmptyMessage(STATE_AUDIO_CANCEL);
                }
                break;
        }
        return true;
    }

    /**
     * 初始化录音
     */
    private void initAudioRecorder() {
        mAudioManager.setRecordStateListener(new AudioManager.AudioRecordStateListener() {
            @Override
            public void onPrepared() {
                mHandler.sendEmptyMessage(STATE_AUDIO_PREPARED);
            }

            @Override
            public void onComplete(String path) {
                Message message = Message.obtain();
                message.what = STATE_AUDIO_COMPLETE;
                message.obj = path;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError(String error) {
                mHandler.sendEmptyMessage(STATE_AUDIO_ERROR);
            }
        });
        mAudioManager.startRecord();
    }

    /**
     * 获取音量大小Runnable
     */
    private Runnable mVoiceLevelRunnable = new Runnable() {
        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(DELAY_GET_VOICE_LEVEL);
                    mRecordTotalTime += DELAY_GET_VOICE_LEVEL;
                    mHandler.sendEmptyMessage(STATE_VOICE_LEVEL_CHANGED);
                    mHandler.sendEmptyMessage(STATE_AUDIO_TIME_CHANGED);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    /**
     * 更改按钮状态
     *
     * @param state 状态 see{@link #STATE_AUDIO_NORMAL ,#STATE_AUDIO_PREPARED ,#STATE_VOICE_LEVEL_CHANGED ,#STATE_OUT_OF_RANGE}
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STATE_AUDIO_NORMAL:
                    setText("按住说话");
                    break;
                case STATE_AUDIO_PREPARED:
                    isRecording = true;
                    new Thread(mVoiceLevelRunnable).start();
                    mDialogManager.showDialog();
                    setText("正在录音");
                    break;
                case STATE_AUDIO_RECORDING:
                    mDialogManager.recording();
                    setText("正在录音");
                    break;
                case STATE_OUT_OF_RANGE:
                    setText("松开手指，取消发送");
                    mDialogManager.isAboutToCancel();
                    break;
                case STATE_VOICE_LEVEL_CHANGED:
                    int voiceLevel = mAudioManager.getVoiceLevel(7);
                    mDialogManager.updateVoiceLevel(voiceLevel);
                    break;
                case STATE_AUDIO_TIME_CHANGED:
//                    if (mRecordTotalTime >= AUDIO_MAX_LENGTH) {
//                        isRecording = false;
//                        mAudioManager.stopRecord();
//                        Log.e("RECORDER", "时间到达上限自动停止录音");
//                    }
                    break;
                case STATE_AUDIO_CANCEL:
                    mDialogManager.dismissDialog();
                    mAudioManager.cancelRecord();
                    break;
                case STATE_AUDIO_STOP:
                    mAudioManager.stopRecord();
                    break;
                case STATE_AUDIO_ERROR:
                    mDialogManager.dismissDialog();
                    break;
                case STATE_AUDIO_COMPLETE:
                    isRecording = false;
                    mFilePath = (String) msg.obj;
                    checkRecordFile();
                    break;
            }
        }
    };

    /**
     * 校验录音文件
     */
    private void checkRecordFile() {
        if (mRecordTotalTime < RECORD_MIN_DURATION) {
            mDialogManager.tooShort();
            return;
        }
        mDialogManager.dismissDialog();
        mRecordTotalTime = 0;
        if (mRecorderListener != null) {
            mRecorderListener.onFinish(mFilePath);

        }
    }

    /**
     * 判断是否超出指定范围
     *
     * @param currX X坐标
     * @param currY Y坐标
     * @return 是否要取消
     */
    private boolean isAboutToCancel(float currX, float currY) {
        if (currX < 0 || currX > getWidth()) {
            return true;
        }
        if (currY < 0 || currY > getHeight()) {
            return true;
        }
        return false;
    }

    private void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public void setRecorderListener(IRecorderListener listener) {
        this.mRecorderListener = listener;
    }

    public interface IRecorderListener {

        /**
         * 录音完成
         *
         * @param path 录音文件路径
         */
        void onFinish(String path);
    }
}
