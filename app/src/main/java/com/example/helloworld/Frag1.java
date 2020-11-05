package com.example.helloworld;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag1#newInstance} factory method to
 * create an instance of this fragment.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Frag1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = Frag1.class.getSimpleName();

    private Button btnStartjob;
    private Button btnCanceljob;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag1.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag1 newInstance(String param1, String param2) {
        Frag1 fragment = new Frag1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_frag1, container, false);
        btnStartjob = rootView.findViewById(R.id.btnStartjob);
        btnCanceljob = rootView.findViewById(R.id.btnCanceljob);
        serviceStart();

        return rootView;
    }

    private void serviceStart() {
        btnStartjob.setOnClickListener(v -> scheduleJob());
        btnCanceljob.setOnClickListener(v -> cancelJob());
    }

    public void scheduleJob(){
        Log.i(TAG, "masuk start");
        ComponentName componentName = new ComponentName(requireActivity().getApplicationContext(), MyJobService.class);
        Log.i(TAG, "scheduleJob: membuat info");

        JobInfo jobInfo = new JobInfo.Builder(123, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();
        Log.i(TAG, "scheduleJob: membuat scheduler");
        JobScheduler jobScheduler = (JobScheduler) requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        Log.i(TAG, "scheduleJob: membuat result");
        int resultCode = jobScheduler.schedule(jobInfo);
        Log.i(TAG, "scheduleJob: cek result");
        if (resultCode == JobScheduler.RESULT_SUCCESS){
            Log.i(TAG, "scheduleJob: success");
        }
        else {
            Log.i(TAG, "scheduleJob: failed");
        }
    }

    public void cancelJob (){
        JobScheduler jobScheduler = (JobScheduler) requireContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(123);
        Log.i(TAG, "cancelJob");
        Toast.makeText(requireContext().getApplicationContext(),"cancelJob",Toast.LENGTH_SHORT).show();
    }


}