package com.example.avante_app.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.avante_app.DataModels.Datum;
import com.example.avante_app.R;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Datum> dataList ;

    public ItemAdapter(Context context, ArrayList<Datum> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int position) {


        holder.firstname.setText(dataList.get(position).getFirstName());
        holder.lastname.setText(dataList.get(position).getLastName());
        holder.email.setText(dataList.get(position).getEmail());

        Glide
                .with(context)
                .load(dataList.get(position).getAvatar())
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView firstname,lastname,email;
        ImageView photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstname=(TextView)itemView.findViewById(R.id.firstname);
            lastname=(TextView)itemView.findViewById(R.id.lastname);
            email=(TextView)itemView.findViewById(R.id.email);
            photo=(ImageView) itemView.findViewById(R.id.img);
        }
    }
}