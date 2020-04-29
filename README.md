# WeChatVoiceCodec

微信语音编解码，支持：

* 微信 amr 语音文件 <——silk——> pcm 文件 <——lame——> mp3 文件

## 使用

* gradle 依赖

```gradle
implementation 'com.reinhard:WeChatVoiceCodec:1.0.1'
```

* API

```java
package com.reinhard.wcvcodec;

/**
 * WeChat voice decoder and encoder
 *
 * @author Reinhard（李剑波）
 * @date 2019-06-22
 */
public class WcvCodec {
    static {
        System.loadLibrary("wcvcodec");
    }

    /**
     * decode amr to mp3 (1. amr to pcm  2. pcm to mp3)
     *
     * @param amrPath amr file path
     * @param pcmPath pcm file path
     * @param mp3Path mp3 file path
     * @return 0 if success, otherwise -1
     */
    public static native int decode(String amrPath, String pcmPath, String mp3Path);

    /**
     * encode pcm to amr
     *
     * @param pcmPath pcm file path
     * @param amrPath amr file path
     * @return 0 if success, otherwise -1
     */
    public static native int encode(String pcmPath, String amrPath);

    /**
     * encode mp3 to amr (1. mp3 to pcm  2. pcm to amr)
     *
     * @param mp3Path mp3 file path
     * @param pcmPath pcm file path
     * @param amrPath amr file path
     * @return 0 if success, otherwise -1
     */
    public static native int encode2(String mp3Path, String pcmPath, String amrPath);
}

```



## 实现细节

* [微信语音编解码实现（一）—— silk 移植](https://wufengxue.github.io/2019/03/12/wechat-voice-codec-silk.html)
* [微信语音编解码实现（二）—— 支持微信语音](https://wufengxue.github.io/2019/04/17/wechat-voice-codec-amr.html)
* [微信语音编解码实现（三）—— lame 移植](https://wufengxue.github.io/2019/05/25/wechat-voice-codec-lame.html)
* [微信语音编解码实现（四）—— 整合 so 库](https://wufengxue.github.io/2019/06/29/wechat-voice-codec-lib.html)
