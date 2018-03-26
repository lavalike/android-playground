//自己创建一个c文件，实现自己定义的native方法，也就是.h文件中的方法
//引入自己生成的.h头文件
#include <com_android_exercise_jni_myJNI.h>

JNIEXPORT jstring JNICALL Java_com_android_exercise_jni_myJNI_sayHello
  (JNIEnv *env, jclass jobj){
  return (*env)->NewStringUTF(env,"sayHello from JNI.");
  }
