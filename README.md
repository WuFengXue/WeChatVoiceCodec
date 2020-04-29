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

## 二进制工具 / bin utils

* 用 adb push 到安卓设备上使用
* Release 只提供了 armeabi-v7a 版本，需要其他版本请自行编译
* lame - lame encoder and decoder

```shell
LAME 32bits version 3.100 (http://lame.sf.net)

usage: ./lame [options] <infile> [outfile]

    <infile> and/or <outfile> can be "-", which means stdin/stdout.

RECOMMENDED:
    lame -V2 input.wav output.mp3

OPTIONS:
    -b bitrate      set the bitrate, default 128 kbps
    -h              higher quality, but a little slower.
    -f              fast mode (lower quality)
    -V n            quality setting for VBR.  default n=4
                    0=high quality,bigger files. 9.999=smaller files
    --preset type   type must be "medium", "standard", "extreme", "insane",
                    or a value for an average desired bitrate and depending
                    on the value specified, appropriate quality settings will
                    be used.
                    "--preset help" gives more info on these

    --help id3      ID3 tagging related options

    --longhelp      full list of options

    --license       print License information
```



* encoder - silk encoder

```shell
usage: ./encoder in.pcm out.bit [settings]

in.pcm               : Speech input to encoder
out.bit              : Bitstream output from encoder
   settings:
-Fs_API <Hz>         : API sampling rate in Hz, default: 24000
-Fs_maxInternal <Hz> : Maximum internal sampling rate in Hz, default: 24000
-packetlength <ms>   : Packet interval in ms, default: 20
-rate <bps>          : Target bitrate; default: 25000
-loss <perc>         : Uplink loss estimate, in percent (0-100); default: 0
-inbandFEC <flag>    : Enable inband FEC usage (0/1); default: 0
-complexity <comp>   : Set complexity, 0: low, 1: medium, 2: high; default: 2
-DTX <flag>          : Enable DTX (0/1); default: 0
-quiet               : Print only some basic values
-wechat              : Add ASCII STX code 0x02 to header and remove 0xFFFF footer (for wechat)
```



* decoder - silk decoder

```shell
usage: ./decoder in.bit out.pcm [settings]

in.bit       : Bitstream input to decoder
out.pcm      : Speech output from decoder
   settings:
-Fs_API <Hz> : Sampling rate of output signal in Hz; default: 24000
-loss <perc> : Simulated packet loss percentage (0-100); default: 0
-quiet       : Print out just some basic values
-wechat      : Skip ASCII STX code of header (for wechat)

```



## 实现细节

* [微信语音编解码实现（一）—— silk 移植](https://wufengxue.github.io/2019/03/12/wechat-voice-codec-silk.html)
* [微信语音编解码实现（二）—— 支持微信语音](https://wufengxue.github.io/2019/04/17/wechat-voice-codec-amr.html)
* [微信语音编解码实现（三）—— lame 移植](https://wufengxue.github.io/2019/05/25/wechat-voice-codec-lame.html)
* [微信语音编解码实现（四）—— 整合 so 库](https://wufengxue.github.io/2019/06/29/wechat-voice-codec-lib.html)
