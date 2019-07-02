/**
 * WeChat voice decoder and encoder
 *
 * @author Reinhard（李剑波）
 * @date 2019/6/22
 */

#include <silk/SilkCodec.h>
#include <lame/LameCodec.h>
#include "WcvCodec.h"
#include "android_log.h"

/*
 * Class:     com_reinhard_wcvcodec_WcvCodec
 * Method:    decode
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_reinhard_wcvcodec_WcvCodec_decode
        (JNIEnv *env, jclass clazz, jstring amrPath, jstring pcmPath, jstring mp3Path) {
    const char *amr = (*env)->GetStringUTFChars(env, amrPath, JNI_FALSE);
    const char *pcm = (*env)->GetStringUTFChars(env, pcmPath, JNI_FALSE);
    const char *mp3 = (*env)->GetStringUTFChars(env, mp3Path, JNI_FALSE);
    int argc = 5;
    const char *argv[] = {"./Decoder", amr, pcm, "-wechat", "-quiet"};
    if (silk_decoder_main(argc, (char **) argv) == JNI_OK) {
        int argc2 = 14;
        const char *argv2[] = {"./lame", "-q", "5", "-b", "128", "-m", "m", "-r",
                               "-s", "24000", "--resample", "24000", pcm, mp3};
        return lame_codec_main(argc2, (char **) argv2);
    } else {
        LOGE("silk_decoder_main failed!");
        return JNI_ERR;
    }
}

/*
 * Class:     com_reinhard_wcvcodec_WcvCodec
 * Method:    encode
 * Signature: (Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_reinhard_wcvcodec_WcvCodec_encode
        (JNIEnv *env, jclass clazz, jstring pcmPath, jstring amrPath) {
    const char *pcm = (*env)->GetStringUTFChars(env, pcmPath, JNI_FALSE);
    const char *amr = (*env)->GetStringUTFChars(env, amrPath, JNI_FALSE);
    int argc = 7;
    const char *argv[] = {"./Encoder", pcm, amr, "-rate", "24000", "-wechat", "-quiet"};
    return silk_encoder_main(argc, (char **) argv);
}

/*
 * Class:     com_reinhard_wcvcodec_WcvCodec
 * Method:    encode2
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_com_reinhard_wcvcodec_WcvCodec_encode2
        (JNIEnv *env, jclass clazz, jstring mp3Path, jstring pcmPath, jstring amrPath) {
    const char *mp3 = (*env)->GetStringUTFChars(env, mp3Path, JNI_FALSE);
    const char *pcm = (*env)->GetStringUTFChars(env, pcmPath, JNI_FALSE);
    const char *amr = (*env)->GetStringUTFChars(env, amrPath, JNI_FALSE);
    int argc = 5;
    const char *argv[] = {"./lame", "--decode", "-t", mp3, pcm};
    if (lame_codec_main(argc, (char **) argv) == JNI_OK) {
        int argc2 = 7;
        const char *argv2[] = {"./Encoder", pcm, amr, "-rate", "24000", "-wechat", "-quiet"};
        return silk_encoder_main(argc2, (char **) argv2);
    } else {
        LOGE("lame_codec_main failed!");
        return JNI_ERR;
    }
}