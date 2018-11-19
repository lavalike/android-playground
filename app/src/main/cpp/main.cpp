//
// Created by 王震 on 2018/11/19.
//

#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_android_exercise_jni_MyTest_stringFromJni(JNIEnv *env, jclass type) {
    return env->NewStringUTF("stringFromJni() from main-lib");
}


extern "C"
JNIEXPORT jint JNICALL
Java_com_android_exercise_jni_MyTest_add(JNIEnv *env, jclass type, jint a, jint b) {
    return a + b;
}