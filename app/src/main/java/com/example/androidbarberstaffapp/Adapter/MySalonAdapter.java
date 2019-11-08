package com.example.androidbarberstaffapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidbarberstaffapp.Interface.IRecyclerItemSelectedListener;
import com.example.androidbarberstaffapp.Model.Salon;
import com.example.androidbarberstaffapp.R;

import java.util.ArrayList;
import java.util.List;

public class MySalonAdapter extends RecyclerView.Adapter<MySalonAdapter.MyViewHolder> {

    Context context;
    List<Salon> salonList;
    List<CardView> cardViewList;
    LocalBroadcastManager localBroadcastManager;


    public MySalonAdapter(Context context, List<Salon> salonList) {
        this.context = context;
        this.salonList = salonList;
        cardViewList = new ArrayList<>();
        localBroadcastManager =LocalBroadcastManager.getInstance(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_salon, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.txt_salon_name.setText(salonList.get(i).getName());
        myViewHolder.txt_salon_address.setText(salonList.get(i).getAddress());

        if(!cardViewList.contains(myViewHolder.card_salon))
            cardViewList.add(myViewHolder.card_salon);

            myViewHolder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
                @Override
                public void onItemSelected(View view, int position) {
                    //Set White Background for all card which are not selected
                    for(CardView cardView : cardViewList)
                        cardView.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));

                    //Set Background for selected item
                    myViewHolder.card_salon.setCardBackgroundColor(context.getResources()
                            .getColor(android.R.color.holo_orange_dark));
                }
            });

    }

    @Override
    public int getItemCount() {
        return salonList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_salon_name, txt_salon_address;
        CardView card_salon;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            card_salon = (CardView)itemView.findViewById(R.id.card_salon);
            txt_salon_address = (TextView)itemView.findViewById(R.id.txt_salon_address);
            txt_salon_name = (TextView)itemView.findViewById(R.id.txt_salon_name);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            iRecyclerItemSelectedListener.onItemSelected(view, getAdapterPosition());
        }
    }
}
