package com.example.helloworld;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobService extends JobService {
    private static final String TAG = MyJobService.class.getSimpleName();
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        doBackground(jobParameters);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        jobCancelled = true;
        return true;
    }

    private void doBackground(final JobParameters jobParameters){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
//                        Looper.prepare();
                        int i = 0;
                        while (i == 0){
                            Log.i(TAG, "run: "+ i);
                            final int finalI = i;
                            Handler handler = new Handler(getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "jobScheduler berjalan", Toast.LENGTH_SHORT).show();
                                }
                            });

                            if (jobCancelled){
                                return;
                            }

                            try{
                                Thread.sleep(3000);
                            }catch (InterruptedException e){
                                Log.e(TAG, "InteruptedException : ", e.getCause());
                            }
                        }
                        Log.i(TAG, "run: JOB FINISHED");
                    }
        }).start();
    }
}
