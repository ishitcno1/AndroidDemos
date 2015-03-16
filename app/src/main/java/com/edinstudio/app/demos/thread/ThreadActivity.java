package com.edinstudio.app.demos.thread;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

import com.edinstudio.app.demos.R;

/**
 * Created by albert on 15-3-16.
 */
public class ThreadActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_thread);
    }

    public void startThread(View view) {
        new Thread(new MyRunnable()).start();
    }

    private class MyRunnable implements Runnable {
        private static final int PB_MAX = 10;

        private int mProgress = 0;

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ProgressBar pb_main = (ProgressBar) findViewById(R.id.pb_main);
                    pb_main.setVisibility(View.VISIBLE);
                    pb_main.setMax(PB_MAX);
                    pb_main.setProgress(mProgress);
                }
            });

            for (int i = 0; i < PB_MAX; i++) {
                SystemClock.sleep(500);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProgressBar pb_main = (ProgressBar) findViewById(R.id.pb_main);
                        pb_main.setProgress(++mProgress);
                    }
                });
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    findViewById(R.id.pb_main).setVisibility(View.GONE);
                }
            });
        }
    }
}
