/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class example_binge_com_hdiffdemo_HDiffPatch */

#ifndef _Included_example_binge_com_hdiffdemo_HDiffPatch
#define _Included_example_binge_com_hdiffdemo_HDiffPatch
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     example_binge_com_hdiffdemo_HDiffPatch
 * Method:    hdiff
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_example_binge_com_hdiffdemo_HDiffPatch_hdiff
  (JNIEnv *env, jobject obj, jstring oldFilePath, jstring newFilePath, jstring diffFilePath);

/*
 * Class:     example_binge_com_hdiffdemo_HDiffPatch
 * Method:    hpatch
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_example_binge_com_hdiffdemo_HDiffPatch_hpatch
  (JNIEnv *env, jobject obj, jstring oldFilePath, jstring diffFilePath, jstring newFilePath);

#ifdef __cplusplus
}
#endif
#endif
