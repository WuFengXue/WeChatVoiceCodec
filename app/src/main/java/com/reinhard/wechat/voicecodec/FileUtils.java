package com.reinhard.wechat.voicecodec;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件工具类。
 *
 * @author Reinhard（李剑波）
 * @date 2020/4/29
 */
public class FileUtils {
    private FileUtils() {
    }

    /**
     * 从assets目录中复制文件到目标路径，如果文件已经存在，则覆盖之
     *
     * @param context       上下文
     * @param assetFileName assets文件名
     * @param toPath        目标路径（绝对路径）
     */
    public static void copyAssetFile(
            Context context,
            String assetFileName,
            String toPath) {
        if (context == null
                || TextUtils.isEmpty(assetFileName)
                || TextUtils.isEmpty(toPath)) {
            return;
        }

        InputStream is = null;
        FileOutputStream fos = null;
        File toFile = null;

        try {
            is = context.getAssets().open(assetFileName);
            toFile = new File(toPath);
            File toDir = toFile.getParentFile();
            if (!toDir.exists()) {
                toDir.mkdirs();
            }
            if (toFile.exists()) {
                toFile.delete();
            }
            toFile.createNewFile();
            toFile.setReadable(true, false);
            toFile.setWritable(true, false);
            toFile.setExecutable(true, false);
            fos = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            // 循环从输入流读取数据到 buffer 中
            while ((byteCount = is.read(buffer)) != -1) {
                // 将读取的输入流写入到输出流
                fos.write(buffer, 0, byteCount);
            }
            // 刷新缓冲区
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
            if (toFile != null && toFile.exists()) {
                toFile.delete();
            }
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
