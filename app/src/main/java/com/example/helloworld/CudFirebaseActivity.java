package com.example.helloworld;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Document;

public class CudFirebaseActivity extends AppCompatActivity {
    private static final String TAG = CudFirebaseActivity.class.getSimpleName();
    private FirebaseFirestore firebaseFirestoreDb = FirebaseFirestore.getInstance();

    private EditText noMhs;
    private EditText namaMhs;
    private EditText phoneMhs;
    private Button buttonSimpan;
    private Button buttonHapus;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cud_firebase);
        String getState = getIntent().getStringExtra("STATE");

        noMhs = findViewById(R.id.noMhs);
        namaMhs = findViewById(R.id.namaMhs);
        phoneMhs = findViewById(R.id.phoneMhs);
        buttonSimpan = findViewById(R.id.simpanButton);
        buttonHapus = findViewById(R.id.hapusButton);
        btnBack = findViewById(R.id.btnBack);
        aksi();

        if(getState.equals("Edit")){
            getDataMahasiswa(getIntent().getStringExtra("DOC"));
        }
    }

    private void aksi() {
        buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!noMhs.getText().toString().isEmpty() && !namaMhs.getText().toString().isEmpty()) {
                    tambahMahasiswa();
                } else {
                    Toast.makeText(getApplication(), "No dan Nama Mhs tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        buttonHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataMahasiswa();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(CudFirebaseActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });
    }

    private void tambahMahasiswa() {

        mahasiswaModel mhs = new mahasiswaModel(noMhs.getText().toString(),
                namaMhs.getText().toString(),
                phoneMhs.getText().toString());

        String a= "mhs"+mhs.getNim();
        Log.i(TAG, "tambahMahasiswa: "+a);

        firebaseFirestoreDb.collection("DaftarMhs").document(a).set(mhs)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplication(), "Mahasiswa berhasil didaftarkan",
                                Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(CudFirebaseActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplication(), "ERROR" + e.toString(),
                            Toast.LENGTH_SHORT).show();
                    Log.d("TAG", e.toString());
                });
    }

    private void deleteDataMahasiswa() {
        String getDoc = getIntent().getStringExtra("DOC");
        firebaseFirestoreDb.collection("DaftarMhs").document(getDoc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        noMhs.setText("");
                        namaMhs.setText("");
                        phoneMhs.setText("");
                        Toast.makeText(getApplication(), "Mahasiswa berhasil dihapus",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplication(), "Error deleting document: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void getDataMahasiswa(String doc) {
        DocumentReference docRef = firebaseFirestoreDb.collection("DaftarMhs").document(doc);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        mahasiswaModel mhs = document.toObject(mahasiswaModel.class);
                        noMhs.setText(mhs.getNim());
                        namaMhs.setText(mhs.getNama());
                        phoneMhs.setText(mhs.getPhone());
                    } else {
                        Toast.makeText(getApplication(), "Document tidak ditemukan",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplication(), "Document error : " + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}