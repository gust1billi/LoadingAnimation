package com.example.loadinganimation;

import android.os.AsyncTask;

public class AsyncTaskSleep extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... strings) {
        publishProgress("Sleeping..."); // Calls onProgressUpdate()

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

    }
}
