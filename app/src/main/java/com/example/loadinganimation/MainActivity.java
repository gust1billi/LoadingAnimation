package com.example.loadinganimation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ProgressBar loadingBar;

    AsyncTaskSleep waits;
    AsyncTaskSleep runner;

    Button button;
    EditText time;
    TextView finalResult;

    Button loadingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waits = new AsyncTaskSleep();

        time = findViewById(R.id.in_time);
        button = findViewById(R.id.btn_run);
        finalResult = findViewById(R.id.tv_result);

        button.setOnClickListener( view -> {

            if ( time.length() != 0) {
                runner = new AsyncTaskSleep();
                String napTimer = time.getText().toString();
                runner.execute( napTimer );
            }

        });

        ExecutorService executor = Executors.newSingleThreadExecutor();
        // Handler handler = new Handler(Looper.getMainLooper());

        loadingBar = findViewById(R.id.loadingAnimation);

        hideLoading();

        loadingButton = findViewById(R.id.loadingButton);
        loadingButton.setOnClickListener( view -> {
            showLoading();
            executor.execute(() -> {
                try {
//                        showLoading();
                    Thread.sleep(3000);
//                        hideLoading();
                    Log.e("Path", "Boop");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        });
    }

    public class AsyncTaskSleep extends AsyncTask<String, String, String> {
        String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()

            try {
                int time = Integer.parseInt(params[0]) * 1000;

                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";
            } catch (InterruptedException e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(MainActivity.this,
                    "ProgressDialog",
                    "Wait for " + time.getText().toString()+ " seconds");
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate( values );

            finalResult.setText( values[0] );
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            finalResult.setText(result);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showLoading() {
        loadingBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        loadingBar.setVisibility(View.INVISIBLE);
    }
}