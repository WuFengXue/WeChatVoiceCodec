package com.reinhard.wechat.voicecodec;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reinhard.wcvcodec.WcvCodec;

import java.io.File;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends Activity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "WcvCodec";
    /**
     * 测试音频存储路径
     */
    private static final String TEST_DIR = Environment.getExternalStorageDirectory().getPath()
            + File.separatorChar + "WcvCodec" + File.separatorChar;
    /**
     * 内置测试音频文件名
     */
    private static final String[] ASSET_FILE_NAMES = {
            "in.amr",
            "in.mp3",
            "in.pcm",
    };
    /**
     * 权限请求码
     */
    private static final int REQUEST_CODE_EXT_STORAGE_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: TEST_DIR = " + TEST_DIR);
        setContentView(R.layout.activity_main);
        initViews();
        if (checkExtStoragePermission()) {
            copyTestVectors();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void initViews() {
        TextView tvTip = findViewById(R.id.tv_tip);
        String formatStr = getString(R.string.test_dir_format);
        tvTip.setText(String.format(formatStr, TEST_DIR));
    }

    /**
     * 拷贝内置测试音频到测试路径
     */
    private void copyTestVectors() {
        for (String assetFileName : ASSET_FILE_NAMES) {
            FileUtils.copyAssetFile(this,
                    assetFileName,
                    TEST_DIR + assetFileName);
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_amr_to_mp3:
                testAmrToMp3();
                break;
            case R.id.btn_pcm_to_amr:
                testPcmToAmr();
                break;
            case R.id.btn_mp3_to_amr:
                testMp3ToAmr();
                break;
            default:
                break;
        }
    }

    /**
     * 校验及申请 sd 卡存储权限
     *
     * @return 已获取权限返回 true；否则申请权限并返回 false
     */
    private boolean checkExtStoragePermission() {
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            return true;
        } else {
            EasyPermissions.requestPermissions(this,
                    "内置的测试音频需要拷贝到 sd 卡，否则无法正常工作",
                    REQUEST_CODE_EXT_STORAGE_PERMISSION,
                    perms);
            return false;
        }
    }

    private void testAmrToMp3() {
        Log.d(TAG, "testAmrToMp3");
        final String amrPath = TEST_DIR + "in.amr";
        final String pcmPath = TEST_DIR + "out.pcm";
        final String mp3Path = TEST_DIR + "out.mp3";
        // demo 展示用，实际项目中请不要这样使用线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (WcvCodec.decode(amrPath, pcmPath, mp3Path) == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "testAmrToMp3 success", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
                }
            }
        }).start();
    }

    private void testPcmToAmr() {
        Log.d(TAG, "testPcmToAmr");
        final String pcmPath = TEST_DIR + "in.pcm";
        final String amrPath = TEST_DIR + "out.amr";
        // demo 展示用，实际项目中请不要这样使用线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (WcvCodec.encode(pcmPath, amrPath) == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "testPcmToAmr success", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
                }
            }
        }).start();
    }

    private void testMp3ToAmr() {
        Log.d(TAG, "testMp3ToAmr");
        final String mp3Path = TEST_DIR + "in.mp3";
        final String pcmPath = TEST_DIR + "out.pcm";
        final String amrPath = TEST_DIR + "out.amr";
        // demo 展示用，实际项目中请不要这样使用线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (WcvCodec.encode2(mp3Path, pcmPath, amrPath) == 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "testMp3ToAmr success", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        // sd 卡权限获取成功，拷贝内置测试音频到测试路径
        copyTestVectors();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // sd 卡权限获取失败，打印提示日志
        Log.e(TAG, "onPermissionsDenied");
    }
}
