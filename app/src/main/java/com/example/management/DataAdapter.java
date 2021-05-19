package com.example.management;


import android.content.Intent;
import android.graphics.drawable.Drawable;
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

    static int all=0,in=0,out=0;
    static int count;



    public DataAdapter(@NonNull @NotNull FirebaseRecyclerOptions<Info> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull DataAdapter.DataViewHolder holder, int position, @NonNull @NotNull Info model) {
        holder.bindModels(model);


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
        RelativeLayout relativeLayout,relativeStatus;
        AppActivity appActivity;
        public DataViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            money = itemView.findViewById(R.id.txtMoney);
            category = itemView.findViewById(R.id.txtCategory);
            subject = itemView.findViewById(R.id.txt_subject);
            relativeLayout = itemView.findViewById(R.id.relativeDetils);
            relativeStatus = itemView.findViewById(R.id.relative_status);




        }
        public void bindModels(Info model){

            money.setText(String.valueOf(model.money));
            subject.setText(model.subject);
            category.setText(model.category);

            if (model.getStatus().equals("in"))
                relativeStatus.setBackgroundResource(R.drawable.back_status_in);
            else relativeStatus.setBackgroundResource(R.drawable.back_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), EditActivity.class);
                    intent.putExtra("uid", model.getUid());
                    intent.putExtra("money", String.valueOf(model.getMoney()));
                    intent.putExtra("subject", model.getSubject());
                    intent.putExtra("category", model.getCategory());
                    intent.putExtra("date", model.getDate());
                    intent.putExtra("ex", model.getExplain());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
