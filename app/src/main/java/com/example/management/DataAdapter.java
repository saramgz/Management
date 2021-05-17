package com.example.management;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

public class DataAdapter extends FirebaseRecyclerAdapter<Info,DataAdapter.DataViewHolder> {



    public DataAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Info> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull DataAdapter.DataViewHolder holder, int position, @NonNull @NotNull Info model) {
            holder.money.setText(model.category);
            holder.subject.setText(model.subject);
            holder.category.setText(model.category);

    }

    @NonNull
    @NotNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_detils, parent, false);
        return new DataAdapter.DataViewHolder(view);
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        TextView money,date,category,subject;
        RelativeLayout relativeLayout;
        public DataViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            money = itemView.findViewById(R.id.txtMoney);
            category = itemView.findViewById(R.id.txtCategory);
            subject = itemView.findViewById(R.id.txt_subject);
            relativeLayout = itemView.findViewById(R.id.relativeDetils);
        }
    }
}
