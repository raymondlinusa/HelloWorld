package com.example.helloworld;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class RecyclerAdapter<MyViewHolder> extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    String[] judul;
    String[] sinopsis;
    int[] gambar;
    Context context;

    public  RecyclerAdapter(Context context, String[] film, String[] sinopsis, int[] img){
        context = context;
        judul = film;
        this.sinopsis = sinopsis;
        gambar = img;
    }

    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rowitem,parent,false);
        RecyclerAdapter.MyViewHolder viewHolder = new RecyclerAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        holder.judul.setText(judul[position]);
        holder.sinopsis.setText(sinopsis[position]);
        holder.gambar.setImageResource(gambar[position]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.sinopsis.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            holder.judul.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
    }

    @Override
    public int getItemCount() {
        return this.judul.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView judul, sinopsis;
        ImageView gambar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.judul);
            sinopsis = itemView.findViewById(R.id.sinopsis);
            gambar = itemView.findViewById(R.id.gambar);
        }
    }
}
