package com.daarululuumlido.latihanasynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements MyAsyncCallback {

    TextView tvStatus, tvDesc;
    final static String INPUT_STRING = "Halo Ini Demo AsyncTask!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tv_status);
        tvDesc = findViewById(R.id.tv_decs);
        DemoASync demoASync = new DemoASync(this);
        demoASync.execute(INPUT_STRING);
    }

    @Override
    public void onPreExecute() {
        tvStatus.setText(R.string.status_pre);
        tvDesc.setText(INPUT_STRING);
    }

    @Override
    public void onPostExecute(String result) {
        tvStatus.setText(R.string.status_post);
        if (result != null) {
            tvDesc.setText(result);
        }

    }

    private static class DemoASync extends AsyncTask<String, Void, String> {
        static final String LOG_ASYNC = "DemoAsync";
        WeakReference<MyAsyncCallback> myListener;

        DemoASync(MyAsyncCallback myListener) {
            this.myListener = new WeakReference<>(myListener);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(LOG_ASYNC, "status: onPreExecute");

            MyAsyncCallback myListener = this.myListener.get();
            if (myListener != null) {
                myListener.onPreExecute();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(LOG_ASYNC, "status: doInBackground: ");
            String output = null;
            try {
                String input = params[0];
                output = input + " Selamat Belajar";
                Thread.sleep(5000);
            } catch (Exception e) {
                Log.d(LOG_ASYNC, e.getMessage());
            }
            return output;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_ASYNC, "status: onPostExecute");
            MyAsyncCallback myListner = this.myListener.get();
            if (myListner != null) {
                myListner.onPostExecute(result);
            }
        }
    }


}
