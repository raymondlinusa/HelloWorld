package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag3 extends Fragment {

    private static final String TAG = Frag3.class.getSimpleName();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FloatingActionButton btnCreate;

    private FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

    private ArrayList<mahasiswaModel> dataMahasiswa = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag3() {
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
    public static Frag3 newInstance(String param1, String param2) {
        Frag3 fragment = new Frag3();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_frag3, container, false);
        getDataMahasiswa();


        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerViewMahasiswa);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        btnCreate = getActivity().findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CudFirebaseActivity.class);
                intent.putExtra("STATE", "Create");
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void getDataMahasiswa() {

        firebaseFirestoreDb.collection("DaftarMhs")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String NIM = (String) document.getData().get("nim");
                                String nama = (String) document.getData().get("nama");
                                String nohp = (String) document.getData().get("phone");

                                mahasiswaModel a = new mahasiswaModel(NIM, nama, nohp);
                                dataMahasiswa.add(a);

                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            mAdapter = new RecyclerAdapterFirebase(getActivity(), getActivity(), dataMahasiswa);
                            recyclerView.setAdapter(mAdapter);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


}