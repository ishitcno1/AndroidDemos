package com.edinstudio.app.demos.thread;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;

import com.edinstudio.app.demos.R;

/**
 * Created by albert on 15-3-17.
 */
public class AsyncTaskActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_async_task);
    }

    public void startTask(View view) {
        new MyAsyncTask().execute("");
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
        private static final int PB_MAX = 10;

        @Override
        protected Integer doInBackground(String... params) {
            int progress = 0;

            for (int i = 0; i < PB_MAX; i++) {
                SystemClock.sleep(500);
                publishProgress(++progress);
            }

            return progress;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressBar pb_main = (ProgressBar) findViewById(R.id.pb_main);
            pb_main.setVisibility(View.VISIBLE);
            pb_main.setMax(PB_MAX);
            pb_main.setProgress(0);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            findViewById(R.id.pb_main).setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ProgressBar pb_main = (ProgressBar) findViewById(R.id.pb_main);
            pb_main.setProgress(values[0]);
        }
    }
}
