package com.example.helloworld;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class RecyclerAdapterFirebase<MyViewHolder> extends RecyclerView.Adapter<RecyclerAdapterFirebase.MyViewHolder> {
    private static final String TAG = RecyclerAdapterFirebase.class.getSimpleName();
    private static ArrayList<mahasiswaModel> listMahasiswa;
    private Context context;
    private Activity activity;

    public RecyclerAdapterFirebase(Activity activity, Context context, ArrayList<mahasiswaModel> listMahasiswa){
        this.listMahasiswa = listMahasiswa;
        this.context = context;
        this.activity = activity;
    }


    @NonNull
    @Override
    public RecyclerAdapterFirebase.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rowitem_firebase,parent,false);
        RecyclerAdapterFirebase.MyViewHolder viewHolder = new RecyclerAdapterFirebase.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterFirebase.MyViewHolder holder, int position) {
        final String NIM = listMahasiswa.get(position).getNim();
        final String Nama = listMahasiswa.get(position).getNama();
        final String Hp = listMahasiswa.get(position).getPhone();

        holder.noMahasiswa.setText(NIM);
        holder.namaMahasiswa.setText(Nama);
        holder.noHp.setText(Hp);

        holder.listitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CudFirebaseActivity.class);
                String mhs = listMahasiswa.get(holder.getAdapterPosition()).getNim();
                Log.i(TAG, "onClick: "+mhs);
                intent.putExtra("STATE", "Edit");
                intent.putExtra("DOC", "mhs"+mhs);
                context.startActivity(intent);
                activity.finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.noMahasiswa.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.namaMahasiswa.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.noHp.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    @Override
    public int getItemCount() {
        return this.listMahasiswa.size();
//        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView noMahasiswa, namaMahasiswa, noHp;
        LinearLayout listitem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            noMahasiswa = itemView.findViewById(R.id.noMhs);
            namaMahasiswa = itemView.findViewById(R.id.namaMhs);
            noHp = itemView.findViewById(R.id.phoneMhs);
            listitem = itemView.findViewById(R.id.list_item);
        }
    }
}
