package com.android.exercise.listener;

/**
 * 音频相关接口
 * Created by wangzhen on 2017/8/8.
 */
public interface IAudioCallback {
    /**
     * 音频录制接口
     */
    interface IRecordCallback {

        /**
         * 开始录制
         */
        void startRecord();

        /**
         * 取消录制
         */
        void cancelRecord();

        /**
         * 停止录制
         */
        void stopRecord();
    }

    /**
     * 音频播放接口
     */
    interface IPlayCallback {
        /**
         * 播放前准备工作
         */
        void preparePlay();

        /**
         * 开始播放
         */
        void startPlay();

        /**
         * 暂停播放
         */
        void pausePlay();

        /**
         * 恢复播放
         */
        void resumePlay();

        /**
         * 停止播放
         */
        void stopPlay();
    }
}
