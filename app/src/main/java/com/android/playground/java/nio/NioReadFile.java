package com.android.playground.java.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * NioReadFile
 * Created by wangzhen on 2018/11/1.
 */
public class NioReadFile {
    public static void main(String[] args) {
        NioReadFile nioReadFile = new NioReadFile();
        nioReadFile.fileChannel();
    }

    private void fileChannel() {
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/wangzhen/Android/Git/AndroidExercise/app/src/main/java/com/android/exercise/java/nio/nio-data", "rw");
            FileChannel channel = randomAccessFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            CharBuffer charBuffer = CharBuffer.allocate(1024);
            CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
            int read = channel.read(byteBuffer);
            while (read != -1) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    decoder.decode(byteBuffer, charBuffer, false);
                    charBuffer.flip();
                    System.out.print(charBuffer);
                }
                byteBuffer.clear();
                charBuffer.clear();
                read = channel.read(byteBuffer);
            }
            randomAccessFile.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
