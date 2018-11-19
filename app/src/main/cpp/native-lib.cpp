#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_android_exercise_ui_activity_JniActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "stringFromJNI() from native-lib";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_android_exercise_ui_activity_JniActivity_getHelloJni(JNIEnv *env, jobject instance) {
    return env->NewStringUTF("getHelloJni() from native-lib");
}