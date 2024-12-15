#include <stdio.h>
#include "jvm_tech_demonlee_jni_FooNative.h"

JNIEXPORT jint JNICALL Java_jvm_tech_demonlee_jni_FooNative_bar
  (JNIEnv *env, jobject thisObject, jint i, jstring str) {
  // 将 jstring 转换为 C 的 char*
  const char *nativeString = (*env)->GetStringUTFChars(env, str, NULL);
  printf("+++++++++Hello, %s, %d\n", nativeString, i);
  (*env)->ReleaseStringUTFChars(env, str, nativeString);
  jint result = 10*i*i;
  return result;
}
