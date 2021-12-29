package com.reinhard.wechat.voicecodec;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.reinhard.wcvcodec.WcvCodec;

public class MainActivity extends Activity {
    private static final String TAG = "WcvCodec";
    private static final String TEST_DIR = "/sdcard/reinhard/";
    private static final String[] ASSET_FILE_NAMES = {
            "in.amr",
            "in2.amr",
            "in3.amr",
            "in4.amr",
            "in.mp3",
            "in.pcm",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        copyTestVectors();
    }

    private void initViews() {
        TextView tvTip = findViewById(R.id.tv_tip);
        String formatStr = getString(R.string.test_dir_format);
        tvTip.setText(String.format(formatStr, TEST_DIR));
    }

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

    private void testAmrToMp3() {
        Log.d(TAG, "testAmrToMp3");
        for (int i = 2; i < 5; i++) {
            final String amrPath = TEST_DIR + "in" + i + ".amr";
            final String pcmPath = TEST_DIR + "out" + i + ".pcm";
            final String mp3Path = TEST_DIR + "out" + i + ".mp3";
            // demo 展示用，实际项目中请不要这样使用线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (WcvCodec.decode(amrPath, pcmPath, mp3Path) == 0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "testAmrToMp3 of " + amrPath + " success", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }
                }
            }).start();
        }
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
}
